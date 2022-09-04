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
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ReleaseInfo {

    // Load All Information
    private static final Properties release_info = new Properties();

    static {
        try {
            URL url = ReleaseInfo.class.getResource("/kode-release-info.txt");
            assert url != null;
            try (final InputStream is = url.openStream()) {
                if (is != null) {
                    release_info.load(is);
                }
            }
        } catch (IOException ignored) {
            // Do nothing
        }
    }

    // Full Display Name
    public static final String FULL_NAME = release_info.getProperty("Application-full-name");

    // Short Name
    public static final String NAME = release_info.getProperty("Application-name");

    // Version Number
    public static final String VERSION = release_info.getProperty("Version");

    // Build Date
    public static final Date BUILD_DATE;

    static {
        Date tempBuildDate = new Date();
        try {
            tempBuildDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                    .parse(release_info.getProperty("Build-date"));
        } catch (ParseException ignored) {
            // Do nothing
        }
        BUILD_DATE = tempBuildDate;
    }
}
