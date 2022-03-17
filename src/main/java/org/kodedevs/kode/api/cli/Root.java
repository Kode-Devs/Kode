package org.kodedevs.kode.api.cli;

import org.kodedevs.kode.common.Globals;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Model.CommandSpec;

@Command(
        synopsisSubcommandLabel = "COMMAND",
        mixinStandardHelpOptions = true,
        usageHelpAutoWidth = true,
        versionProvider = VersionProvider.class,
        sortOptions = false,
        subcommands = {
                HelpCommand.class,
        })
public class Root implements Runnable {
    @Spec
    CommandSpec spec;

    @Override
    public void run() {
        // Required [sub]command to be present
        throw new ParameterException(spec.commandLine(), "Missing required [sub]command");
    }

    // Entrypoint for the CLI interface
    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Root());

        // Dynamically set the executable name for the root command
        cmd.setCommandName(Globals.EXECUTABLE_NAME);

        // Perform Execution
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }
}

class VersionProvider implements IVersionProvider {
    @Override
    public String[] getVersion() {
        return new String[] {Globals.APPLICATION_VERSION};
    }
}
