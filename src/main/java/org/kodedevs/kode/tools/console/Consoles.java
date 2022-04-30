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

package org.kodedevs.kode.tools.console;

public class Consoles {

    // don't create me
    private Consoles() {
    }

    /**
     * The handler for interactive consoles, set by {@link #installConsole(Console)} and accessed by
     * {@link #getConsole()}.
     */
    private static Console console;

    /**
     * Gets the Console as set by the {@link #installConsole(Console)} method.
     *
     * @return The Console object
     */
    public static Console getConsole() {
        if (console == null) {
            console = new SystemConsole(null);
        }

        return console;
    }

    /**
     * Install the provided Console, first uninstalling any current one (if present).
     *
     * @param console The new Console object
     */
    public static void installConsole(Console console) {
        if (Consoles.console != null) {
            // try uninstalling
            Consoles.console.systemUninstall();
            Consoles.console = null;
        }

        // Install the new one
        console.systemInstall();
        Consoles.console = console;
    }
}
