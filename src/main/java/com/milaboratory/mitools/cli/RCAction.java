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

import cc.redberry.pipe.CUtils;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.SingleReadImpl;
import com.milaboratory.core.io.sequence.SingleSequenceWriter;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.util.SmartProgressReporter;

import java.util.ArrayList;
import java.util.List;

public class RCAction implements Action {
    final RCParameters actionParameters = new RCParameters();

    @Override
    public void go(ActionHelper helper) throws Exception {
        try (SingleFastqReader reader = MiCLIUtil.createSingleReader(actionParameters.getInput());
             SingleSequenceWriter writer = MiCLIUtil.createSingleWriter(actionParameters.getOutput())) {
            SmartProgressReporter.startProgressReport("Processing", reader, System.err);
            for (SingleRead read : CUtils.it(reader)) {
                SingleRead rc = new SingleReadImpl(read.getId(), read.getData().getReverseComplement(), read.getDescription());
                writer.write(rc);
            }
        }
    }

    @Override
    public String command() {
        return "rc";
    }

    @Override
    public ActionParameters params() {
        return actionParameters;
    }

    @Parameters(commandDescription = "Reverse complement.", optionPrefixes = "-")
    public static final class RCParameters extends ActionParameters {
        @Parameter(description = "[ input_file_R1.fastq[.gz] [ output_file.fastq[.gz] ] ]", variableArity = true)
        public List<String> parameters = new ArrayList<>();

        public String getInput() {
            return parameters.size() == 0 ? "-" : parameters.get(0);
        }

        public String getOutput() {
            return parameters.size() < 2 ? "-" : parameters.get(1);
        }

        @Override
        public void validate() {
            if (parameters.size() > 2)
                throw new ParameterException("Wrong number of parameters.");
        }
    }
}
