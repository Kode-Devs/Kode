package org.kodedevs.core.internal.parser;

import org.kodedevs.core.internal.runtime.Source;
import org.kodedevs.core.internal.ast.nodes.ExprNode;
import org.kodedevs.core.internal.ast.nodes.StmtNode;
import org.kodedevs.core.internal.ast.nodes.expr.BinaryExprNode;
import org.kodedevs.core.internal.ast.nodes.expr.LiteralExprNode;
import org.kodedevs.core.internal.ast.nodes.expr.UnaryExprNode;
import org.kodedevs.core.internal.ast.nodes.stmt.ExpressionStmtNode;

import java.util.ArrayList;
import java.util.List;

public class ASTGenerator {

    // Private Fields
    private final SourceTokenizer sourceTokenizer;
    private Token currentToken;

    // Constructor
    public ASTGenerator(Source sourceCode) {
        sourceTokenizer = new SourceTokenizer(sourceCode);
        scanNextToken();
    }

    public Source sourceCode() {
        return sourceTokenizer.sourceCode();
    }

    // ------------------------------------------------------------------------------------------------- parser fns

    public List<StmtNode> generate() throws ParseException {
        List<StmtNode> statements = new ArrayList<>();

        while (currentToken.tokenType() != TokenType.TOKEN_EOF) {
            statements.add(statement());
        }
        return statements;
    }

    // ------------------------------------------------------------------------------------------------- statement

    private StmtNode statement() {
        return expressionStatement();
    }

    private StmtNode expressionStatement() {
        ExprNode expr = expression();
        consume(TokenType.TOKEN_SEMICOLON, "Expect ';' after expression.");
        return new ExpressionStmtNode(expr);
    }

    // ------------------------------------------------------------------------------------------------- expression

    private ExprNode expression() {
        ExprNode expr = logical_or();

        // TODO: Assignment operator

        return expr;
    }

    private ExprNode logical_or() {
        ExprNode left = logical_and();

        while (currentToken.tokenType() == TokenType.TOKEN_OR) {
            Token operator = currentToken;
            scanNextToken(); // Skip operator

            ExprNode right = logical_and();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode logical_and() {
        ExprNode left = relational_eq();

        while (currentToken.tokenType() == TokenType.TOKEN_AND) {
            Token operator = currentToken;
            scanNextToken(); // Skip operator

            ExprNode right = relational_eq();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode relational_eq() {
        ExprNode left = relational_comp();

        while (currentToken.tokenType() == TokenType.TOKEN_BANG_EQUAL || currentToken.tokenType() == TokenType.TOKEN_EQUAL_EQUAL) {
            Token operator = currentToken;
            scanNextToken(); // Skip operator

            ExprNode right = relational_comp();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode relational_comp() {
        ExprNode left = operator_shift();

        while (currentToken.tokenType() == TokenType.TOKEN_GREATER || currentToken.tokenType() == TokenType.TOKEN_GREATER_EQUAL || currentToken.tokenType() == TokenType.TOKEN_LESS || currentToken.tokenType() == TokenType.TOKEN_LESS_EQUAL) {
            Token operator = currentToken;
            scanNextToken(); // Skip operator

            ExprNode right = operator_shift();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode operator_shift() {
        ExprNode left = operator_additive();

        while (currentToken.tokenType() == TokenType.TOKEN_LEFT_SHIFT || currentToken.tokenType() == TokenType.TOKEN_RIGHT_SHIFT) {
            Token operator = currentToken;
            scanNextToken(); // Skip operator

            ExprNode right = operator_additive();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode operator_additive() {
        ExprNode left = operator_multiplicative();

        while (currentToken.tokenType() == TokenType.TOKEN_MINUS || currentToken.tokenType() == TokenType.TOKEN_PLUS) {
            Token operator = currentToken;
            scanNextToken(); // Skip operator

            ExprNode right = operator_multiplicative();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode operator_multiplicative() {
        ExprNode left = operator_unary();

        while (currentToken.tokenType() == TokenType.TOKEN_SLASH || currentToken.tokenType() == TokenType.TOKEN_BACKSLASH || currentToken.tokenType() == TokenType.TOKEN_STAR || currentToken.tokenType() == TokenType.TOKEN_MOD) {
            Token operator = currentToken;
            scanNextToken(); // Skip operator

            ExprNode right = operator_unary();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode operator_unary() {
        if (currentToken.tokenType() == TokenType.TOKEN_NOT || currentToken.tokenType() == TokenType.TOKEN_MINUS || currentToken.tokenType() == TokenType.TOKEN_PLUS) {
            Token operator = currentToken;
            scanNextToken(); // Skip operator

            ExprNode right = operator_unary();
            return new UnaryExprNode(operator, right);
        } else {
            return operator_exponential();
        }
    }

    private ExprNode operator_exponential() {
        ExprNode left = function_call();

        while (currentToken.tokenType() == TokenType.TOKEN_POWER) {
            Token operator = currentToken;
            scanNextToken(); // Skip operator

            ExprNode right = operator_unary();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode function_call() {
        ExprNode caller = atomic();

        // TODO: implement call

        return caller;
    }

    private ExprNode atomic() {
        switch (currentToken.tokenType()) {
            case TOKEN_FALSE, TOKEN_TRUE, TOKEN_NUMBER, TOKEN_STRING -> {
                Token literal = currentToken;
                scanNextToken(); // Skip keyword
                return new LiteralExprNode(literal);
            }
            default -> {
                errorAtCurrent("Expect expression.");
                return null;
            }
        }
    }


    // ------------------------------------------------------------------------------------------------- navigator fns

    private void scanNextToken() {
        currentToken = sourceTokenizer.scanTokenOnDemand();
    }

    private void consume(TokenType type, String errMsg) {
        if (currentToken.tokenType() == type) {
            scanNextToken();
            return;
        }

        errorAtCurrent(errMsg);
    }

    // ------------------------------------------------------------------------------------------------- error fns

    private void errorAtCurrent(String errMsg) {
        errorAt(currentToken, errMsg);
    }

    private void errorAt(Token identifierToken, String errMsg) {
        throw new ParseException(errMsg, identifierToken.offset());
    }
}
