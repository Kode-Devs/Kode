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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Options {
    // don't create me
    private Options() {
    }

    /**
     * Convenience function for getting build properties in a safe way
     *
     * @param name     name of boolean property
     * @param defValue default value of boolean property
     * @return the value of this property converted to a {@code Boolean}
     */
    public static boolean getBooleanProperty(final String name, final boolean defValue) {
        final String result = CONFIGS.getProperty(name);
        return result != null ? Boolean.parseBoolean(result) : defValue;
    }

    /**
     * Convenience function for getting build properties in a safe way
     *
     * @param name name of boolean property
     * @return the value of this property converted to a {@code Boolean}
     */
    public static boolean getBooleanProperty(final String name) {
        return getBooleanProperty(name, false);
    }

    /**
     * Convenience function for getting build properties in a safe way
     *
     * @param name     name of string property
     * @param defValue default value of string property
     * @return the value of this property converted to a {@code String}
     */
    public static String getStringProperty(final String name, final String defValue) {
        final String result = CONFIGS.getProperty(name);
        return result != null ? result : defValue;
    }

    /**
     * Convenience function for getting build properties in a safe way
     *
     * @param name     name of integer property
     * @param defValue default value of integer property
     * @return the value of this property converted to a {@code Integer}
     */
    public static int getIntProperty(final String name, final int defValue) {
        try {
            return Integer.parseInt(CONFIGS.getProperty(name));
        } catch (NumberFormatException ex) {
            return defValue;
        }
    }

    static final String BUILD_PROPS = "META-INF/kode.properties";
    private static final Properties CONFIGS = new Properties();

    static {
        loadConfiguration(BUILD_PROPS);
    }

    /**
     * Load information from configuration file.
     *
     * @param path Path to the resource
     */
    private static void loadConfiguration(String path) {
        try (InputStream stream = Options.class.getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new FileNotFoundException();
            }
            CONFIGS.load(stream);
        } catch (IOException ignored) {
            // Do nothing
        }
    }
}
