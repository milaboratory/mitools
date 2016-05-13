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

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.milaboratory.cli.Action;
import com.milaboratory.cli.ActionHelper;
import com.milaboratory.cli.ActionParameters;
import com.milaboratory.core.io.sequence.SequenceRead;
import com.milaboratory.core.io.sequence.SequenceReaderCloseable;
import com.milaboratory.core.io.sequence.SequenceWriter;
import com.milaboratory.core.io.sequence.fastq.PairedFastqReader;
import com.milaboratory.core.io.sequence.fastq.PairedFastqWriter;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.core.io.sequence.fastq.SingleFastqWriter;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitAction implements Action {
    final AParameters params = new AParameters();

    @Override
    @SuppressWarnings("unchecked")
    public void go(ActionHelper helper) throws Exception {
        RandomGenerator gen = new Well19937c();
        int partId = 0;
        try (SequenceReaderCloseable<?> sequenceReader = getSequenceReader()) {
            SequenceRead read = sequenceReader.take();
            OUTER:
            while (true) {
                int chunkSize = generateSize(gen, params.getMinChunkSize(), params.getMaxChunkSize());
                if (read == null)
                    break OUTER;
                try (SequenceWriter writer = getSequenceWriter(partId++)) {
                    while ((chunkSize--) > 0) {
                        if (read == null)
                            break OUTER;
                        writer.write(read);
                        read = sequenceReader.take();
                        if (read == null)
                            break OUTER;
                    }
                }
            }
        }
    }

    @Override
    public String command() {
        return "split";
    }

    @Override
    public ActionParameters params() {
        return params;
    }

    public int generateSize(RandomGenerator generator, int min, int max) {
        if (min == max)
            return min;
        return generator.nextInt(max - min + 1) + min;
    }

    public SequenceWriter<?> getSequenceWriter(int partId) throws IOException {
        if (params.numberOfReads() == 1)
            return new SingleFastqWriter(getOutputFileName(1, partId));
        if (params.numberOfReads() == 2)
            return new PairedFastqWriter(getOutputFileName(1, partId), getOutputFileName(2, partId));
        throw new RuntimeException();
    }

    public String getOutputFileName(int readId, int partId) {
        return params.getOutputFileNamePattern()
                .replace("{P}", partIndexToString(partId))
                .replace("{R}", Integer.toString(readId));
    }

    public String partIndexToString(int val) {
        String s = Integer.toString(val);
        int zeroLength = params.partIndexDigits - s.length();
        if (zeroLength < 0)
            return s;
        char[] c = new char[zeroLength];
        Arrays.fill(c, '0');
        return new String(c) + s;
    }

    public SequenceReaderCloseable<?> getSequenceReader() throws IOException {
        if (params.numberOfReads() == 1)
            return new SingleFastqReader(params.parameters.get(0));
        if (params.numberOfReads() == 2)
            return new PairedFastqReader(params.parameters.get(0), params.parameters.get(1));
        throw new RuntimeException();
    }

    @Parameters(commandDescription = "Splits input fastq file(s) into several parts. \"{P}\" in output file name " +
            "will be substituted with sequential part index, \n\"{R}\" will be substituted with read index (in case " +
            "of one input file will always be substituted with \"1\").", optionPrefixes = "-")
    public static final class AParameters extends ActionParameters {
        @Parameter(description = "input_file_R1.fastq[.gz] [ input_file_R2.fastq[.gz] ] output_file_part{P}_R{R}.fastq[.gz]",
                variableArity = true)
        public List<String> parameters = new ArrayList<>();

        @Parameter(description = "Minimal chunk size (use with --max-chunk-size option for random-sized chunks).",
                names = {"-l", "--min-chunk-size"})
        Integer minChunkSize;

        @Parameter(description = "Maximal chunk size (use with --min-chunk-size option for random-sized chunks).",
                names = {"-u", "--max-chunk-size"})
        Integer maxChunkSize;

        @Parameter(description = "Chunk size (use for fixed size chunks).",
                names = {"-c", "--chunk-size"})
        Integer chunkSize;

        @Parameter(description = "Part index digits.",
                names = {"-d", "--part-digits"})
        int partIndexDigits = 4;

        boolean randomSizedChunks() {
            return chunkSize == null;
        }

        int getMinChunkSize() {
            if (randomSizedChunks())
                return minChunkSize;
            else
                return chunkSize;
        }

        int getMaxChunkSize() {
            if (randomSizedChunks())
                return maxChunkSize;
            else
                return chunkSize;
        }

        int numberOfReads() {
            return parameters.size() - 1;
        }

        String getOutputFileNamePattern() {
            return parameters.get(parameters.size() - 1);
        }

        @Override
        public void validate() {
            if (parameters.size() > 3 || parameters.size() < 2)
                throw new ParameterException("Wrong number of parameters.");

            if (chunkSize == null && minChunkSize == null && maxChunkSize == null)
                throw new ParameterException("Please specify chunk size.");

            if (chunkSize != null) {
                if (minChunkSize != null)
                    throw new ParameterException("--min-chunk-size is not compatible with --chunk-size.");
                if (maxChunkSize != null)
                    throw new ParameterException("--max-chunk-size is not compatible with --chunk-size.");
            } else {
                if (minChunkSize == null)
                    throw new ParameterException("Please specify value for --min-chunk-size.");
                if (maxChunkSize == null)
                    throw new ParameterException("Please specify value for --max-chunk-size.");
            }

            String ofp = getOutputFileNamePattern();

            if (!ofp.contains("{P}"))
                throw new ParameterException("Output file name doesn't contain \"{P}\".");

            if (numberOfReads() == 2 && !ofp.contains("{R}"))
                throw new ParameterException("Output file name doesn't contain \"{R}\" while paired-end input is used.");
        }
    }
}
