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

package org.kodedevs.kode.tools;

import org.fusesource.jansi.AnsiConsole;
import org.kodedevs.kode.KodeException;
import org.kodedevs.kode.sdk.*;

import java.util.Scanner;

public class CLIToolKit {

    public static void runInteractiveCLI() {
        // Enable ANSI
        AnsiConsole.systemInstall();

        // Scanner For User Input
        final Scanner sc = new Scanner(System.in);

        while (true) {
            // Print Prompt
            System.out.print("$> ");

            // Read User Input
            final String input = sc.nextLine();

            // If Blank
            if (input.isBlank()) continue;

            // If EXIT
            if (input.equals("\\q")) break;

            // Otherwise
            try {
                final CodeSource cs = CodeSource.fromRawString(input, true);
                final Lexer lexer = new Lexer(cs);
                final Parser parser = new Parser(lexer);

                System.out.println(parser.parse());
            } catch (KodeException k) {
                System.err.println(k.getLocalizedMessage());
            }
        }

        // Disable ANSI
        AnsiConsole.systemUninstall();
    }
}
