package com.calculator;

import com.calculator.astcalculator.AstCalculator;

public final class Main {

  private Main() {}

  /**
   * Application entry point.
   *
   * @param args command-line arguments
   */
  public static void main(final String[] args) {
    System.out.println("Expression Calculator");
    System.out.println("=====================");

    System.out.println();
    System.out.println("--- V1: Simple procedural calculator ---");
    String[] v1Examples = {"2 + 3", "3 * 2 + 1", "3 * -2 + 6"};
    for (String expr : v1Examples) {
      int result = Calculator.calculate(expr);
      System.out.printf("  calculate(\"%s\") = %d%n", expr, result);
    }

    System.out.println();
    System.out.println("--- V2: AST-based calculator ---");
    String[] v2Examples = {
      "2 + 3", "3 * 2 + 1", "3 * -2 + 6", "(1 + 2) * 3", "((2 + 3) * (1 + 3))", "-(1 + 2) * 4"
    };
    for (String expr : v2Examples) {
      int result = AstCalculator.calculate(expr);
      System.out.printf("  calculate(\"%s\") = %d%n", expr, result);
    }
  }
}
