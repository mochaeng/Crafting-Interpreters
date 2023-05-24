# Scanning

The first step in any compiler or interpreter is scanning. The scanner takes in raw source code as a series of characters and groups it into a series of chunks we call tokens. These are the meaningful "words" and "punctuation" that make up the language's grammar.

# Lexemes and Tokens

Our job is to scan through the list of characters and group them together into the smallest sequences that still represent something. Each of these blobs of characters is called a `lexeme`.

In the process of grouping character sequences into lexemes, we also stumble upon some other useful information. When we take the lexeme and bundle it together with that other data, the result is a token. It includes useful stuff like:

## Token type

The parser often has code like, "If the next token is `while` then do ..." That means the parser wants to know not just that it has a lexeme for some identifier, but that it has a _reserved_ word, and which keyword it is.

## Reserved Words and Identifiers

Consider the following case:

```java
case 'o':
	if (match('r')) {
		addToken(OR);
	}
break;
```

Now imagine if the user named a variable `orchid`. The scanner would see the first two letters, `or`, and immediately emit an `or` keyword token.

This gets us to an important principle called **maximal munch**. When two lexical grammar rules can both match a chunk of code that the scanner is looking at, _whichever one matches the most characters wins_.

Maximal munch means we can't easily detect a reserved word until er've reached the end of what might instead be an identifier. After all, a reserved word is an identifier. That's where term **reserved word** comes from.

So we begin by assuming any lexeme starting with a letter or underscore is an identifier.

```java
default:
	if (isDigit(c)) {
		number();
	} else if (isAlpha(c)) {
		identifier();
	} else {
		Lox.error(line, "Unexpected character.);
	}
```

# Challenges

1. The lexical grammars of Python and Haskell are not regular. What does that mean, and why aren’t they?

- - This means that the rules governing the formation of tokens in these languages cannot be fully described by regular expression or recognized by finite automata alone. The reason lies in the complexity of their tokenization rules. Both languages incorporate features that go beyond what regular languages can express.
    <br />

    > Python uses identation to denote block structure, making whitespace significant. This requirement cannot be expressed by a regular grammar, as regular languages cannot account for the context-dependent nature of identation.

    > Haskel allows nested comments, where comment delimiters can appear within other comment sections.

2. Aside from separating tokens—distinguishing print foo from printfoo—spaces aren’t used for much in most languages. However, in a couple of dark corners, a space does affect how code is parsed in CoffeeScript, Ruby, and the C preprocessor. Where and what effect does it have in each of those languages?
   <br />

   2.1. **Ruby:**

   In ruby, methods can be called with or withou parentheses. Takes this code as example:

   ```rb
   Array.new(1,9)
   Array.new 1,9
   ```

   But if you try to run

   ```rb
   Array.new (1,9)
   ```

   The parser thinks it will receive arugments after the space, but actually it receives a tuple `(1,2)`. So essentially the latter is actually the same as:

   ```rb
   Array.new (1,9) <=> Array.new((1,9))
   ```

   So we got a syntax error.

   Look more at: [StackOverflow](https://stackoverflow.com/questions/26480823/why-does-white-space-affect-ruby-function-calls)

3. Our scanner here, like most, discards comments and whitespace since those aren’t needed by the parser. Why might you want to write a scanner that does not discard those? What would it be useful for?

4. Add support to Lox’s scanner for C-style /_ ... _/ block comments. Make sure to handle newlines in them. Consider allowing them to nest. Is adding support for nesting more work than you expected? Why?

```java
case '/':
    ...
    } else if (match('*')) {
        comment();
    }
    ...
```

```java
private void comment() {
    int closed = 1;
    while (closed > 0) {
        char c = peek();
		if (c == '*' && peekNext() == '/') {
			advance();

			closed--;
		} else if (c == '/' && peekNext() == '*') {
			advance();
			closed++;
		} else if (isAtEnd()) {
			Lox.error(line, "Unclosed comment");
			break;
		} else if (c == '\n') {
			line++;
		}
		advance();
	}
}
```
