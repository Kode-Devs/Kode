package org.kodedevs.kode.errors;

import org.kodedevs.kode.api.KodeException;

public class RuntimeError extends KodeException {
    public RuntimeError(String errMsg) {
        super(errMsg, null);
    }
}
