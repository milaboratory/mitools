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
import com.milaboratory.cli.Action;
import com.milaboratory.cli.ActionHelper;
import com.milaboratory.cli.ActionParameters;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.SingleReadImpl;
import com.milaboratory.core.io.sequence.SingleSequenceWriter;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.util.SmartProgressReporter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameAction implements Action {
    final RenameParameters actionParameters = new RenameParameters();

    @Override
    public void go(ActionHelper helper) throws Exception {
        final String newName = actionParameters.name;

        Pattern pattern = actionParameters.pattern == null ?
                null :
                Pattern.compile(actionParameters.pattern);

        Matcher matcher;

        try (SingleFastqReader reader = MiCLIUtil.createSingleReader(actionParameters.getInput(), false);
             SingleSequenceWriter writer = MiCLIUtil.createSingleWriter(actionParameters.getOutput())) {
            SmartProgressReporter.startProgressReport("Processing", reader, System.err);
            for (SingleRead read : CUtils.it(reader)) {
                // Replacing index
                String description = newName.replace("%n", Long.toString(read.getId()));

                // Replacing old description
                description = description.replace("%o", read.getDescription());

                // Replacing caption groups
                if (pattern != null)
                    if ((matcher = pattern.matcher(read.getDescription())).find())
                        for (int i = 1; i <= matcher.groupCount(); i++)
                            description = description.replace("%g" + i, matcher.group(i));

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

    @Parameters(commandDescription = "Relabel sequences (change sequence headers).")
    public static final class RenameParameters extends ActionParameters {
        @Parameter(description = "[ input_file_R1.fastq[.gz] [ output_file.fastq[.gz] ] ]", variableArity = true)
        public List<String> parameters = new ArrayList<>();

        @Parameter(description = "New header (%n - for index; %o - for old description; %g1, %g2, ... - for old description)",
                names = {"-p", "--pattern"})
        String pattern;

        @Parameter(description = "New name (%n - for index; %o - for old description; " +
                "%g1, %g2, ... - extracted regexp groups, if -p option used), example: -n \"R%n %o\" to add read index to header",
                names = {"-n", "--name"})
        String name;

        public String getInput() {
            return parameters.size() == 0 ? "." : parameters.get(0);
        }

        public String getOutput() {
            return parameters.size() < 2 ? "." : parameters.get(1);
        }

        @Override
        public void validate() {
            if (parameters.size() > 2)
                throw new ParameterException("Wrong number of parameters.");
        }
    }
}
