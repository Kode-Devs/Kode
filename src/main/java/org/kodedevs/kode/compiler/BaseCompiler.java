package org.kodedevs.kode.compiler;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class BaseCompiler {
    private final Deque<ScopeInfo> scopeStack = new ArrayDeque<>();

    protected final void beginScope() {
        scopeStack.push(new ScopeInfo());
    }

    protected final void endScope() {
        scopeStack.pop();
    }
}
