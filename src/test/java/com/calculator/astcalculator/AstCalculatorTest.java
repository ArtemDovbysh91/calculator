package com.calculator.astcalculator;

import static org.junit.jupiter.api.Assertions.*;

import com.calculator.astcalculator.exception.CalculatorException;
import com.calculator.astcalculator.exception.InvalidExpressionException;
import com.calculator.astcalculator.exception.ValidationException;
import org.junit.jupiter.api.Test;

class AstCalculatorTest {

  // --- Basic arithmetic ---

  @Test
  void testAddition() {
    assertEquals(5, AstCalculator.calculate("2 + 3"));
  }

  @Test
  void testSubtraction() {
    assertEquals(1, AstCalculator.calculate("3 - 2"));
  }

  @Test
  void testMultiplication() {
    assertEquals(6, AstCalculator.calculate("2 * 3"));
  }

  @Test
  void testDivision() {
    assertEquals(3, AstCalculator.calculate("9 / 3"));
  }

  // --- Operator precedence ---

  @Test
  void testOperatorPrecedence() {
    assertEquals(7, AstCalculator.calculate("3 * 2 + 1"));
    assertEquals(5, AstCalculator.calculate("1 + 2 * 2"));
    assertEquals(10, AstCalculator.calculate("2 + 2 * 3 + 2"));
  }

  // --- Unary minus ---

  @Test
  void testUnaryMinus() {
    assertEquals(-5, AstCalculator.calculate("-5"));
    assertEquals(-1, AstCalculator.calculate("-3 + 2"));
    assertEquals(6, AstCalculator.calculate("-2 * -3"));
    assertEquals(-5, AstCalculator.calculate("-3 + -2"));
  }

  // --- Single number ---

  @Test
  void testSingleNumber() {
    assertEquals(42, AstCalculator.calculate("42"));
    assertEquals(0, AstCalculator.calculate("0"));
  }

  // --- Division truncation ---

  @Test
  void testDivisionTruncation() {
    assertEquals(2, AstCalculator.calculate("7 / 3"));
  }

  // --- Parentheses ---

  @Test
  void testSimpleParentheses() {
    assertEquals(9, AstCalculator.calculate("(4 + 5)"));
    assertEquals(9, AstCalculator.calculate("3 * (1 + 2)"));
  }

  @Test
  void testParenthesesOverridePrecedence() {
    assertEquals(9, AstCalculator.calculate("(1 + 2) * 3"));
    assertEquals(20, AstCalculator.calculate("(2 + 3) * (1 + 3)"));
  }

  @Test
  void testNestedParentheses() {
    assertEquals(12, AstCalculator.calculate("((2 + 1) * (3 + 1))"));
    assertEquals(21, AstCalculator.calculate("(1 + 2) * (3 + (2 * 2))"));
  }

  @Test
  void testParenthesesWithUnaryMinus() {
    assertEquals(-3, AstCalculator.calculate("-(1 + 2)"));
    assertEquals(6, AstCalculator.calculate("-(1 - 3) * 3"));
  }

  // --- Complex expressions ---

  @Test
  void testComplexExpression() {
    assertEquals(14, AstCalculator.calculate("10 + 2 * 3 - 4 / 2"));
  }

  @Test
  void testComplexWithParentheses() {
    assertEquals(21, AstCalculator.calculate("(10 + 2) * 3 - (4 + 2) * (7 - 4) + 3"));
  }

  // --- No spaces ---

  @Test
  void testNoSpaces() {
    assertEquals(5, AstCalculator.calculate("2+3"));
    assertEquals(9, AstCalculator.calculate("(1+2)*3"));
  }

  // --- Error handling ---

  @Test
  void testDivisionByZero() {
    assertThrows(ValidationException.class, () -> AstCalculator.calculate("5 / 0"));
  }

