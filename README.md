# Expression Calculator

A Java 21 expression calculator demonstrating two implementation approaches: a simple procedural calculator and an AST-based calculator using the Visitor design pattern.

## Prerequisites

- **Java 21** or later
- **Maven 3.8+** (or use the included Maven Wrapper)

## Building & Running

```bash
# Build
./mvnw clean compile

# Run tests
./mvnw test

# Run the demo
./mvnw exec:java

# Run Spotless (Google Java Format)
./mvnw spotless:check
```

## Project Structure

```
src/main/java/com/calculator/
├── Main.java                        # Demo entry point
├── Calculator.java                  # V1: Simple procedural calculator
└── astcalculator/                   # V2: AST-based calculator
    ├── AstCalculator.java           # Facade: Tokenize → Parse → Validate → Evaluate
    ├── Tokenizer.java               # Lexical analysis (string → tokens)
    ├── Token.java                   # Token record (type, value, position)
    ├── Parser.java                  # Recursive-descent parser (tokens → AST)
    ├── Validator.java               # Static analysis on the AST
    ├── Evaluator.java               # Tree-walking evaluator (AST → int)
    ├── AstVisitor.java              # Generic Visitor interface
    ├── exception/                   # Domain-specific exceptions
    │   ├── CalculatorException.java
    │   ├── InvalidExpressionException.java
    │   └── ValidationException.java
    └── nodes/                       # AST node hierarchy (sealed)
        ├── AstNode.java
        ├── NumberNode.java
        ├── BinaryOpNode.java
        └── UnaryMinusNode.java
```

## Design Decisions

### Two Implementations

1. **`Calculator` (V1)** — A simple, procedural two-pass evaluator that splits on whitespace and handles operator precedence via separate passes for `* /` then `+ -`. Demonstrates a straightforward approach but requires spaces between tokens and lacks parentheses support.

2. **`AstCalculator` (V2)** — A full pipeline demonstrating compiler-design principles:
   - **Tokenizer** — Character-by-character lexical analysis; handles expressions with or without spaces.
   - **Parser** — Recursive-descent parser implementing the grammar:
     ```
     expression = term (('+' | '-') term)*
     term       = unary (('*' | '/') unary)*
     unary      = '-' unary | primary
     primary    = NUMBER | '(' expression ')'
     ```
   - **Validator** — Static analysis pass using the Visitor pattern (catches literal division by zero before evaluation).
   - **Evaluator** — Tree-walking interpreter using the Visitor pattern with overflow-safe arithmetic (`Math.addExact`, etc.).

### Key Patterns & Practices

- **Visitor pattern** — `AstVisitor<T>` enables adding new operations (evaluation, validation, pretty-printing) without modifying node classes.
- **Records** — `Token` is a Java record, eliminating boilerplate for immutable data carriers.
- **Custom exceptions** — `CalculatorException` hierarchy provides domain-specific error handling.
- **Overflow protection** — `Math.addExact`, `Math.subtractExact`, `Math.multiplyExact`, `Math.negateExact` detect integer overflow at runtime.
- **Immutable AST** — All node fields are `final`; the tree is built once and never mutated.

## Supported Operations

| Operation      | Example               | Result |
|----------------|-----------------------|--------|
| Addition       | `2 + 3`               | `5`    |
| Subtraction    | `3 - 2`               | `1`    |
| Multiplication | `2 * 3`               | `6`    |
| Division       | `9 / 3`               | `3`    |
| Unary minus    | `-5`                  | `-5`   |
| Parentheses    | `(1 + 2) * 3`         | `9`    |
| Mixed          | `10 + 2 * 3 - 4 / 2`  | `14`   |

## Testing

Tests use **JUnit 5** and cover both implementations:

- `CalculatorTest` — V1 tests (basic arithmetic, precedence, negative numbers, error handling)
- `AstCalculatorTest` — V2 tests (all of the above plus parentheses, nested expressions, unary minus, overflow/underflow, whitespace variations, invalid input)

```bash
./mvnw test
```

## CI & Code Quality

- **GitHub Actions CI** — Builds and tests on every push/PR to `main` (`mvn verify`) with Spotless format check
- **CodeQL** — Static security analysis runs on push/PR and weekly schedule
- **Spotless** — Google Java Format enforced via `spotless-maven-plugin` (checked in CI)
- **Dependabot** — Automated weekly dependency updates for Maven and GitHub Actions
