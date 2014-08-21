package com.milaboratory.mitools.cli;

public class Main {
    public static void main(String[] args) throws Exception {
        JCommanderMain main = new JCommanderMain("mitools", new MergeAction(), new TrimAction());
        main.main(args);
    }
}
