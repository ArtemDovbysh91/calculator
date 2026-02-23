package com.calculator.astcalculator;

import com.calculator.astcalculator.exception.InvalidExpressionException;
import com.calculator.astcalculator.exception.ValidationException;
import com.calculator.astcalculator.nodes.AstNode;
import java.util.List;

public final class AstCalculator {

  private AstCalculator() {}

  /**
   * Evaluates an arithmetic expression and returns the integer result.
   *
   * <p>The expression is processed through four stages:
   *
   * <ol>
   *   <li><strong>Tokenize</strong> — split the input into {@link Token}s.
   *   <li><strong>Parse</strong> — build an AST from the token stream.
   *   <li><strong>Validate</strong> — static analysis (e.g. literal division by zero).
   *   <li><strong>Evaluate</strong> — walk the AST and compute the result.
   * </ol>
   *
   * @param expression a non-null, non-blank arithmetic expression (e.g. {@code "2 + 3 * (4 - 1)"})
   * @return the integer result of the expression (division truncates toward zero)
   * @throws com.calculator.astcalculator.exception.InvalidExpressionException if the expression is
   *     null, blank, or syntactically invalid
   * @throws ValidationException if evaluation fails (e.g. division by zero, integer overflow)
   */
  public static int calculate(final String expression) {
    if (expression == null || expression.isBlank()) {
      throw new InvalidExpressionException("Expression must not be null or empty");
    }

    // Step 1: Tokenize
    List<Token> tokens = Tokenizer.tokenize(expression);

    // Step 2: Parse into AST
    AstNode ast = Parser.parse(tokens);

    // Step 3: Validate
    Validator.validate(ast);

    // Step 4: Evaluate
    return Evaluator.evaluate(ast);
  }
}
