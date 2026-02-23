package com.calculator.astcalculator.exception;

/** Thrown when the expression cannot be evaluated (e.g., division by zero, integer overflow). */
public class ValidationException extends CalculatorException {

  /**
   * Constructs with a message.
   *
   * @param message the detail message
   */
  public ValidationException(final String message) {
    super(message);
  }

  /**
   * Constructs with a message and cause.
   *
   * @param message the detail message
   * @param cause the cause
   */
  public ValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
