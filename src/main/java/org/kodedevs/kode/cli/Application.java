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

package org.kodedevs.kode.cli;

import org.fusesource.jansi.AnsiConsole;
import org.kodedevs.kode.tools.Shell;
import org.kodedevs.kode.utils.ReleaseInfo;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.IVersionProvider;

@Command(
        synopsisSubcommandLabel = "COMMAND",
        mixinStandardHelpOptions = true,
        usageHelpAutoWidth = true,
        versionProvider = Application.VersionProvider.class,
        sortOptions = false,
        subcommands = {
                HelpCommand.class,
                UpdateCmd.class,
        })
public class Application implements Runnable {

    // Entrypoint for the CLI interface
    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new Application());

        // Dynamically set the executable name for the root command
        cmd.setCommandName("kode");

        // Enable ANSI Support
        AnsiConsole.systemInstall();

        // Perform Execution
        int exitCode = cmd.execute(args);

        // Close and Exit
        AnsiConsole.systemUninstall();
        System.exit(exitCode);
    }

    // Default to shell if no subcommand is mentioned
    @Override
    public void run() {
        // Start REPL Shell
        Shell.start();
    }

    // IVersionProvider implementation that returns version information
    static final class VersionProvider implements IVersionProvider {
        @Override
        public String[] getVersion() {
            String version = ReleaseInfo.getVersion();
            String buildTime = ReleaseInfo.getBuildTime();
            return new String[]{
                    "Kode version " + version,
                    "Built: " + buildTime,
            };
        }
    }
}
