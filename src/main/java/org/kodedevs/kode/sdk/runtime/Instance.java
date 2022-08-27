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

package org.kodedevs.kode.sdk.runtime;


import org.apache.commons.beanutils.PropertyUtils;

import java.util.NoSuchElementException;

public final class Instance {

    private final Object javaObj;

    //// Section: Constructors

    private Instance(Object javaObj) {
        this.javaObj = javaObj;
    }

    public static <T> Instance of(T javaObj) {
        return new Instance(javaObj);
    }

    //// Section: Property Query Operations

    public Instance get(String name) {
        // Find in wrapped Java Object
        if (javaObj != null) {
            try {
                return Instance.of(PropertyUtils.getProperty(javaObj, name));
            } catch (Exception ignored) {
                // Do nothing
            }
        }

        // Otherwise
        throw new NoSuchElementException(name);
    }

    public void set(String name, Instance value) {
        // Update in wrapped Java Object
        if (javaObj != null) {
            try {
                PropertyUtils.setProperty(javaObj, name, value.safeToJava());
            } catch (Exception ignored) {
                // Do nothing
            }
        }

        // Otherwise
        throw new NoSuchElementException(name);
    }

    //// Section: Convert to Java Object

    public Object safeToJava() {
        return safeToJava(Object.class);
    }

    public <T> T safeToJava(Class<T> cls) {
        throw new UnsupportedOperationException();
    }
}
