package com.milaboratory.mitools.com.milaboratory.cli;

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

    public static SingleFastqReader createSingleReader(String fileName) throws IOException {
        if (fileName.equals("-"))
            return new SingleFastqReader(System.in);
        return new SingleFastqReader(fileName);
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
