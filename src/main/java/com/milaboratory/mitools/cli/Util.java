package com.milaboratory.mitools.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;
import java.util.List;

public class Util {
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
