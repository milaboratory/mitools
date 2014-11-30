package com.milaboratory.mitools.cli;

import java.io.PrintStream;

public interface ActionHelper {
    PrintStream getDefaultPrintStream();

    String getCommandLineArguments();
}
