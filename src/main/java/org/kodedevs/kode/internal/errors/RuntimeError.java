package org.kodedevs.kode.internal.errors;

import org.kodedevs.kode.api.KodeException;

public class RuntimeError extends KodeException {
    public RuntimeError(String errMsg) {
        super(errMsg, null);
    }
}
