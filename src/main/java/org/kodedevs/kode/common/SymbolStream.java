package org.kodedevs.kode.common;

public interface SymbolStream<Symbol> {

    String UNKNOWN_SOURCE_NAME = "<unknown>";

    /**
     * Consumes the current symbol in the stream.
     *
     * @throws IllegalStateException if it fails to consume the current token
     */
    void consume();

    /**
     * Gets the value of the symbol at offset {@code i} relative to the current position. When {@code i==-1}, returns
     * the value of the previously read symbol in the * stream. When {@code i==0}, returns the value of the current
     * symbol in the stream, and so on.
     *
     * @return value of the symbol at given offset
     * @throws IndexOutOfBoundsException if value of {@code i} do not belong to {@code [-1..n)} where {@code n} depends
     *                                   on its underlying implementation.
     */
    Symbol lookAhead(int i);

    /**
     * Return the index into the stream of the input symbol referred to by the current symbol.
     */
    int index();

    /**
     * Returns the total number of symbols in the stream.
     *
     * @throws UnsupportedOperationException if the size of the stream is unknown.
     */
    default int size() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the name of the underlying symbol source. If such a name is not known, this method returns {@link
     * #UNKNOWN_SOURCE_NAME} by default.
     */
    default String getSourceName() {
        return UNKNOWN_SOURCE_NAME;
    }
}
