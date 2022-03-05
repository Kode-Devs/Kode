package org.kodedevs.kode.internal.ast.nodes;

import org.kodedevs.kode.internal.runtime.RuntimeState;

import java.util.Collections;
import java.util.List;

public interface ASTNode {

    Object evaluate(RuntimeState runtimeState);

    default String getName() {
        return getClass().getSimpleName();
    }

    default List<ASTNode> getChildren() {
        return Collections.emptyList();
    }
}
