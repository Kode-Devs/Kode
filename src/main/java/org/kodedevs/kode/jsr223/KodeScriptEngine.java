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

package org.kodedevs.kode.jsr223;

import javax.script.*;
import java.io.Reader;

/**
 * JSR-223 compliant script engine for Kode. Instances are not created directly, but rather returned
 * through {@link KodeScriptEngineFactory#getScriptEngine()}.
 *
 * @author arpan
 * @see KodeScriptEngineFactory
 */
public class KodeScriptEngine extends AbstractScriptEngine {

    // the factory that created this engine
    private final ScriptEngineFactory factory;

    public KodeScriptEngine(KodeScriptEngineFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return null;
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        return null;
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return factory;
    }
}
