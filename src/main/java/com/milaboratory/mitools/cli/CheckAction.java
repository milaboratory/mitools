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
import com.milaboratory.core.io.sequence.PairedRead;
import com.milaboratory.core.io.sequence.SingleRead;
import com.milaboratory.core.io.sequence.fastq.FastqRecordsReader;
import com.milaboratory.core.io.sequence.fastq.PairedFastqWriter;
import com.milaboratory.core.io.sequence.fastq.QualityFormat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class CheckAction implements Action {
    final AParameters params = new AParameters();

    @Override
    public void go(ActionHelper helper) throws Exception {
        Pattern r1Pattern = params.getHeaderPatternR1();
        Pattern r2Pattern = params.getHeaderPatternR2();

        long missedR1 = 0, missedR2 = 0, skippedR1 = 0, skippedR2 = 0, mismatchedHeaders = 0, correct = 0;

        try (FastqRecordsReader reader1 = new FastqRecordsReader(false,
                getInputStream(params.getInputR1()), 524288, false, true);
             FastqRecordsReader reader2 = new FastqRecordsReader(false,
                     getInputStream(params.getInputR2()), 524288, false, true);
             PairedFastqWriter writer = params.doOutput() ?
                     new PairedFastqWriter(params.getOutputR1(), params.getOutputR2()) :
                     null
        ) {
            OUTER:
            while (true) {
                boolean r1b = reader1.nextRecord(true);
                boolean r2b = reader2.nextRecord(true);

                if (!r1b && !r2b)
                    break;

                if (!r1b) {
                    ++missedR1;
                    continue;
                }

                if (!r2b) {
                    ++missedR2;
                    continue;
                }

                SingleRead r1 = reader1.createRead(0, QualityFormat.Phred33);
                SingleRead r2 = reader2.createRead(0, QualityFormat.Phred33);

                Matcher matcherR1 = r1Pattern.matcher(r1.getDescription());
                Matcher matcherR2 = r2Pattern.matcher(r2.getDescription());

                if (!matcherR1.matches()) {
                    ++skippedR1;
                    continue;
                }

                if (!matcherR2.matches()) {
                    ++skippedR2;
                    continue;
                }

                for (int i = 0; i < matcherR1.groupCount(); i++) {
                    if (!matcherR1.group(i + 1).equals(matcherR2.group(i + 1))) {
                        mismatchedHeaders++;
                        continue OUTER;
                    }
                }

                ++correct;

                if (writer != null)
                    writer.write(new PairedRead(r1, r2));
            }
        }

        System.out.println("Missed R1: " + missedR1);
        System.out.println("Missed R2: " + missedR2);

        System.out.println("Skipped R1: " + skippedR1);
        System.out.println("Skipped R1: " + skippedR2);

        System.out.println("Mismatched header: " + mismatchedHeaders);

        System.out.println("Correct reads: " + correct);
    }

    public InputStream getInputStream(String fileName) throws IOException {
        if (fileName.endsWith(".gz"))
            return new GZIPInputStream(new BufferedInputStream(new FileInputStream(fileName), 524288));
        else
            return new BufferedInputStream(new FileInputStream(fileName), 524288);
    }

    @Override
    public String command() {
        return "check";
    }

    @Override
    public ActionParameters params() {
        return params;
    }

    @Parameters(commandDescription = "Checks paired-end read file for correctness and fixes it if possible.", optionPrefixes = "-")
    public static final class AParameters extends ActionParameters {
        @Parameter(description = "input_file_R1.fastq[.gz] input_file_R2.fastq[.gz] " +
                "[fixed_output_file_R1.fastq[.gz] fixed_output_file_R2.fastq[.gz]]",
                variableArity = true)
        public List<String> parameters = new ArrayList<>();

        @Parameter(description = "Header pattern.",
                names = {"-d", "--header-pattern"})
        String headerStructure = "^(.*)$";

        public Pattern getHeaderPatternR1() {
            return Pattern.compile(headerStructure);
        }

        public Pattern getHeaderPatternR2() {
            return Pattern.compile(headerStructure);
        }

        public boolean doOutput() {
            return parameters.size() == 4;
        }

        String getInputR1() {
            return parameters.get(0);
        }

        String getInputR2() {
            return parameters.get(1);
        }

        String getOutputR1() {
            return parameters.get(2);
        }

        String getOutputR2() {
            return parameters.get(3);
        }

        @Override
        public void validate() {
            if (parameters.size() != 2 && parameters.size() != 4)
                throw new ParameterException("Wrong number of parameters.");
        }
    }
}
