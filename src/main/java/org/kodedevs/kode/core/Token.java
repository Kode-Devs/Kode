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

package org.kodedevs.kode.core;

// Represents all Source Tokens
public final class Token {

    private final TokenType tokenType;
    private final int startIdx;
    private final int stopIdx;
    private final CodeSource codeSource;

    // Default Constructor
    public Token(TokenType tokenType, int startIdx, int stopIdx, CodeSource codeSource) {
        this.tokenType = tokenType;
        this.startIdx = startIdx;
        this.stopIdx = stopIdx;
        this.codeSource = codeSource;
    }

    // Gets the token type of this token
    public TokenType getTokenType() {
        return tokenType;
    }

    // Gets the beginning character index (inclusive) of this token i.e., index of the first character
    public int getStartIdx() {
        return startIdx;
    }

    // Gets the ending character index (exclusive) of this token i.e., index of character just after the last character
    public int getStopIdx() {
        return stopIdx;
    }

    // Gets the length of this token
    public int getLength() {
        return stopIdx - startIdx;
    }

    // Gets the lexeme as represented by this token
    public String getLexeme() {
        return new String(codeSource.getChars(), startIdx, stopIdx - startIdx);
    }

    // Gets the literal as represented by this token
    public String getLiteral() {
        return null;
    }

    // Get the line number at which the 1st character of this token was matched
    public int getLineNo() {
        return codeSource.getLineNoAt(startIdx);
    }

    // Get the index of the first character of this token relative to the beginning of the line
    public int getColumnNo() {
        return codeSource.getColumnNoAt(startIdx);
    }

    // Gets the source object associated with this token
    public CodeSource getSource() {
        return codeSource;
    }

    @Override
    public String toString() {
        return String.format("Token(type: %s, lexeme: '%s')", tokenType, getLexeme());
    }
}
