/*
 * Copyright 2022 Kode Devs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kodedevs.kode.api.cli;

import org.fusesource.jansi.AnsiConsole;
import org.kodedevs.kode.common.context.Version;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Model.CommandSpec;

@Command(
        synopsisSubcommandLabel = "COMMAND",
        mixinStandardHelpOptions = true,
        usageHelpAutoWidth = true,
        versionProvider = Root.VersionProvider.class,
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

    // IVersionProvider implementation that returns version information
    protected static class VersionProvider implements IVersionProvider {
        @Override
        public String[] getVersion() {
            return String.format("Kode %s", Version.fullVersion()).split("\n");
        }
    }

    // Entrypoint for the CLI interface
    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Root());

        // Dynamically set the executable name for the root command
        cmd.setCommandName("kode");

        // Initialize ANSI
        AnsiConsole.systemInstall();

        // Perform Execution
        int exitCode = cmd.execute(args);

        // Close and Exit
        AnsiConsole.systemUninstall();
        System.exit(exitCode);
    }
}
