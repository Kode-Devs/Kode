package org.kodedevs.kode.compiler;

import org.kodedevs.kode.common.Recognizer;
import org.kodedevs.kode.common.Token;
import org.kodedevs.kode.common.CharStream;

public class Lexer extends Recognizer<Character> {

    private final CharStream charStream;

    public Lexer(CharStream charStream) {
        this.charStream = charStream;
    }

    public Token nextToken() {
        return null;
    }

    @Override
    protected boolean isAtEnd() {
        return charStream.index() >= charStream.size();
    }

    @Override
    public CharStream getStream() {
        return charStream;
    }

    protected static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    protected static boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    protected static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}