  @Test
  void testNullExpression() {
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate(null));
  }

  @Test
  void testEmptyExpression() {
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate(""));
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("   "));
  }

  @Test
  void testUnexpectedCharacter() {
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("2 ^ 3"));
  }

  @Test
  void testMismatchedParentheses() {
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("(2 + 3"));
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("2 + 3)"));
  }

  @Test
  void testFirstTokenMustBeNumber() {
    InvalidExpressionException ex =
        assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("+ 2 3"));
    assertTrue(ex.getMessage().contains("Expected number or '('"));

    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("* 5"));
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("/ 4 2"));
  }

  @Test
  void testMissingOperand() {
    assertThrows(CalculatorException.class, () -> AstCalculator.calculate("2 +"));
    assertThrows(CalculatorException.class, () -> AstCalculator.calculate("2 *"));
  }

  // --- Validator catches static division by zero ---

  @Test
  void testValidatorCatchesDivisionByZeroLiteral() {
    assertThrows(ValidationException.class, () -> AstCalculator.calculate("10 / 0"));
  }

  // --- Letters instead of numbers ---

  @Test
  void testLetterInsteadOfNumber() {
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("a + 2"));
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("2 + b"));
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("x"));
  }

  // --- Roman numerals (invalid) ---

  @Test
  void testRomanNumeralIsInvalid() {
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("IV + 2"));
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("X * III"));
  }

  // --- Integer overflow on addition/multiplication ---

  @Test
  void testIntegerOverflowAddition() {
    // 2147483647 + 1 overflows int
    assertThrows(ValidationException.class, () -> AstCalculator.calculate("2147483647 + 1"));
  }

  @Test
  void testIntegerOverflowMultiplication() {
    // 2147483647 * 2 overflows int
    assertThrows(ValidationException.class, () -> AstCalculator.calculate("2147483647 * 2"));
  }

  // --- Integer underflow on subtraction ---

  @Test
  void testIntegerUnderflowSubtraction() {
    // -2147483647 - 2 underflows int (result would be -2147483649)
    assertThrows(ValidationException.class, () -> AstCalculator.calculate("-2147483647 - 2"));
  }

  // --- Deeply nested parentheses ---

  @Test
  void testDeeplyNestedParentheses() {
    assertEquals(3, AstCalculator.calculate("((((1 + 2))))"));
  }

  // --- Double negation ---

  @Test
  void testDoubleNegation() {
    assertEquals(5, AstCalculator.calculate("--5"));
    assertEquals(-5, AstCalculator.calculate("---5"));
  }

  // --- Large chained expression ---

  @Test
  void testLargeChainedExpression() {
    assertEquals(10, AstCalculator.calculate("1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1"));
  }

  // --- Whitespace variations ---

  @Test
  void testExtraWhitespace() {
    assertEquals(5, AstCalculator.calculate("  2  +  3  "));
  }

  @Test
  void testTabsInExpression() {
    assertEquals(5, AstCalculator.calculate("2\t+\t3"));
  }

  // --- Zero operations ---

  @Test
  void testZeroOperations() {
    assertEquals(0, AstCalculator.calculate("0 * 999"));
    assertEquals(0, AstCalculator.calculate("0 + 0"));
    assertEquals(0, AstCalculator.calculate("0 - 0"));
  }

  // --- Max int literal ---

  @Test
  void testMaxIntLiteral() {
    assertEquals(Integer.MAX_VALUE, AstCalculator.calculate("2147483647"));
  }

  // --- Number literal overflow ---

  @Test
  void testNumberLiteralOverflow() {
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("9999999999"));
    assertThrows(InvalidExpressionException.class, () -> AstCalculator.calculate("9999999999 + 1"));
  }

  // --- Unary minus with parenthesized multiplication ---

  @Test
  void testUnaryMinusParenthesizedMultiplication() {
    assertEquals(10, AstCalculator.calculate("-(2 + 3) * -(1 + 1)"));
  }

  // --- Division edge cases ---

  @Test
  void testDivisionByOne() {
    assertEquals(42, AstCalculator.calculate("42 / 1"));
  }

  @Test
  void testDynamicDivisionByZero() {
    assertThrows(ValidationException.class, () -> AstCalculator.calculate("10 / (5 - 5)"));
  }

  @Test
  void testMinValueDivideByNegativeOne() {
    // Integer.MIN_VALUE / -1 overflows int (result would be 2147483648)
    assertThrows(
        ValidationException.class, () -> AstCalculator.calculate("(-2147483647 - 1) / -1"));
  }

  // --- Negation of Integer.MIN_VALUE boundary ---

  @Test
  void testNegateOverflow() {
    // -(-2147483647) is fine, but -(2147483647 + 1) would overflow during addition
    assertThrows(ValidationException.class, () -> AstCalculator.calculate("-(2147483647 + 1)"));
  }
}
