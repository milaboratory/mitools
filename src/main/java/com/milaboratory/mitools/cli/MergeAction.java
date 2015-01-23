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

import cc.redberry.pipe.blocks.Merger;
import cc.redberry.pipe.blocks.ParallelProcessor;
import cc.redberry.pipe.util.Indexer;
import cc.redberry.pipe.util.OrderedOutputPort;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;
import com.milaboratory.core.io.sequence.PairedRead;
import com.milaboratory.core.io.sequence.SingleReadImpl;
import com.milaboratory.core.io.sequence.fastq.PairedFastqReader;
import com.milaboratory.core.io.sequence.fastq.SingleFastqWriter;
import com.milaboratory.mitools.merger.MismatchOnlyPairedReadMerger;
import com.milaboratory.mitools.merger.PairedReadMergingResult;
import com.milaboratory.util.SmartProgressReporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static cc.redberry.pipe.CUtils.buffered;
import static cc.redberry.pipe.CUtils.it;

public class MergeAction implements Action {
    final MergingParameters actionParameters = new MergingParameters();

    @Override
    public void go(ActionHelper helper) throws Exception {
        final MismatchOnlyPairedReadMerger merger = new MismatchOnlyPairedReadMerger(actionParameters.overlap,
                actionParameters.similarity);

        long total = 0, overlapped = 0;
        try (PairedFastqReader reader = new PairedFastqReader(actionParameters.getR1(), actionParameters.getR2());
             SingleFastqWriter mainWriter = MiCLIUtil.createSingleWriter(actionParameters.getOutput());
             SingleFastqWriter nfWriter = actionParameters.forwardNegative == null ?
                     null : new SingleFastqWriter(actionParameters.forwardNegative);
             SingleFastqWriter nrWriter = actionParameters.reverseNegative == null ?
                     null : new SingleFastqWriter(actionParameters.reverseNegative)) {
            SmartProgressReporter.startProgressReport("Merging", reader, System.err);

            final Merger<PairedRead> buffered = buffered(reader, 256);

            ParallelProcessor<PairedRead, PairedReadMergingResult> processor =
                    new ParallelProcessor<>(buffered, merger, 1024, actionParameters.threads);

            OrderedOutputPort<PairedReadMergingResult> oop = new OrderedOutputPort<>(processor, new Indexer<PairedReadMergingResult>() {
                @Override
                public long getIndex(PairedReadMergingResult pairedReadMergingResult) {
                    return pairedReadMergingResult.getOriginalRead().getId();
                }
            });

            final boolean includeNonOverlapped = actionParameters.includeNonOverlapped;
            long id = 0;

            for (PairedReadMergingResult result : it(oop)) {
                ++total;
                if (result.isSuccessful()) {
                    mainWriter.write(
                            new SingleReadImpl(result.getOriginalRead().getId(),
                                    result.getOverlappedSequence(), result.getOriginalRead().getR1().getDescription()));
                    ++overlapped;
                } else {
                    if (nfWriter != null) {
                        nfWriter.write(result.getOriginalRead().getR1());
                    }

                    if (nrWriter != null) {
                        nrWriter.write(result.getOriginalRead().getR2());
                    }

                    if (includeNonOverlapped) {
                        mainWriter.write(result.getOriginalRead().getR1());
                        mainWriter.write(result.getOriginalRead().getR2());
                    }
                }
            }
        }

        if (actionParameters.report != null) {
            boolean n = !new File(actionParameters.report).exists();
            try (PrintStream reportStream = new PrintStream(new FileOutputStream(actionParameters.report, true))) {
                if (n)
                    reportStream.println("OutputFileName\tTotal\tOverlapped\tOverlappedPercent");
                reportStream.println(actionParameters.getOutput() + "\t" + total + "\t" + overlapped + "\t" + (100.0 * overlapped / total));
            }
        }
    }

    @Override
    public String command() {
        return "merge";
    }

    @Override
    public ActionParameters params() {
        return actionParameters;
    }

    @Parameters(commandDescription = "Merge overlapping paired-end reads.", optionPrefixes = "-")
    public static final class MergingParameters extends ActionParameters {
        @Parameter(description = "input_file_R1.fastq[.gz] input_file_R2.fastq[.gz] -|output_file.fastq[.gz]", variableArity = true)
        public List<String> parameters = new ArrayList<>();

        @Parameter(description = "FASTQ file to put non-overlapped forward reads (supported formats: " +
                "*.fastq and *.fastq.gz).",
                names = {"-nf", "--negative-forward"},
                converter = MiCLIUtil.FileConverter.class)
        File forwardNegative;

        @Parameter(description = "FASTQ file to put non-overlapped reverse reads (supported formats: " +
                "*.fastq and *.fastq.gz).",
                names = {"-nr", "--negative-reverse"},
                converter = MiCLIUtil.FileConverter.class)
        File reverseNegative;

        @Parameter(description = "Report file.",
                names = {"-r", "--report"})
        String report;

        //@Parameter(description = "Output FASTQ file or \"-\" for STDOUT to put non-overlapped " +
        //        "reverse reads (supported formats: *.fastq and *.fastq.gz).",
        //        names = {"-o", "--out"},
        //        required = true)
        //String output;

        @Parameter(description = "Include both paired-end reads for pairs where no overlap was found.",
                names = {"-i", "--include-non-overlapped"})
        boolean includeNonOverlapped;

        @Parameter(description = "Discard original sequence header and put sequential ids.",
                names = {"-d", "--discard-header"})
        boolean discardHeader;

        @Parameter(description = "Minimal overlap.",
                names = {"-p", "--overlap"}, validateWith = PositiveInteger.class)
        int overlap = 15;

        @Parameter(description = "Minimal allowed similarity",
                names = {"-s", "--similarity"})
        double similarity = 0.85;

        @Parameter(description = "Threads",
                names = {"-t", "--threads"}, validateWith = PositiveInteger.class)
        int threads = Math.min(Runtime.getRuntime().availableProcessors(), 4);

        public String getR1() {
            return parameters.get(0);
        }

        public String getR2() {
            return parameters.get(1);
        }

        public String getOutput() {
            return parameters.size() == 2 ? "-" : parameters.get(2);
        }

        @Override
        public void validate() {
            if (parameters.size() > 3 || parameters.size() < 2)
                throw new ParameterException("Wrong number of parameters.");
        }
    }
}
