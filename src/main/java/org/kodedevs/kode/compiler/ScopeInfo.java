package org.kodedevs.kode.compiler;

import java.util.HashMap;
import java.util.Map;

public class ScopeInfo {
    private final Map<String, SymInfo> table = new HashMap<>();

    public void addGlobal(String name) {
        throw new UnsupportedOperationException();
    }

    public void addParam(String name) {
        throw new UnsupportedOperationException();
    }
}
