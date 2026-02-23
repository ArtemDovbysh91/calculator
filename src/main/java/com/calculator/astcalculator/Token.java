package com.calculator.astcalculator;

public record Token(Type type, String value, int position) {

  public enum Type {
    /** Numeric literal. */
    NUMBER,
    /** Addition operator. */
    PLUS,
    /** Subtraction operator. */
    MINUS,
    /** Multiplication operator. */
    MULTIPLY,
    /** Division operator. */
    DIVIDE,
    /** Left parenthesis. */
    LPAREN,
    /** Right parenthesis. */
    RPAREN,
    /** End of input. */
    EOF
  }
}
