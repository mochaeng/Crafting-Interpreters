# Parsing Expressions

The only remaining piece missing is parsing: Transmogrifying a sequence of tokens into one of those syntax trees.

## Ambiguity and the Parsing Game

Parsers play the "generate strings game" from context-free grammars in reverse. So given a series of tokens we map those tokens to terminals in the grammar to figure out which rules could have generated that string.

If two choices of productions lead to the same string, we say that grammar is _ambiguous_. When parsing, ambiguity means the parser may misunderstand the user's code. 

When we are parsing, we aren't just determining if the string is valid code, but also tracking which rules match which parts of it, thus we know what part of the language each token belongs to.

So we need some sort of rules for precedence and associativity.

- **Precedence**: Determines which operator is evaluated first in an expression containing a mixture of different operators.
- **Associativity**: Determines which operator is evaluated first in a series of the _same_ operator.

Until now, all expressions types are into a single _expression_ rule. So let's define a separate rule for each precedence level.

```hs
expression :: equality 
equality :: NUMBER | STRING | "true" | "false" | "nil"
			 | "(" expression ")" ;
			 
comparison :: term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term :: factor ( ( "-" | "+" ) factor )* ;
factor :: unary ( ( "/" | "*" ) unary )*  ;
unary :: ( "!" | "-" ) unary | primary ;
primary :: NUMBER | STRING | "true" | "false" | "nil"
			| "(" expression ")" ;
```

# Recursive Descent Parsing 

It's is the simplest way technique to build a parser. A recursive descent parser is a literal translation of the grammar's rules straight into imperative code. Each rule becomes a function. The body of the rule translates to code.



