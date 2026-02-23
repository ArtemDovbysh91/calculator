package com.calculator.astcalculator.exception;

/** Thrown when the input expression cannot be tokenized or parsed. */
public class InvalidExpressionException extends CalculatorException {

  /**
   * Constructs with a message.
   *
   * @param message the detail message
   */
  public InvalidExpressionException(final String message) {
    super(message);
  }
}
