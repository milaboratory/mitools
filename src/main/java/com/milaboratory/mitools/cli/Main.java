package com.milaboratory.mitools.cli;

public class Main {
    public static void main(String[] args) throws Exception {
        JCommanderBasedMain main = new JCommanderBasedMain("mitools",
                new MergeAction(),
                new TrimAction(),
                new RCAction(),
                new RenameAction(),
                new ProcessAction());
        main.main(args);
    }
}
