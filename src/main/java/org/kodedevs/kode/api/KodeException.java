package org.kodedevs.kode.api;

import java.io.Serial;

public abstract class KodeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private String fileName;
    private int offset;

    protected KodeException(String errMsg, String fileName, int offset) {
        this(errMsg, null, fileName, offset);
    }

    protected KodeException(String errMsg, Throwable cause) {
        this(errMsg, cause, null, -1);
    }

    protected KodeException(String errMsg, Throwable cause, String fileName, int offset) {
        super(errMsg, cause);
        this.fileName = fileName;
        this.offset = offset;
    }

    public final String getFileName() {
        return fileName;
    }

    public final void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public final int getOffset() {
        return offset;
    }

    public final void setOffset(int offset) {
        this.offset = offset;
    }
}
