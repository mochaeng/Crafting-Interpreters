# Representing Code

## Context-Free Grammars

In the last chapter, the formalism for defining the lexical grammar was called a _regular language_. That was fine for our scanner, but regular languages aren't powerful enough to handle expressions which can nest arbitrarily deeply.

We need a **context-free grammar (CFG)**.

A formal grammar takes a set of atomic pieces it calls its "alphabet". Then it defines a (usually infinite) set of "strings" that are "in" the grammar.

| Terminology             | Lexical grammar | Syntactic grammar |
| ----------------------- | --------------- | ----------------- |
| The "alphabet" is       | Characters      | Tokens            |
| A “string” is           | Lexeme or token | Expression        |
| It’s implemented by the | Scanner         | Parser            |

A formal grammar's job is to specify which strings are valid and which aren't. In English the sentence: "eggs are tasty for breakfast" would be in the grammar, but "tasty breakfast for are eggs" would not.

## Rules for grammars

You can use the rules to _generat_ strings that are in the grammar. Strings created this way are called **derivations** because each is _derived_ from the rules of the grammar. Rules are called **productions** because they _produce_ strings in the grammar.

Each production in a context-free grammar has a **head** and a **body**.

Heads are restricted to a single symbol in a context-free grammar.

- A **terminal** is a letter from the grammar's alphabet. In the syntactic grammar we're defining, the terminals are individual lexemes - tokens coming from the scanner like `if` or `123`.

- A **nonterminal** is a named reference to another rule in the grammar.

## Enhancing the notation

- `|`: Instead of repeating the rule name each time just add another production separated by a pipe.
- `(`: Use parentheses for grouping and allow only one inside to be selected from a series of option.
- `*`: Allow the previous to be repeated zero or more times.
- `+`: Similar but requires the preceding production to appear at least once.
- `?`: Optional production. the thing before it can appear zero or one time.

## A grammar for Lox expressions

Some expressions:

- Literal: Numbers, strings Booleans, and `nil`
- Unary expressions: A prefix `!` to perform a logical not, and `-` to negate a number.
- Binary expressions: The infix arithmetic (+,-,\*,/) and logic operators.
- Parentheses.

```hs
<expression> :: literal | unary | binary | grouping ;

literal  :: NUMBER | STRING | "true" | "false" | "nil" ;
grouping :: "(" <expression> ")" ;
unary    :: ( "-" | "!" ) <expression> ;
binary   :: <expression> <operator> <expression> ;
operator :: "==" | "!=" | "<" | "<=" | ">" | ">=" |
			  "+"  | "-"  | "*" | "/" ;
```

This grammar is still ambiguous! But it's good for now

## Implementing Syntax Trees

Since the grammar is recursive our data structure will form a tree. And since this structure represents the syntax of our language, it's called a **syntax tree**.

We define a a base class for expressions. Then, for each kind of expression - each production under `expression` - we create a subclass that has fields for the nonterminals specific to that rule.

This way, we got a compile error if we, say, try to access the second operand of a unary expression.

```java
abstract class Expr {
    static class Binary extends Expr {
        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        final Expr left;
        final Token operator;
        final Expr left;
    }

    // other expressions...
}
```

## Metaprogramming the trees

We create a simple script to generate the ASTs. Check `jLox/tool/GenerateAst.java`.

## Working with Trees

## The expression problem

## The Visitor pattern

The Visitor pattern is really about approximating the functional style within an OOP language. We can define all of the behavior for a new operation on a set of types in one place, without having to touch the types themselves. It does this by adding a layer of indirection.

Take this example:

```java
abstract class Pastry {
}

class Beignet extends Pastry {
}

class Cruller extends Pastry {
}
```

We want to define new pastry operations - cooking them, eating them, decorating them - without having to add a new method to each class every time.

```java
interface PastryVisitor {
	void visitBeignet(Beignet beignet);
	void visitCruller(Cruller cruller);
}
```

Each operation that can be performed on pastries is a new class that implements that interface.

Give some pastry, how do we route it to the correct method on the visitor based on its type?

```java
abstract class Pastry {
	abstract void accept(PastryVisitor visitor);
}
```

```java
class Beignet extends Pastry {
	@override
	void accept(PastryVisitor visitor) {
		visitor.visitBeignet(this);
	}
}
```

```java
class Cruller extends Pastry {
	@override
	void accept(PastryVisitor visitor) {
		visitor.visitCruller(this);
	}
}
```

## Visitors for expressions

## A (Not Very) Pretty Printer

# Challenges

1.

```hs
expr :: expr "(" ")" ;
expr :: expr "(" expr "," expr ")" ;
expr :: expr "(" expr ")" ;

expr :: expr "." IDENTIFIER ;
expr :: expr "(" expr "," expr ")"

expr :: IDENTIFIER
expr :: NUMBER
```

Produces expressions like:

- `4`
- `x`
- `6()`
- `add(1,2,3)`
- `tuple.first.length(1,2)`

2.

```ml
type operator = Plus | Minus

type literal = NUMBER of int | STRING of string

type expr =
| Binary of expr _ operator _ expr
| Literal of literal
| Unary of operator * expr

let rec print_expr = function
| Binary (left, token, right)
-> "(" ^ print_expr left ^ print_token token ^ print_expr right ^ ")"
| Literal value -> print_literal value
| Unary (token, right)
-> print_token token ^ print_expr right

and print_token = function
| Plus -> "+"
| Minus -> "-"

and print_literal = function
| NUMBER num -> string_of_int num
| STRING str -> str

let expr0 =
Binary
( Unary (Minus, Literal (NUMBER 4)),
Plus,
Literal (NUMBER 1)
)

let expr1 =
Unary (Minus, Literal (STRING "not"))

let () = print_endline (print_expr expr0)
let () = print_endline (print_expr expr1)
```

3. See the file at `jLox/lox/PrinterPolishNotation.java`.
