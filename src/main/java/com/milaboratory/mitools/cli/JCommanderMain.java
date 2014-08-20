package com.milaboratory.mitools.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.HashMap;
import java.util.Map;

public class JCommanderMain {
    protected final Map<String, Action> actions = new HashMap<>();
    protected final String command;

    protected void reg(Action a) {
        actions.put(a.command(), a);
    }

    protected JCommanderMain(String command, Action... actions) {
        this.command = command;
        for (Action action : actions)
            reg(action);
    }

    public void main(String... args) throws Exception {
        Action action;
        MainParameters mainParameters = new MainParameters();
        JCommander commander = new JCommander(mainParameters);
        commander.setProgramName(command);

        for (Action a : actions.values())
            commander.addCommand(a.command(), a.params());

        commander.parse(args);

        if (mainParameters.help != null) {
            StringBuilder builder = new StringBuilder();
            commander.usage(builder);
            System.err.print(builder);
            return;
        }

        final String parsedCommand = commander.getParsedCommand();

        if (parsedCommand == null || !actions.containsKey(parsedCommand)) {
            if (parsedCommand == null)
                System.err.println("No command specified.");
            else
                System.err.println("Command " + parsedCommand + " not supported.");
            System.err.println("Use -h option to get a list of supported commands.");
            return;
        }

        action = actions.get(parsedCommand);

        if (action.params().help != null) {
            StringBuilder builder = new StringBuilder();
            commander.usage(parsedCommand, builder);
            System.err.print(builder);
            return;
        }

        action.go();
    }

    public static final class MainParameters {
        @Parameter(names = {"-h", "--help"}, help = true, description = "Displays this help message.")
        public Boolean help;
    }
}
