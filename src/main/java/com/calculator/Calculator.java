package com.calculator;

import java.util.ArrayList;
import java.util.List;

public final class Calculator {

  private Calculator() {}

  /**
   * Evaluates a simple space-separated arithmetic expression and returns the integer result.
   *
   * <p>Supports the four basic operators ({@code +}, {@code -}, {@code *}, {@code /}) with standard
   * precedence (multiplication and division before addition and subtraction). Operands may be
   * negative integers. Parentheses are <strong>not</strong> supported; use {@link
   * com.calculator.astcalculator.AstCalculator} for full expression support.
   *
   * @param expression a non-null, non-blank, space-separated arithmetic expression (e.g. {@code "2
   *     + 3 * 4"})
   * @return the integer result of the expression (division truncates toward zero)
   * @throws IllegalArgumentException if the expression is null, blank, contains unknown operators,
   *     or has invalid number tokens
   * @throws ArithmeticException if division by zero is attempted
   */
  public static int calculate(final String expression) {
    if (expression == null || expression.isBlank()) {
      throw new IllegalArgumentException("Expression must not be null or empty");
    }

    List<String> tokens = tokenize(expression);
    return evaluate(tokens);
  }

  private static List<String> tokenize(final String expression) {
    String[] parts = expression.trim().split("\\s+");
    List<String> tokens = new ArrayList<>();

    for (String part : parts) {
      if (part.isEmpty()) {
        continue;
      }
      tokens.add(part);
    }

    if (tokens.isEmpty()) {
      throw new IllegalArgumentException("Expression contains no tokens");
    }

    return tokens;
  }

  private static int evaluate(final List<String> tokens) {
    // First pass: resolve * and / into a simplified list of additive terms
    List<Integer> values = new ArrayList<>();
    List<String> ops = new ArrayList<>();

    values.add(parseNumber(tokens.getFirst()));

    for (int i = 1; i < tokens.size(); i += 2) {
      if (i + 1 >= tokens.size()) {
        throw new IllegalArgumentException("Missing operand after operator: " + tokens.get(i));
      }

      String operator = tokens.get(i);
      int nextValue = parseNumber(tokens.get(i + 1));

      if (operator.equals("*") || operator.equals("/")) {
        int left = values.removeLast();
        if (operator.equals("*")) {
          values.add(left * nextValue);
        } else {
          if (nextValue == 0) {
            throw new ArithmeticException("Division by zero");
          }
          values.add(left / nextValue);
        }
      } else if (operator.equals("+") || operator.equals("-")) {
        ops.add(operator);
        values.add(nextValue);
      } else {
        throw new IllegalArgumentException("Unknown operator: " + operator);
      }
    }

    // Second pass: resolve + and -
    int result = values.getFirst();
    for (int i = 0; i < ops.size(); i++) {
      if (ops.get(i).equals("+")) {
        result += values.get(i + 1);
      } else {
        result -= values.get(i + 1);
      }
    }

    return result;
  }

  private static int parseNumber(final String token) {
    try {
      return Integer.parseInt(token);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number: " + token);
    }
  }
}
