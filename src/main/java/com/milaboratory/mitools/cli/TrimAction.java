/*
 * Copyright 2015 MiLaboratory.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.milaboratory.mitools.cli;

import cc.redberry.pipe.blocks.FilteringPort;
import cc.redberry.pipe.blocks.Merger;
import cc.redberry.pipe.blocks.ParallelProcessor;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.core.io.sequence.fastq.SingleFastqWriter;
import com.milaboratory.mitools.trimmer.ReadLengthFilter;
import com.milaboratory.mitools.trimmer.SequenceTrimmer;
import com.milaboratory.util.SmartProgressReporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static cc.redberry.pipe.CUtils.buffered;
import static cc.redberry.pipe.CUtils.it;

public class TrimAction implements Action {
    final TrimParameters actionParameters = new TrimParameters();

    @Override
    public void go(ActionHelper helper) throws Exception {
        try (SingleFastqReader reader = MiCLIUtil.createSingleReader(actionParameters.getInput(), false);
             SingleFastqWriter writer = MiCLIUtil.createSingleWriter(actionParameters.getOutput())) {
            SmartProgressReporter.startProgressReport("Trimming", reader, System.err);

            SimpleReadsStatisticsPortWrapper<SingleRead> stats = new SimpleReadsStatisticsPortWrapper<>(reader);

            final Merger<SingleRead> buffered = buffered(stats, 256);

            final SequenceTrimmer trimmer = new SequenceTrimmer(actionParameters.qualutyThreshold,
                    actionParameters.trimmLeft(), actionParameters.trimmRight());

            ParallelProcessor<SingleRead, SingleRead> processor =
                    new ParallelProcessor<>(buffered, trimmer, 1024, actionParameters.threads);

            FilteringPort<SingleRead> filtered = new FilteringPort<>(processor,
                    new ReadLengthFilter(actionParameters.length));

            for (SingleRead result : it(filtered)) {
                writer.write(result);
            }

            if (actionParameters.report != null) {
                boolean n = !new File(actionParameters.report).exists();
                try (PrintStream reportStream = new PrintStream(new FileOutputStream(actionParameters.report, true))) {
                    if (n)
                        reportStream.println("OutputFileName\tTotal\tTrimmed\tPercentOfReads\tPercentOfNucleotides\tPercentFiltered");
                    reportStream.println(actionParameters.getOutput() + "\t"
                            + stats.count.get() + "\t"
                            + trimmer.getTotalTrimmedReads() + "\t"
                            + (100.0 * trimmer.getTotalTrimmedReads() / stats.count.get()) + "\t"
                            + (100.0 * (trimmer.getTrimmedNucleotidesLeft() + trimmer.getTrimmedNucleotidesRight())
                            / stats.nucleotides.get()) + "\t" +
                            (100.0 * filtered.getRejectedCount() / stats.count.get()));
                }
            }
        }
    }

    @Override
    public String command() {
        return "trim";
    }

    @Override
    public ActionParameters params() {
        return actionParameters;
    }

    @Parameters(commandDescription = "Trim low quality ends of reads.", optionPrefixes = "-")
    private final static class TrimParameters extends ActionParameters {
        @Parameter(description = "input_file.fastq[.gz] -|output_file.fastq[.gz]", variableArity = true)
        public List<String> parameters = new ArrayList<>();

        @Parameter(description = "Trim left", names = {"-tl", "--left"})
        public Boolean left;

        @Parameter(description = "Trim right", names = {"-tr", "--right"})
        public Boolean right;

        @Parameter(description = "Quality threshold", names = {"-q", "--quality"},
                validateWith = PositiveInteger.class)
        public int qualutyThreshold = 15;

        @Parameter(description = "Length threshold", names = {"-l", "--length"})
        public int length = 10;

        @Parameter(description = "Threads", names = {"-t", "--threads"},
                validateWith = PositiveInteger.class)
        int threads = Math.min(Runtime.getRuntime().availableProcessors(), 6);

        @Parameter(description = "Report file.",
                names = {"-r", "--report"})
        String report;

        public String getInput() {
            return parameters.get(0);
        }

        public String getOutput() {
            return parameters.get(1);
        }

        public boolean trimmLeft() {
            return left == null ? false : left;
        }

        public boolean trimmRight() {
            return right == null ? false : right;
        }

        @Override
        public void validate() {
            if (parameters.size() != 2)
                throw new ParameterException("Wrong number of parameters.");
        }
    }
}
