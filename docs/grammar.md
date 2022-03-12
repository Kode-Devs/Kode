# Syntax Grammar

The syntactic grammar is used to parse the linear sequence of tokens into the nested syntax tree structure. It starts with the first rule that matches an entire program (or a single REPL entry).

```
program        → declaration* EOF ;
```

## Declarations

A program is a series of declarations

```
declaration    → varDecl
               | statement ;

varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ;
```

## Statements

```
statement      → exprStmt
               | block ;

exprStmt       → expression ";" ;
block          → "{" declaration* "}" ;
```

## Expressions

```
expression     → assignment ;

assignment     → logic_or ;

logic_or       → logic_and ( "or" logic_and )* ;
logic_and      → equality ( "and" equality )* ;
equality       → comparison ( ( "!=" | "==" ) comparison )* ;
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term           → factor ( ( "-" | "+" ) factor )* ;
factor         → unary ( ( "/" | "*" ) unary )* ;

unary          → ( "!" | "-" | "+" ) unary
               | call ;

call           → primary ;
primary        → "true" | "false"
               | NUMBER | STRING | IDENTIFIER
               | "(" expression ")" ;
```

# Lexical Grammar

The lexical grammar is used by the scanner to group characters into tokens.

```
NUMBER         → DIGIT+ ( "." DIGIT+ )? ;
STRING         → "\"" <any char except "\"">* "\"" ;
IDENTIFIER     → ALPHA ( ALPHA | DIGIT )* ;
ALPHA          → "a" ... "z" | "A" ... "Z" | "_" ;
DIGIT          → "0" ... "9" ;
```

## WhiteSpace and Comments

These are ignored charecter-sets

```
WS             → [ \n\t\r]+ ;       // skip
COMMENT        → "//" ~[\r\n]* ;    // skip
```
