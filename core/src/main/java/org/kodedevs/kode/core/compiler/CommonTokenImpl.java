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

import org.kodedevs.kode.common.Source;
import org.kodedevs.kode.common.Token;
import org.kodedevs.kode.common.TokenType;

public class CommonTokenImpl implements Token {

    private final TokenType type;
    private final int startIdx, stopIdx;
    private final Source tokenSource;

    public CommonTokenImpl(TokenType type, int startIdx, int stopIdx, Source tokenSource) {
        this.type = type;
        this.startIdx = startIdx;
        this.stopIdx = stopIdx;
        this.tokenSource = tokenSource;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public int getStartIndex() {
        return startIdx;
    }

    @Override
    public int getStopIndex() {
        return stopIdx;
    }

    @Override
    public Source getTokenSource() {
        return tokenSource;
    }
}
