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
        try (SingleFastqReader reader = MiCLIUtil.createSingleReader(actionParameters.getInput());
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

    @Parameters(commandDescription = "Reverse complement.", optionPrefixes = "-")
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
