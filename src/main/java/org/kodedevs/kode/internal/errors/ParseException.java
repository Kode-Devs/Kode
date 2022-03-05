package org.kodedevs.kode.internal.errors;

import org.kodedevs.kode.api.KodeException;

public class ParseException extends KodeException {

    public ParseException(String errMsg, int offset) {
        super(errMsg, null, offset);
    }
}
