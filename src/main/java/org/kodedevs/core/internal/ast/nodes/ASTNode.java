package org.kodedevs.core.internal.ast.nodes;

import org.kodedevs.core.internal.Resolver;
import org.kodedevs.core.internal.RuntimeState;

import java.util.Collections;
import java.util.List;

public interface ASTNode {

    default void resolve(Resolver resolver) {
        // Do Nothing
    }

    Object evaluate(RuntimeState runtimeState);

    default String getName() {
        return getClass().getSimpleName();
    }

    default List<ASTNode> getChildren() {
        return Collections.emptyList();
    }
}
