package com.milaboratory.mitools.cli;

import cc.redberry.pipe.CUtils;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.milaboratory.core.io.sequence.SequenceRead;
import com.milaboratory.core.io.sequence.SequenceReaderCloseable;
import com.milaboratory.core.io.sequence.SequenceWriter;
import com.milaboratory.core.io.sequence.fastq.PairedFastqReader;
import com.milaboratory.core.io.sequence.fastq.PairedFastqWriter;
import com.milaboratory.mitools.processing.Processor;
import com.milaboratory.util.CanReportProgress;
import com.milaboratory.util.SmartProgressReporter;

import java.io.IOException;

/**
 * Created by dbolotin on 29/11/14.
 */
public class ProcessAction implements Action {
    final RenameParameters actionParameters = new RenameParameters();

    @Override
    public void go(ActionHelper helper) throws Exception {
        try (SequenceReaderCloseable<?> reader = actionParameters.getInput();
             SequenceWriter writer = actionParameters.getOutput()) {

            if (reader instanceof CanReportProgress)
                SmartProgressReporter.startProgressReport("Processing", (CanReportProgress) reader, System.err);

            Processor processor = Processor.build(actionParameters.actions);

            processor.setReadConsumer(s -> writer.write(s));

            for (SequenceRead read : CUtils.it(reader))
                if (!processor.apply(read))
                    return;
        }
    }

    @Override
    public String command() {
        return "process";
    }

    @Override
    public ActionParameters params() {
        return actionParameters;
    }

    @Parameters(commandDescription = "Reverse complement.", optionPrefixes = "-")
    public static final class RenameParameters extends ActionParameters {
        @Parameter(description = "Actions",
                names = {"-a", "--actions"})
        String actions;

        @Parameter(description = "Input 1",
                names = {"-i1", "--input1"})
        String i1;

        @Parameter(description = "Input 2",
                names = {"-i2", "--input2"})
        String i2;

        @Parameter(description = "Output 1",
                names = {"-o1", "--output1"})
        String o1;

        @Parameter(description = "Output 2",
                names = {"-o2", "--output2"})
        String o2;

        public SequenceReaderCloseable getInput() throws IOException {
            if (i1 == null && i2 == null)
                return MiCLIUtil.createSingleReader("-");

            if (i2 == null)
                return MiCLIUtil.createSingleReader(i1);

            return new PairedFastqReader(MiCLIUtil.createSingleReader(i1), MiCLIUtil.createSingleReader(i2));
        }

        public SequenceWriter getOutput() throws IOException {
            if (o1 == null && o2 == null)
                return MiCLIUtil.createSingleWriter("-");

            if (o2 == null)
                return MiCLIUtil.createSingleWriter(o1);

            return new PairedFastqWriter(MiCLIUtil.createSingleWriter(o1), MiCLIUtil.createSingleWriter(o2));
        }
    }
}
