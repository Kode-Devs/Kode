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

package org.kodedevs.kode.sdk;

import org.kodedevs.kode.KodeException;
import org.kodedevs.kode.sdk.runtime.Instance;

import java.util.*;

public final class RuntimeState {

    //// Section: Symbol Table

    private final Map<String, Instance> globals = new HashMap<>();
    private final LinkedList<Map<String, Instance>> locals = new LinkedList<>();

    // Enter a new scope
    public void beginScope() {
        locals.push(new HashMap<>());
    }

    // Exit the current scope
    public void endScope() {
        locals.pop();
    }

    // Stores a new variable in the symbol table, with an initial value
    public void defineSymbol(final String name, final Instance value) {
        if (locals.isEmpty()) {
            globals.put(name, value);
        } else {
            locals.peek().put(name, value);
        }
    }

    // Assigns a new value to a variable iff the variable is present in the symbol table
    public void assignSymbol(final String name, final Instance value) {
        for (final var local : locals) {
            if (local.containsKey(name)) {
                local.put(name, value);
                return;
            }
        }

        // Check global scope
        if (globals.containsKey(name)) {
            globals.put(name, value);
            return;
        }

        throw new KodeException("Undefined variable '" + name + "'.");
    }

    // Assigns a new value to a variable iff the variable is present in the symbol table at a specific distance
    public void assignSymbolAt(final int distance, final String name, final Instance value) {
        if (distance >= 0 && distance < locals.size()) {
            final var local = locals.get(distance);
            if (local.containsKey(name)) {
                local.put(name, value);
                return;
            }
        }

        // Check global scope
        if (globals.containsKey(name)) {
            globals.put(name, value);
            return;
        }

        throw new KodeException("Undefined variable '" + name + "'.");
    }

    // Retrieves the value of a variable from the symbol table, by using its name
    public Instance retrieveSymbol(final String name) {
        for (final var local : locals) {
            if (local.containsKey(name)) {
                return local.get(name);
            }
        }

        // Check global scope
        if (globals.containsKey(name)) {
            return globals.get(name);
        }

        throw new KodeException("Undefined variable '" + name + "'.");
    }

    // Retrieves the value of a variable from the symbol table from a specific distance, by using its name
    public Instance retrieveSymbolAt(final int distance, final String name) {
        if (distance >= 0 && distance < locals.size()) {
            final var local = locals.get(distance);
            if (local.containsKey(name)) {
                return local.get(name);
            }
        }

        // Check global scope
        if (globals.containsKey(name)) {
            return globals.get(name);
        }

        throw new KodeException("Undefined variable '" + name + "'.");
    }
}
