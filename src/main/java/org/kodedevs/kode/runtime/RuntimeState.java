package org.kodedevs.kode.runtime;

import javax.script.Bindings;
import javax.script.SimpleBindings;
import java.util.Objects;
import java.util.Stack;

public final class RuntimeState {
    private Bindings GLOBAL = new SimpleBindings();
    private final Stack<Bindings> LOCAL = new Stack<>();

    public void beginScope() {
        LOCAL.push(new SimpleBindings());
    }

    public void endScope() {
        LOCAL.pop();
    }

    public Object lookup(String name) {
        for (int i = LOCAL.size(); i > 0; i--) {
            var localScope = LOCAL.get(i - 1);
            if (localScope.containsKey(name)) {
                return localScope.get(name);
            }
        }

        if (GLOBAL.containsKey(name)) {
            return GLOBAL.get(name);
        }

        // throw
        return null;
    }

    public void bind(String name, String value, boolean toGlobal) {
        if(toGlobal || LOCAL.isEmpty()) {
            GLOBAL.put(name, value);
        } else {
            LOCAL.peek().put(name, value);
        }
    }

    public void rebind(String name, String value) {
        for (int i = LOCAL.size(); i > 0; i--) {
            Bindings localScope = LOCAL.get(i - 1);
            if (localScope.containsKey(name)) {
                localScope.replace(name, value);
                return;
            }
        }

        if (GLOBAL.containsKey(name)) {
            GLOBAL.replace(name, value);
        }

        // throw
    }

    public void setGlobal(Bindings newGlobal) {
        GLOBAL = Objects.requireNonNull(newGlobal);
    }

    public Bindings getGlobal() {
        return GLOBAL;
    }
}
