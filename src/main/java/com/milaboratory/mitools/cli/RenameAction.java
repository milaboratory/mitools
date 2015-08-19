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

public class RenameAction implements Action {
    final RenameParameters actionParameters = new RenameParameters();

    @Override
    public void go(ActionHelper helper) throws Exception {
        final String pattern = actionParameters.pattern;
        try (SingleFastqReader reader = MiCLIUtil.createSingleReader(actionParameters.getInput(), false);
             SingleSequenceWriter writer = MiCLIUtil.createSingleWriter(actionParameters.getOutput())) {
            SmartProgressReporter.startProgressReport("Processing", reader, System.err);
            for (SingleRead read : CUtils.it(reader)) {
                String description = pattern.replace("%n", Long.toString(read.getId()));
                description = description.replace("%o", read.getDescription());
                SingleRead rc = new SingleReadImpl(read.getId(), read.getData(), description);
                writer.write(rc);
            }
        }
    }

    @Override
    public String command() {
        return "rename";
    }

    @Override
    public ActionParameters params() {
        return actionParameters;
    }

    @Parameters(commandDescription = "Relabel sequences.", optionPrefixes = "-")
    public static final class RenameParameters extends ActionParameters {
        @Parameter(description = "[ input_file_R1.fastq[.gz] [ output_file.fastq[.gz] ] ]", variableArity = true)
        public List<String> parameters = new ArrayList<>();

        @Parameter(description = "Pattern (%n - for index; %o - for old description)",
                names = {"-p", "--pattern"})
        String pattern;

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
