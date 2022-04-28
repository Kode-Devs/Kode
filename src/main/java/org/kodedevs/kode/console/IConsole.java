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

package org.kodedevs.kode.console;

import java.nio.charset.Charset;

public interface IConsole {

    /**
     * Complete initialization of the Console environment.
     */
    void systemInstall();

    /**
     * Uninstall the Console (if possible).
     */
    void systemUninstall();

    /**
     * Name of the encoding, normally supplied during initialisation, and used for line input. This
     * may not be the canonical name of the codec returned by {@link #getEncodingCharset()}.
     *
     * @return name of the encoding in use.
     */
    String getEncoding();

    /**
     * Accessor for encoding to use for line input as a <code>Charset</code>.
     *
     * @return Charset of the encoding in use.
     */
    Charset getEncodingCharset();
}
