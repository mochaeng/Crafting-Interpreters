# Scanning

The first step in any compiler or interpreter is scanning. The scanner takes in raw source code as a series of characters and groups it into a series of chunks we call tokens. These are the meaningful "words" and "punctuation" that make up the language's grammar.

# Lexemes and Tokens

Our job is to scan through the list of characters and group them together into the smallest sequences that still represent something. Each of these blobs of characters is called a `lexeme`.

In the process of grouping character sequences into lexemes, we also stumble upon some other useful information. When we take the lexeme and bundle it together with that other data, the result is a token. It includes useful stuff like:

## Token type

The parser often has code like, "If the next token is `while` then do ..." That means the parser wants to know not just that it has a lexeme for some identifier, but that it has a _reserved_ word, and which keyword it is.

