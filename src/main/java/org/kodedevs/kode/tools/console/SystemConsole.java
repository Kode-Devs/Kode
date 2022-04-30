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

package org.kodedevs.kode.tools.console;

import java.nio.charset.Charset;

public class SystemConsole implements Console {

    /** Encoding to use for line input. */
    private final String encoding;

    /** Encoding to use for line input as a <code>Charset</code>. */
    private final Charset encodingCharset;

    public SystemConsole(String encoding) {
        this.encoding = encoding != null ? encoding : Charset.defaultCharset().name();
        this.encodingCharset = Charset.forName(this.encoding);
    }

    @Override
    public void systemInstall() {
        // Nothing to do!
    }

    @Override
    public void systemUninstall() {
        // Nothing to do!
    }

    @Override
    public String getEncoding() {
        return encoding;
    }

    @Override
    public Charset getEncodingCharset() {
        return encodingCharset;
    }
}
