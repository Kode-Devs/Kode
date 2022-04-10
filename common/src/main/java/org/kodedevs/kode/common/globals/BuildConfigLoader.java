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

package org.kodedevs.kode.common.globals;

import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.DataConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BuildConfigLoader {
    static final String BUILD_PROPS = "META-INF/build.properties";

    private static final Logger log = LoggerFactory.getLogger(BuildConfigLoader.class);
    static final DataConfiguration CONFIGS = new DataConfiguration(new BaseConfiguration());

    static {
        loadConfiguration(BUILD_PROPS);
    }

    /**
     * Load information from configuration file.
     *
     * @param path Path to the resource
     */
    private static void loadConfiguration(String path) {
        try (InputStream stream = BuildConfigLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new FileNotFoundException();
            }
            PropertiesConfiguration props = new PropertiesConfiguration();
            props.read(new InputStreamReader(stream));
            CONFIGS.append(props);
        } catch (IOException | ConfigurationException ex) {
            log.error("Failed to load config resource: " + path, ex);
        }

        log.debug("Configuration file loaded: {}", path);
    }
}
