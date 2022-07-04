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

package org.kodedevs.kode.api.repl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {

    public static void main(String[] args) {
        Console sysConsole = System.console();
        BufferedReader bufConsole = new BufferedReader(new InputStreamReader(System.in));
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        if (engine == null) {
            throw new UnsupportedOperationException("No Supported Engine Found");
        }

        System.out.printf("%s version %s%n", engine.getFactory().getLanguageName(), engine.getFactory().getLanguageVersion());

        // Break with Ctrl+C
        while (true) {
            try {
                // read the next input
                System.out.print("> ");
                String line = sysConsole != null
                        ? sysConsole.readLine() : bufConsole.readLine();
                if (line == null) {
                    break;
                }

                // if just a return, loop
                if (line.isBlank()) {
                    continue;
                }

                // evaluate
                Object retVal = engine.eval(line);

                // write output
                System.out.println(retVal);
            } catch (IOException | ScriptException e) {
                // write error
                System.err.println(e.getMessage());
            }
        }

        try {
            bufConsole.close();
        } catch (IOException ignored) {
        }
    }
}
