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

public abstract class KodeType extends KodeInstance {

    public KodeType() {
        super(null);    // TODO: Add Class of class
    }

    /** Checking whether the given object is an instance of 'this' object. */
    public final boolean isInstance(final KodeInstance instance) {
        return instance.isInstanceOf(this);
    }
}
