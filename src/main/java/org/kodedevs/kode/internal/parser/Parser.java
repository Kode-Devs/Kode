package org.kodedevs.kode.internal.parser;

import org.kodedevs.kode.internal.ast.nodes.ExprNode;
import org.kodedevs.kode.internal.ast.nodes.StmtNode;
import org.kodedevs.kode.internal.ast.nodes.expr.BinaryExprNode;
import org.kodedevs.kode.internal.ast.nodes.expr.LiteralExprNode;
import org.kodedevs.kode.internal.ast.nodes.expr.UnaryExprNode;
import org.kodedevs.kode.internal.ast.nodes.stmt.ExpressionStmt;
import org.kodedevs.kode.internal.errors.ParseException;
import org.kodedevs.kode.internal.source.Source;

import java.util.ArrayList;
import java.util.List;

import static org.kodedevs.kode.internal.parser.TokenType.*;

public final class Parser {

    private int k = 0;
    private final Lexer lexer;

    private List<StmtNode> statements;

    public Parser(Source source) {
        lexer = new Lexer(source);
    }

    public List<StmtNode> compile() {
        if (statements == null) {
            statements = statements();
        }
        return statements;
    }

    private List<StmtNode> statements() throws ParseException {
        List<StmtNode> statements = new ArrayList<>();

        while (!isAtEnd()) {
            statements.add(statement());
        }
        return statements;
    }

    private StmtNode statement() {
        return expressionStatement();
    }

    private StmtNode expressionStatement() {
        ExprNode expr = expression();
        consume(TOKEN_SEMICOLON, "Expect ';' after expression.");
        return new ExpressionStmt(expr);
    }

    private ExprNode expression() {
        ExprNode expr = logical_or();

        // TODO: Assignment operator

        return expr;
    }

    private ExprNode logical_or() {
        ExprNode left = logical_and();

        while (match(TOKEN_OR)) {
            Token operator = previous();
            ExprNode right = logical_and();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode logical_and() {
        ExprNode left = relational_eq();

        while (match(TOKEN_AND)) {
            Token operator = previous();
            ExprNode right = relational_eq();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode relational_eq() {
        ExprNode left = relational_comp();

        while (match(TOKEN_BANG_EQUAL, TOKEN_EQUAL_EQUAL)) {
            Token operator = previous();
            ExprNode right = relational_comp();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode relational_comp() {
        ExprNode left = operator_shift();

        while (match(TOKEN_GREATER, TOKEN_GREATER_EQUAL, TOKEN_LESS, TOKEN_LESS_EQUAL)) {
            Token operator = previous();
            ExprNode right = operator_shift();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode operator_shift() {
        ExprNode left = operator_additive();

        while (match(TOKEN_LEFT_SHIFT, TOKEN_RIGHT_SHIFT)) {
            Token operator = previous();
            ExprNode right = operator_additive();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode operator_additive() {
        ExprNode left = operator_multiplicative();

        while (match(TOKEN_MINUS, TOKEN_PLUS)) {
            Token operator = previous();
            ExprNode right = operator_multiplicative();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode operator_multiplicative() {
        ExprNode left = operator_unary();

        while (match(TOKEN_SLASH, TOKEN_BACKSLASH, TOKEN_STAR, TOKEN_MOD)) {
            Token operator = previous();
            ExprNode right = operator_unary();
            left = new BinaryExprNode(left, operator, right);
        }

        return left;
    }

    private ExprNode operator_unary() {
        if (match(TOKEN_NOT, TOKEN_MINUS, TOKEN_PLUS)) {
            Token operator = previous();
            ExprNode right = operator_unary();
            return new UnaryExprNode(operator, right);
        } else {
            return operator_exponential();
        }
    }

    private ExprNode operator_exponential() {
        ExprNode left = function_call();

        while (match(TOKEN_POWER)) {
            Token operator = previous();
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
        if (match(TOKEN_FALSE, TOKEN_TRUE, TOKEN_NUMBER, TOKEN_STRING)) return new LiteralExprNode(previous());

        throw errorAtCurrent("Expect expression.");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(TokenType type, String errMsg) {
        if (check(type)) return advance();

        throw errorAtCurrent(errMsg);
    }

    private boolean check(TokenType type) {
        if (!isAtEnd()) return false;

        return peek().tokenType() == type;
    }

    private Token peek() {
        return lexer.getToken(k);
    }

    private Token previous() {
        return lexer.getToken(k - 1);
    }

    private Token advance() {
        if (!isAtEnd()) k++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().tokenType() == TokenType.TOKEN_EOF;
    }

    private ParseException errorAtCurrent(String errMsg) {
        return errorAt(errMsg, peek());
    }

    private ParseException errorAt(String errMsg, Token identifierToken) {
        return errorAt(errMsg, identifierToken.offset());
    }

    private ParseException errorAt(String errMsg, int offset) {
        return new ParseException(errMsg, offset);
    }
}
