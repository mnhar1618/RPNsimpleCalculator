# RPN Calculator

A reverse Polish notation (RPN) calculator. You type a **normal infix expression** (e.g. `3 + 4 * 2`) and the program converts it to RPN internally, prints the converted expression, and shows the result.

## Project layout

```
.
├── Module/
│   └── ReversedPolishNotation.java   # infix → RPN converter + evaluator
└── src/
    ├── Main.java                     # entry point (interactive loop)
    └── Calculator.java               # math operations (binary + unary)
```

All classes are in the `RPNsimpleCalculator` package.

## Requirements

- Java 8 or newer (JDK with `javac`)

## How to compile

From the project root:

```bash
javac -d out src/Main.java src/Calculator.java
```

If you also want to compile the `Module` classes:

```bash
javac -d out src/Main.java src/Calculator.java Module/ReversedPolishNotation.java
```

## How to run

```bash
java -cp out RPNsimpleCalculator.Main
```

## Example session

```
	------RPN Calculator-------

Type a normal arithmetic expression, e.g.  3 + 4 * (2 - 1)
Functions: sin cos tan log ln exp sqr sqrt inv abs
Type 'quit' to exit.

> 3 + 4 * 2
RPN:    3 4 2 * +
Result: 11.0

> (1 + 2) * (3 - 1)
RPN:    1 2 + 3 1 - *
Result: 6.0

> sqrt(16) + 9
RPN:    16 sqrt 9 +
Result: 13.0

> quit
Bye!
```

## What you can type

| Input | Example |
|-------|---------|
| Numbers (integers and decimals) | `3.5` |
| Operators | `+` `-` `*` `/` `%` `^` |
| Parentheses | `(1 + 2) * 3` |
| Functions | `sin cos tan log ln exp sqr sqrt inv abs` |

Operator precedence is respected (`^` > `* / %` > `+ -`), and `^` is right-associative (`2 ^ 3 ^ 2 = 512`).

## Notes

- Tokens are whitespace-separated, so write spaces around operators and parentheses: `3 + 4 * 2`, not `3+4*2`.
- Division by zero and invalid function input (e.g. `sqrt(-1)`, `log(0)`) print an error message.
- `tan` works in degrees.
