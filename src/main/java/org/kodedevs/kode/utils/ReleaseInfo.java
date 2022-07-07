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

package org.kodedevs.kode.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReleaseInfo {
    private static final Properties RELEASE_INFO = new Properties();
    private static final String RELEASE_INFO_FILE = "META-INF/kode-release-info.properties";
    private static final String VERSION_KEY = "Version";

    static {
        ClassLoader classLoader = ReleaseInfo.class.getClassLoader();
        try (final InputStream is = classLoader.getResourceAsStream(RELEASE_INFO_FILE)) {
            if (is != null) {
                RELEASE_INFO.load(is);
            }
        } catch (IOException e) {
            // Do nothing
        }
    }

    public static String getVersion() {
        return RELEASE_INFO.getProperty(VERSION_KEY, "");
    }
}
