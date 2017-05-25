/*
 * Copyright 2017 MiLaboratory.com
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
import cc.redberry.pipe.OutputPort;
import cc.redberry.pipe.OutputPortCloseable;
import cc.redberry.pipe.util.CountLimitingOutputPort;
import cc.redberry.pipe.util.CountingOutputPort;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.milaboratory.cli.Action;
import com.milaboratory.cli.ActionHelper;
import com.milaboratory.cli.ActionParameters;
import com.milaboratory.cli.ActionParametersWithOutput;
import com.milaboratory.core.io.sequence.SequenceRead;
import com.milaboratory.core.io.sequence.SequenceReaderCloseable;
import com.milaboratory.core.io.sequence.SequenceWriter;
import com.milaboratory.core.io.sequence.fastq.PairedFastqReader;
import com.milaboratory.core.io.sequence.fastq.PairedFastqWriter;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.core.io.sequence.fastq.SingleFastqWriter;
import com.milaboratory.primitivio.PrimitivI;
import com.milaboratory.primitivio.PrimitivO;
import com.milaboratory.util.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RandomizeAction implements Action {
    final AParameters parameters = new AParameters();

    @Override
    public void go(ActionHelper helper) throws Exception {
        CountingOutputPort<SequenceRead> randomized;
        long totalReadsCount;
        try (SequenceReaderCloseable<SequenceRead> reader = parameters.getReader()) {
            SmartProgressReporter.startProgressReport("Randomizing chunks", (CanReportProgress) reader);
            CountingOutputPort<SequenceRead> countingReader = new CountingOutputPort<>(reader);
            File tempFile = TempFileManager.getTempFile();
            randomized = new CountingOutputPort<>(
                    Randomizer.randomize(countingReader, RandomUtil.getThreadLocalRandomData(),
                            1000000, new ObjectSerializer<SequenceRead>() {
                                @Override
                                public void write(Collection<SequenceRead> data, OutputStream stream) {
                                    try (PrimitivO o = new PrimitivO(new BufferedOutputStream(stream))) {
                                        o.writeInt(data.size());
                                        for (SequenceRead el : data)
                                            o.writeObject(el);
                                    }
                                }

                                @Override
                                public OutputPort<SequenceRead> read(InputStream stream) {
                                    final PrimitivI i = new PrimitivI(stream);
                                    int count = i.readInt();
                                    return new CountLimitingOutputPort<>(new OutputPort<SequenceRead>() {
                                        @Override
                                        public SequenceRead take() {
                                            synchronized (i) {
                                                return i.readObject(SequenceRead.class);
                                            }
                                        }
                                    }, count);
                                }
                            }, tempFile));
            totalReadsCount = countingReader.getCount();
        }

        SmartProgressReporter.startProgressReport("Writing result",
                SmartProgressReporter.extractProgress(randomized, totalReadsCount));

        // Open output file ad write results
        try (SequenceWriter<SequenceRead> writer = parameters.getWriter()) {
            for (SequenceRead read : CUtils.it(randomized))
                writer.write(read);
        }
    }

    @Override
    public String command() {
        return "randomize";
    }

    @Override
    public ActionParameters params() {
        return parameters;
    }

    @Parameters(commandDescription = "Randomize reads in single-end or paired-end fastq files.")
    public static final class AParameters extends ActionParametersWithOutput {
        @Parameter(description = "input_file_R1.fastq[.gz] [input_file_R2.fastq[.gz]] " +
                "output_file_R1.fastq[.gz] [output_file_R2.fastq[.gz]]", variableArity = true)
        public List<String> parameters = new ArrayList<>();

        @SuppressWarnings("unchecked")
        public SequenceReaderCloseable<SequenceRead> getReader() throws IOException {
            if (parameters.size() == 2)
                return (SequenceReaderCloseable) new SingleFastqReader(parameters.get(0));
            else
                return (SequenceReaderCloseable) new PairedFastqReader(parameters.get(0), parameters.get(1));
        }

        @SuppressWarnings("unchecked")
        public SequenceWriter<SequenceRead> getWriter() throws IOException {
            if (parameters.size() == 2)
                return (SequenceWriter) new SingleFastqWriter(parameters.get(1));
            else
                return (SequenceWriter) new PairedFastqWriter(parameters.get(2), parameters.get(3));
        }

        @Override
        protected List<String> getOutputFiles() {
            if (parameters.size() == 2)
                return parameters.subList(1, 2);
            else
                return parameters.subList(2, 4);
        }

        @Override
        public void validate() {
            if (parameters.size() != 2 && parameters.size() != 4)
                throw new ParameterException("Wrong number of parameters.");
        }
    }
}
