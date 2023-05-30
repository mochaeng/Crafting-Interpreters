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

1.  - This means that the rules governing the formation of tokens in these languages cannot be fully described by regular expression or recognized by finite automata alone. The reason lies in the complexity of their tokenization rules. Both languages incorporate features that go beyond what regular languages can express.
      <br />

    > Python uses identation to denote block structure, making whitespace significant. This requirement cannot be expressed by a regular grammar, as regular languages cannot account for the context-dependent nature of identation.

    > Haskel allows nested comments, where comment delimiters can appear within other comment sections.

2.  2.1. **Ruby:**

    In ruby, methods can be called with or withou parentheses. Takes this code as example:

    ```rb
    Array.new(1,9)
    Array.new 1,9
    ```

    However, if you attempt to run the following code:

    ```rb
    Array.new (1,9)
    ```

    The parser mistakenly interprets it as receiving arguments after the space, whereas it actually receives a tuple (1, 9). In essence, the latter code is equivalent to:

    ```rb
    Array.new (1,9) <=> Array.new((1,9))
    ```

    However, if you attempt to run the following code:

    Look more at: [StackOverflow](https://stackoverflow.com/questions/26480823/why-does-white-space-affect-ruby-function-calls)

<br />

3.

<br />

4.  ```java
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

I just need to keep track of the number of open `/*` comments and ensure that all of them have been closed with a `*/`.
