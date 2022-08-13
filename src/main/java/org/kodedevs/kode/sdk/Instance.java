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

public interface Instance {

    // Retrieves the type whose instance is this object.
    InstanceType getType();

    // Checking whether this object is an instance of the given 'type' object.
    default boolean isInstanceOf(final InstanceType instanceType) {
        return this.getType() == instanceType;
    }

    // Retrieves a named member of this object.
    Instance getMember(final String name);

    // Retrieves an indexed member of this object.
    Instance getSlot(final int index);

    // Does this object have a named member?
    boolean hasMember(final String name);

    // Does this object have an indexed member?
    boolean hasSlot(final int index);

    // Sets a named member in this object.
    void setMember(final String name, final Instance value);

    // Sets an indexed member in this object.
    void setSlot(final int index, final Instance value);
}
