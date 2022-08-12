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

import org.kodedevs.kode.sdk.CodeSource;
import org.kodedevs.kode.sdk.Lexer;
import org.kodedevs.kode.sdk.Parser;
import org.kodedevs.kode.utils.ReleaseInfo;

import java.util.Scanner;

public class CLIToolKit {

    private static final String APP_VERSION = ReleaseInfo.getVersion();
    private static final String JAVA_VM_NAME = System.getProperty("java.vm.name");      // OpenJDK 64-Bit Server VM
    private static final String JAVA_VENDOR = System.getProperty("java.vendor");        // Azul Systems, Inc.
    private static final String JAVA_VERSION = System.getProperty("java.version");      // 17.0.4

    private static final String DEFAULT_INTRO =
            "Welcome to Kode " + APP_VERSION + " (" + JAVA_VM_NAME + ", Java " + JAVA_VERSION + ")."
                    + "\nType in expression for evaluation. Or try :help";

    public static final String DEFAULT_END_NOTE =
            "Thank you for using our product ...";

    private static final String DEFAULT_PROMPT = "kode> ";

    private static final String DEFAULT_HELP = "No available help";


    public static void runInteractiveCLI() {
        // Print Intro Message
        System.out.println(DEFAULT_INTRO);

        String input;
        try (final Scanner sc = new Scanner(System.in)) {
            loop:
            for (; ; ) {
                System.out.println();
                System.out.print(DEFAULT_PROMPT);         // Print Prompt

                input = sc.nextLine();                      // Read Line

                switch (input.trim()) {
                    case ":quit", ":q":
                        break loop;                         // Exit the loop
                    case ":help", ":h":
                        System.out.println(DEFAULT_HELP);   // Print Help
                        break;
                    default:                                // Evaluate
                        try {
                            System.out.println("res: " + evaluateInputString(input));
                        } catch (Exception e) {
                            System.out.println("err: " + e.getLocalizedMessage());
                        }
                }
            }
        } finally {
            System.out.println();
            System.out.println(DEFAULT_END_NOTE);
        }
    }

    private static Object evaluateInputString(final String inputStr) throws Exception {
        return new Parser(new Lexer(
                CodeSource.fromRawString(inputStr, true)))
                .parse();
    }
}
