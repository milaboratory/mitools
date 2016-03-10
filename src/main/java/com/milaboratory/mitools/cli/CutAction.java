/*
 * Copyright 2016 MiLaboratory.com
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

import cc.redberry.pipe.CUtils;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.SingleSequenceWriter;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.mitools.processors.CutSide;
import com.milaboratory.mitools.processors.RandomCutter;
import com.milaboratory.util.SmartProgressReporter;

import java.util.ArrayList;
import java.util.List;

import static cc.redberry.pipe.CUtils.wrap;

public class CutAction implements Action {
    final AParams actionParameters = new AParams();

    @Override
    public void go(ActionHelper helper) throws Exception {
        try (SingleFastqReader reader = MiCLIUtil.createSingleReader(actionParameters.getInput(), false);
             SingleSequenceWriter writer = MiCLIUtil.createSingleWriter(actionParameters.getOutput())) {
            SmartProgressReporter.startProgressReport("Processing", reader, System.err);

            RandomCutter cutter = new RandomCutter(actionParameters.getPosition(), actionParameters.getMinLengt(),
                    actionParameters.getMaxLengt(), actionParameters.getSeed());

            for (SingleRead read : CUtils.it(wrap(reader, cutter)))
                writer.write(read);
        }
    }

    @Override
    public String command() {
        return "cut";
    }

    @Override
    public ActionParameters params() {
        return actionParameters;
    }

    @Parameters(commandDescription = "Cut sequences.", optionPrefixes = "-")
    public static final class AParams extends ActionParameters {
        @Parameter(description = "[ input_file_R1.fastq[.gz] [ output_file.fastq[.gz] ] ]", variableArity = true)
        public List<String> parameters = new ArrayList<>();

        @Parameter(description = "Length.",
                names = {"-l", "--length"})
        Integer length;

        @Parameter(description = "Minimal length; inclusive (random value will be selected for each sequence; use with " +
                "--max-length; don't use with --length option).",
                names = {"-al", "--min-length"})
        Integer minLength;

        @Parameter(description = "Maximal length; inclusive (random value will be selected for each sequence; use with " +
                "--min-length; don't use with --length option).",
                names = {"-bl", "--max-length"})
        Integer maxLength;

        @Parameter(description = "Position to cut from. (possible values 'Left', 'Right', 'Center')",
                names = {"-p", "--position"})
        String position = "Left";

        @Parameter(description = "Seed for random generator.",
                names = {"-s", "--seed"})
        Integer seed;

        public String getInput() {
            return parameters.size() == 0 ? "-" : parameters.get(0);
        }

        public String getOutput() {
            return parameters.size() < 2 ? "-" : parameters.get(1);
        }

        public int getSeed() {
            return seed == null ? 0 : seed;
        }

        public int getMinLengt() {
            if (length != null)
                return length;
            return minLength;
        }

        public int getMaxLengt() {
            if (length != null)
                return length;
            return maxLength;
        }

        public CutSide getPosition() {
            return CutSide.valueOf(position);
        }

        @Override
        public void validate() {
            if (parameters.size() > 2)
                throw new ParameterException("Wrong number of parameters.");
            if (length == null && minLength == null && maxLength == null)
                throw new ParameterException("Please specify the length to cut: --length or --max-length and --min-length.");
            if (length != null && (minLength != null || maxLength != null))
                throw new ParameterException("--length option is not compatible with --max-length and --min-length");
            if ((minLength == null) != (maxLength == null))
                throw new ParameterException("Please specify both options --max-length and --min-length.");
        }
    }
}
