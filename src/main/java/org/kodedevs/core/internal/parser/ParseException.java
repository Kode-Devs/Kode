package org.kodedevs.core.internal.parser;

import org.kodedevs.core.api.KodeException;

public class ParseException extends KodeException {

    public ParseException(String errMsg, int offset) {
        super(errMsg, null, offset);
    }
}
