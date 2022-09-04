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

import org.kodedevs.kode.sdk.CodeSource;
import org.kodedevs.kode.sdk.Lexer;
import org.kodedevs.kode.sdk.Parser;
import org.kodedevs.kode.utils.ReleaseInfo;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.Scanner;
import java.util.concurrent.Callable;

@Command(name = "kode",
        description = "A simple picocli demo",
        versionProvider = Application.DefaultVersionProvider.class,
        mixinStandardHelpOptions = true,
        subcommands = {
                CommandLine.HelpCommand.class,
        })
public class Application implements Callable<Integer> {

    private static final String JAVA_VM_NAME = System.getProperty("java.vm.name");      // OpenJDK 64-Bit Server VM
    private static final String JAVA_VENDOR = System.getProperty("java.vendor");        // Azul Systems, Inc.
    private static final String JAVA_VERSION = System.getProperty("java.version");      // 17.0.4

    @Override
    public Integer call() {     // Run interactive shell
        // Print Intro Message
        System.out.println("Welcome to Kode " + ReleaseInfo.VERSION + " (" + JAVA_VM_NAME + ", Java " + JAVA_VERSION + ").");
        System.out.println("Type in expression for evaluation. Or try :help");

        String input;
        try (final Scanner sc = new Scanner(System.in)) {
            loop:
            for (; ; ) {
                System.out.println();
                System.out.print("kode> ");                         // Print Prompt

                input = sc.nextLine();                              // Read Line

                switch (input.trim()) {
                    case ":quit", ":q":
                        break loop;                                 // Exit the loop
                    case ":help", ":h":
                        System.out.println("No available help");    // Print Help
                        break;
                    default:                                        // Evaluate
                        try {
                            final CodeSource source = CodeSource.fromRawString(input, true);
                            final Lexer lexer = new Lexer(source);
                            final Parser parser = new Parser(lexer);
                            System.out.println("res: " + parser.parse());
                        } catch (Exception e) {
                            System.out.println("err: " + e.getLocalizedMessage());
                        }
                }
            }
        } finally {
            System.out.println();
            System.out.println("Thank you for using our product ...");
        }
        return 0;
    }

    // Entrypoint for the CLI interface
    public static void main(String[] args) {
        // bootstrap the application
        CommandLine cmd = new CommandLine(new Application());
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }

    static class DefaultVersionProvider implements IVersionProvider {

        @Override
        public String[] getVersion() throws Exception {
            return new String[]{
                    ReleaseInfo.FULL_NAME + " version " + ReleaseInfo.VERSION,
                    "JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})",
                    "OS : ${os.name} ${os.version} ${os.arch}"
            };
        }
    }
}
