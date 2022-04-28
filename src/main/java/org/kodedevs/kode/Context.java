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

package org.kodedevs.kode;

import org.kodedevs.kode.console.IConsole;
import org.kodedevs.kode.console.DefaultConsole;

public class Context {

    /** Is debug mode enabled ? */
    public static final boolean DEBUG = Options.getBooleanProperty("debug");

    /**
     * The handler for interactive consoles, set by {@link #installConsole(IConsole)} and accessed by
     * {@link #getConsole()}.
     */
    private static IConsole console;

    /**
     * Gets the Console as set by the {@link #installConsole(IConsole)} method.
     *
     * @return The Console object
     */
    public static IConsole getConsole() {
        if (console == null) {
            console = new DefaultConsole(null);
        }

        return console;
    }

    /**
     * Install the provided Console, first uninstalling any current one (if present).
     *
     * @param console The new Console object
     */
    public static void installConsole(IConsole console) {
        if (Context.console != null) {
            // try uninstalling
            Context.console.systemUninstall();
            Context.console = null;
        }

        // Install the new one
        console.systemInstall();
        Context.console = console;
    }
}
