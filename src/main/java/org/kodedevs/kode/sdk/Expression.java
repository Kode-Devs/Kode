/*
 * Copyright 2022 Kode Devs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kodedevs.kode.sdk;

import org.kodedevs.kode.sdk.runtime.KodeInstance;
import org.kodedevs.kode.sdk.ast.*;

public interface Expression {

    default KodeInstance evaluate(RuntimeState runtimeState) {
        // Todo your handling code here
        return null;
    }

    //// Section: Visitor Pattern

    interface Visitor<R> {

        R visit(GroupExpr groupExpr);

        R visit(LiteralExpr literalExpr);

        R visit(FetchExpr fetchExpr);

        R visit(StoreExpr storeExpr);

        R visit(GetterExpr getterExpr);

        R visit(SetterExpr setterExpr);

        R visit(PrefixExpr prefixExpr);

        R visit(PostfixExpr postfixExpr);

        R visit(InfixExpr infixExpr);
    }

    <R> R accept(Visitor<R> visitor);
}
