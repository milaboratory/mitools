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

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import com.milaboratory.core.io.sequence.fastq.SingleFastqReader;
import com.milaboratory.core.io.sequence.fastq.SingleFastqWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MiCLIUtil {
    public static SingleFastqWriter createSingleWriter(String fileName) throws IOException {
        if (fileName.equals("-"))
            return new SingleFastqWriter(System.out);
        return new SingleFastqWriter(fileName);
    }

    public static SingleFastqReader createSingleReader(String fileName, boolean replaceWildcards) throws IOException {
        if (fileName.equals("-"))
            return new SingleFastqReader(System.in, replaceWildcards);
        return new SingleFastqReader(fileName, replaceWildcards);
    }

    public static class FileConverter implements IStringConverter<File> {
        @Override
        public File convert(String value) {
            return new File(value);
        }
    }

    public static class FileValueValidator implements IValueValidator<File> {
        @Override
        public void validate(String name, File value) throws ParameterException {
            if (!value.exists())
                throw new ParameterException("File not found: " + value);
        }
    }

    public static class FileValidator implements IParameterValidator {
        @Override
        public void validate(String name, String value) throws ParameterException {
            if (!new File(value).exists())
                throw new ParameterException("File not found: " + value);
        }
    }

    public static class List2Validator implements IValueValidator<List> {
        @Override
        public void validate(String name, List value) throws ParameterException {
            if (value.size() > 2)
                throw new ParameterException("Too many parameters.");
            if (value.size() < 2)
                throw new ParameterException("More parameters required.");
        }
    }
}
