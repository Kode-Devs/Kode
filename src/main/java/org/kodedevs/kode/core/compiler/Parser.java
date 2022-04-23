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

package org.kodedevs.kode.core.compiler;

import org.kodedevs.kode.common.runtime.Token;
import org.kodedevs.kode.core.streams.TokenStream;

import static org.kodedevs.kode.common.runtime.TokenType.EOF;

public class Parser extends Recognizer<Token> {

    private final TokenStream tokenStream;

    public Parser(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }

    @Override
    protected boolean isAtEnd() {
        return peek().getType() == EOF;
    }

    @Override
    public TokenStream getStream() {
        return tokenStream;
    }
}
