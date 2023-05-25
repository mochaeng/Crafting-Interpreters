# Representing Code

## Context-Free Grammars

In the last chapter, the formalism for defining the lexical grammar was called a _regular language_. That was fine for our scanner, but regular languages aren't powerful enough to handle expressions which can nest arbitrarily deeply.

We need a **context-free grammar (CFG)**. 

A formal grammar takes a set of atomic pieces it calls its "alphabet". Then it defines a (usually infinite) set of "strings" that are "in" the grammar.

| Terminology             | Lexical grammar | Syntactic grammar |   |   |
|-------------------------|-----------------|-------------------|---|---|
| The "alphabet" is       | Characters      | Tokens            |   |   |
| A “string” is           | Lexeme or token | Expression        |   |   |
| It’s implemented by the | Scanner         | Parser            |   |   |

A formal grammar's job is to specify which strings are valid and which aren't. In English the sentence: "eggs are tasty for breakfast" would be in the grammar, but "tasty breakfast for are eggs" would not.

## Rules for grammars

You can use the rules to _generat_ strings that are in the grammar. Strings created this way are called **derivations** because each is _derived_ from the rules of the grammar. Rules are called **productions** because they _produce_ strings in the grammar.

Each production in a context-free grammar has a **head** and a **body**.

Heads are restricted to a single symbol in a context-free grammar.

- A **terminal** is a letter from the grammar's alphabet. In the syntactic grammar we're defining, the terminals are individual lexemes - tokens coming from the scanner like `if` or `123`.

- A **nonterminal** is a named reference to another rule in the grammar.

## A grammar for Lox expressions

Some expressions:

- Literal: Numbers, strings Booleans, and `nil`
- Unary expressions: A prefix `!` to perform a logical not, and `-` to negate a number.
- Binary expressions: The infix arithmetic (+,-,*,/) and logic operators.
- Parentheses.

## Implementing Syntax Trees


Since the grammar is recursive our data structure will form a tree. And since this structure represents the syntax of our language, it's called a **syntax tree**.

