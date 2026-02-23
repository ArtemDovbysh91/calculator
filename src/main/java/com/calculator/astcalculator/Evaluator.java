package com.calculator.astcalculator;

import com.calculator.astcalculator.exception.ValidationException;
import com.calculator.astcalculator.nodes.AstNode;
import com.calculator.astcalculator.nodes.BinaryOpNode;
import com.calculator.astcalculator.nodes.NumberNode;
import com.calculator.astcalculator.nodes.UnaryMinusNode;

public final class Evaluator implements AstVisitor<Integer> {

  private Evaluator() {}

  /**
   * Evaluates the given AST and returns the result.
   *
   * @param node the root of the AST
   * @return the integer result
   */
  public static int evaluate(final AstNode node) {
    return node.accept(new Evaluator());
  }

  @Override
  public Integer visit(final NumberNode node) {
    return node.getValue();
  }

  @Override
  public Integer visit(final BinaryOpNode node) {
    int left = node.getLeft().accept(this);
    int right = node.getRight().accept(this);

    return switch (node.getOperator()) {
      case ADD -> wrapOverflow(() -> Math.addExact(left, right));
      case SUBTRACT -> wrapOverflow(() -> Math.subtractExact(left, right));
      case MULTIPLY -> wrapOverflow(() -> Math.multiplyExact(left, right));
      case DIVIDE -> {
        if (right == 0) {
          throw new ValidationException("Division by zero");
        }
        if (left == Integer.MIN_VALUE && right == -1) {
          throw new ValidationException("Integer overflow in expression");
        }
        yield left / right;
      }
    };
  }

  @Override
  public Integer visit(final UnaryMinusNode node) {
    return wrapOverflow(() -> Math.negateExact(node.getOperand().accept(this)));
  }

  private static int wrapOverflow(final java.util.function.IntSupplier operation) {
    try {
      return operation.getAsInt();
    } catch (ArithmeticException e) {
      throw new ValidationException("Integer overflow in expression", e);
    }
  }
}
