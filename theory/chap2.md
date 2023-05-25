# Scanning

The first step also know as `lexing`. It takes the linear steam of characters and chunks them together into a series of something more akin to "words". In programming languages, each of these words is called a token. E.g. `var`, `average`, `=`, `(`, `min`, `+`, `max`, `)`, `/`, `2`, `;`.

# Parsing

This is where our syntax gets a grammar - the ability to compose larger expressions and statements out of smaller parts. Like when you did diagram sentences in English classes.

A parser takes the flat sequence of tokens and builds a tree structure that mirrors the nested nature of the grammar.

# Static analysis

So at this point we know the syntactic structure of the code, but we don't know much more than that.

For example, in an expression like `a + b`, we know we're adding a and b, but we don't know what those names refer to. Are they local variables? Global?

# Intermediate presentations

Think of the compiler as a pipeline where each stage's job is to organize the data representing the user's cod in a way that makes the next stage simpler to implement.

In the middle, the code may be stored in some `intermediate representation (IR)`

# Optimisation

A simple example is **constant folding**: if some expression always evaluates to the exact same value, we can do the evaluation at compile time and replace the code for the expression with its result.

# Code generation

Now is time to converting the user's code to a form the machine can actually run. Some sort of primitive assembly-like instructions a CPU runs and not the kind of "source code" a human might want to read.

# Virtual machine

# Runtime

We usually need some services that our language provides while the program is running. For example, if the language automatically manages memory, we need a garbage collector going in order to reclaim unused bits.

# Shortcuts and alternate routes

# Compilers and Interpreters

- Compiling is an implementation technique that involves translating a source language to some other -- usually lower-level-- form. When you transpile to another high-level language, you are compiling too.

- When you say a language implementation "is a **compiler**", that means it translates source code to some other form but doesn't execute it. The user has to take the resulting output and run it themselves.

- Conversely, when we say an implementation "is an interpreter", we mean it takes in source code and executes it immediately. It runs programs "from source".

