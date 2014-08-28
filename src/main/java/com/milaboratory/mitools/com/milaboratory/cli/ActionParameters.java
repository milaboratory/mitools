package com.milaboratory.mitools.com.milaboratory.cli;

import com.beust.jcommander.Parameter;

public abstract class ActionParameters {
    @Parameter(names = {"-h", "--help"}, help = true, description = "Displays help for this command.")
    public Boolean help;

    public boolean help() {
        return help != null && help;
    }

    public void validate() {
    }
}
