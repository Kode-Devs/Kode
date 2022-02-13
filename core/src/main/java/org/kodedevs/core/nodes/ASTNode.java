package org.kodedevs.core.nodes;

import hu.webarticum.treeprinter.TreeNode;
import org.kodedevs.core.internal.Resolver;
import org.kodedevs.core.internal.RuntimeState;

import java.util.Collections;
import java.util.List;

public interface ASTNode extends TreeNode {

    default void resolve(Resolver resolver) {
        // Do Nothing
    }

    Object evaluate(RuntimeState runtimeState);

    @Override
    default String content() {
        return getClass().getSimpleName();
    }

    @Override
    default List<TreeNode> children() {
        return Collections.emptyList();
    }
}
