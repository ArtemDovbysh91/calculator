package com.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculatorTest {

  @Test
  void testAddition() {
    assertEquals(5, Calculator.calculate("2 + 3"));
  }

  @Test
  void testSubtraction() {
    assertEquals(1, Calculator.calculate("3 - 2"));
  }

  @Test
  void testMultiplication() {
    assertEquals(6, Calculator.calculate("2 * 3"));
  }

  @Test
  void testDivision() {
    assertEquals(3, Calculator.calculate("9 / 3"));
  }

  @Test
  void testOperatorPrecedence() {
    assertEquals(7, Calculator.calculate("3 * 2 + 1"));
    assertEquals(5, Calculator.calculate("1 + 2 * 2"));
    assertEquals(10, Calculator.calculate("2 + 2 * 3 + 2"));
  }

  @Test
  void testNegativeNumbers() {
    assertEquals(0, Calculator.calculate("3 * -2 + 6"));
    assertEquals(-5, Calculator.calculate("-3 + -2"));
    assertEquals(6, Calculator.calculate("-2 * -3"));
  }

  @Test
  void testSingleNumber() {
    assertEquals(42, Calculator.calculate("42"));
    assertEquals(-7, Calculator.calculate("-7"));
  }

  @Test
  void testDivisionTruncation() {
    assertEquals(2, Calculator.calculate("7 / 3"));
    assertEquals(-2, Calculator.calculate("-7 / 3"));
  }

  @Test
  void testComplexExpression() {
    assertEquals(14, Calculator.calculate("10 + 2 * 3 - 4 / 2"));
  }

  @Test
  void testNegativeSubtraction() {
    assertEquals(3, Calculator.calculate("1 - -2"));
  }

  @Test
  void testDivisionByZero() {
    assertThrows(ArithmeticException.class, () -> Calculator.calculate("5 / 0"));
  }

  @Test
  void testNullExpression() {
    assertThrows(IllegalArgumentException.class, () -> Calculator.calculate(null));
  }

  @Test
  void testEmptyExpression() {
    assertThrows(IllegalArgumentException.class, () -> Calculator.calculate(""));
    assertThrows(IllegalArgumentException.class, () -> Calculator.calculate("   "));
  }

  @Test
  void testInvalidOperator() {
    assertThrows(IllegalArgumentException.class, () -> Calculator.calculate("2 ^ 3"));
  }

  @Test
  void testInvalidNumber() {
    assertThrows(IllegalArgumentException.class, () -> Calculator.calculate("2 + abc"));
  }

  @Test
  void testMissingOperator() {
    assertThrows(IllegalArgumentException.class, () -> Calculator.calculate("2 3"));
  }

  @Test
  void testTrailingOperator() {
    assertThrows(IllegalArgumentException.class, () -> Calculator.calculate("2 +"));
  }

  @Test
  void testLargeNumberOverflowOnParse() {
    assertThrows(IllegalArgumentException.class, () -> Calculator.calculate("99999999999 + 1"));
  }

  @Test
  void testZeroOperations() {
    assertEquals(0, Calculator.calculate("0 * 999"));
    assertEquals(0, Calculator.calculate("0 + 0"));
  }

  @Test
  void testDivisionByOne() {
    assertEquals(42, Calculator.calculate("42 / 1"));
  }

  @Test
  void testMultipleOperations() {
    assertEquals(14, Calculator.calculate("10 + 2 * 3 - 4 / 2"));
  }
}
