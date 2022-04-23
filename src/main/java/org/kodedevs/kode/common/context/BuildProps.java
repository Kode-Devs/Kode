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

package org.kodedevs.kode.common.context;

import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.DataConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConversionException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class BuildProps {
    // don't create me
    private BuildProps() {
    }

    /**
     * Convenience function for getting build properties in a safe way
     *
     * @param name     name of boolean property
     * @param defValue default value of boolean property
     * @return the value of this property converted to a {@code Boolean}
     */
    public static boolean getBooleanProperty(final String name, final boolean defValue) {
        try {
            return CONFIGS.getBoolean(name, defValue);
        } catch (ConversionException ex) {
            return defValue;
        }
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
        try {
            return CONFIGS.getString(name, defValue);
        } catch (ConversionException ex) {
            return defValue;
        }
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
            return CONFIGS.getInt(name, defValue);
        } catch (ConversionException ex) {
            return defValue;
        }
    }

    static final String BUILD_PROPS = "META-INF/build.properties";
    private static final DataConfiguration CONFIGS = new DataConfiguration(new BaseConfiguration());

    static {
        loadConfiguration(BUILD_PROPS);
    }

    /**
     * Load information from configuration file.
     *
     * @param path Path to the resource
     */
    private static void loadConfiguration(String path) {
        try (InputStream stream = BuildProps.class.getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new FileNotFoundException();
            }
            PropertiesConfiguration props = new PropertiesConfiguration();
            props.read(new InputStreamReader(stream));
            CONFIGS.append(props);
        } catch (IOException | ConfigurationException ignored) {
            // Do nothing
        }
    }
}
