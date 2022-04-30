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

package org.kodedevs.kode.api.jsr223;

import org.kodedevs.kode.internal.runtime.Version;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.List;

/**
 * JSR-223 compliant script engine factory for Kode.
 *
 * @author arpan
 */
public class KodeScriptEngineFactory implements ScriptEngineFactory {

    private static final List<String> names = List.of("kode", "Kode", "kde", "KDE");

    private static final List<String> mimeTypes = List.of("application/x-kode", "text/x-kode");

    private static final List<String> extensions = List.of("kde");

    @Override
    public String getEngineName() {
        return "Kode Script Engine";
    }

    @Override
    public String getEngineVersion() {
        return getLanguageVersion();
    }

    @Override
    public List<String> getExtensions() {
        return extensions;
    }

    @Override
    public List<String> getMimeTypes() {
        return mimeTypes;
    }

    @Override
    public List<String> getNames() {
        return names;
    }

    @Override
    public String getLanguageName() {
        return "Kode";
    }

    @Override
    public String getLanguageVersion() {
        return Version.fullVersion();
    }

    @Override
    public Object getParameter(String key) {
        return switch (key) {
            case ScriptEngine.NAME -> "kode";
            case ScriptEngine.ENGINE -> getEngineName();
            case ScriptEngine.ENGINE_VERSION -> getEngineVersion();
            case ScriptEngine.LANGUAGE -> getLanguageName();
            case ScriptEngine.LANGUAGE_VERSION -> getLanguageVersion();
            case "THREADING" -> "MULTITHREADED";
            default -> null;
        };
    }

    @Override
    public String getMethodCallSyntax(String obj, String m, String... args) {
        return null;
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        return null;
    }

    @Override
    public String getProgram(String... statements) {
        return null;
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new KodeScriptEngine(this);
    }
}
