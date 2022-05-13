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

package org.kodedevs.kode.internal.runtime;

import org.kodedevs.kode.internal.ast.expr.Binary;
import org.kodedevs.kode.internal.ast.expr.Expr;
import org.kodedevs.kode.internal.ast.expr.Unary;
import org.kodedevs.kode.internal.ast.stmt.Stmt;
import org.kodedevs.kode.internal.objects.ScriptObject;

public class ScriptRuntime implements Expr.Visitor<ScriptObject>, Stmt.Visitor<Void> {

    @Override
    public ScriptObject visitBinaryExpr(Binary expr) {
        evaluate(expr.left);
        return evaluate(expr.right);
    }

    @Override
    public ScriptObject visitUnaryExpr(Unary expr) {
        return evaluate(expr.right);
    }
}
