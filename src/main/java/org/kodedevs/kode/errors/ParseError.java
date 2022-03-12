package org.kodedevs.kode.errors;

import org.kodedevs.kode.api.KodeException;

public class ParseError extends KodeException {

    public ParseError(String errMsg, int offset) {
        super(errMsg, null, offset);
    }
}
