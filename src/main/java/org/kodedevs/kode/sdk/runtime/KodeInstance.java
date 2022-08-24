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

import org.kodedevs.kode.KodeException;

public abstract class KodeInstance {

    private final KodeType type;

    public KodeInstance(final KodeType type) {
        this.type = type;
    }

    /** Retrieves the type whose instance is this object. */
    public final KodeType getType() {
        return type;
    }

    /** Retrieves a named member of this object. */
    public final KodeInstance getMember(final String name) {
        final var property = findProperty(name);

        if (property == null) {
            throw new KodeException("Unable to find property : '" + name + "'");
        }

        return (KodeInstance) property;
    }

    /** Does this object have a named member? */
    public final boolean hasMember(final String name) {
        return findProperty(name) != null;
    }

    protected abstract Object findProperty(final String propertyName);

    /** Sets a named member in this object. */
    public void setMember(final String name, final KodeInstance value) {
        final var property = findProperty(name);

        if (property == null) {
            throw new KodeException("Unable to find property : '" + name + "'");
        }
    }

    /** Checking whether this object is an instance of the given 'type' object. */
    public boolean isInstanceOf(final KodeType kodeType) {
        return this.getType() == kodeType;
    }

    public static KodeInstance wrapObject(Object o) {
        return null;
    }

    public final Object safeToJava() {
        return safeToJava(Object.class);
    }

    public <T> T safeToJava(Class<T> javaType) {
        return null;
    }
}
