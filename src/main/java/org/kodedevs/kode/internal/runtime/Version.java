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

package org.kodedevs.kode.internal.runtime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation to handle version information
 *
 * @author arpan
 */
public final class Version {

    public static final String VERSION_DISPLAY_MSG = "Kode version %s";

    private static byte[] _version;

    static {
        try (InputStream stream = Version.class.getClassLoader().getResourceAsStream("META-INF/VERSION")) {
            if (stream == null) {
                throw new FileNotFoundException();
            }
            _version = stream.readAllBytes();
        } catch (IOException ignored) {
            // Do nothing
        }
    }

    // don't create me
    private Version() {
    }

    /**
     * The current version number as a string.
     *
     * @return version string
     */
    public static String version() {
        return new String(_version);
    }
}
