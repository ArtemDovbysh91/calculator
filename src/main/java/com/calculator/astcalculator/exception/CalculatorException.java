package com.calculator.astcalculator.exception;

/** Base exception for all calculator errors. */
public class CalculatorException extends RuntimeException {

  /**
   * Constructs with a message.
   *
   * @param message the detail message
   */
  public CalculatorException(final String message) {
    super(message);
  }

  /**
   * Constructs with a message and cause.
   *
   * @param message the detail message
   * @param cause the cause
   */
  public CalculatorException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
