package org.kodedevs.kode.common;

public interface IToken {

    /**
     * Get the text of the token.
     */
    default String getText() {
        return getTokenSource().getString(getStartIndex(), getLength());
    }

    /**
     * Get the token type of the token
     */
    TokenType getType();

    /**
     * The starting character index of the token
     */
    int getStartIndex();

    /**
     * The last character index of the token.
     */
    int getStopIndex();

    /**
     * Get the length of the token, length=1..n
     */
    default int getLength() {
        return getStopIndex() - getStartIndex() + 1;
    }

    /**
     * The line number on which the 1st character of this token was matched, line=1..n
     */
    default int getLine() {
        return getTokenSource().getLine(getStartIndex());
    }

    /**
     * The index of the first character of this token relative to the beginning of the line at which it occurs, 0..n-1
     */
    default int getCharPositionInLine() {
        return getTokenSource().getColumn(getStartIndex());
    }

    /**
     * Gets the source which created this token.
     */
    ISource getTokenSource();
}
