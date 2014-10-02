package com.milaboratory.mitools.cli;

import com.milaboratory.mitools.com.milaboratory.cli.JCommanderBasedMain;

public class Main {
    public static void main(String[] args) throws Exception {
        JCommanderBasedMain main = new JCommanderBasedMain("mitools",
                new MergeAction(),
                new TrimAction(),
                new RCAction(),
                new RenameAction());
        main.main(args);
    }
}
