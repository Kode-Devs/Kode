package org.kodedevs.kode.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;

@Command(name = "kode", synopsisSubcommandLabel = "COMMAND", subcommands = {}, version = "0.1.0", usageHelpAutoWidth = true)
public class App implements Runnable {
    @Spec
    CommandSpec spec;

    @Option(names = {"-V", "--version"}, versionHelp = true, description = "display version info")
    boolean versionInfoRequested;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    @Override
    public void run() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Missing required subcommand");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App())
                .execute(args);
        System.exit(exitCode);
    }
}
