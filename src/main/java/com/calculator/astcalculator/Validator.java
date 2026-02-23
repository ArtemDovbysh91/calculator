package com.calculator.astcalculator;

import com.calculator.astcalculator.exception.ValidationException;
import com.calculator.astcalculator.nodes.AstNode;
import com.calculator.astcalculator.nodes.BinaryOpNode;
import com.calculator.astcalculator.nodes.NumberNode;
import com.calculator.astcalculator.nodes.UnaryMinusNode;

/**
 * Static analysis pass over the AST.
 *
 * <p>Currently catches division by zero when the divisor is a literal {@link NumberNode}. Dynamic
 * cases (e.g., {@code 10 / (5 - 5)}) are caught at evaluation time by {@link Evaluator}.
 */
public final class Validator implements AstVisitor<Void> {

  private Validator() {}

  /**
   * Validates the given AST.
   *
   * @param node the root of the AST
   */
  public static void validate(final AstNode node) {
    node.accept(new Validator());
  }

  @Override
  public Void visit(final NumberNode node) {
    // Numbers are always valid
    return null;
  }

  @Override
  public Void visit(final BinaryOpNode node) {
    node.getLeft().accept(this);
    node.getRight().accept(this);

    if (node.getOperator() == BinaryOpNode.Operator.DIVIDE) {
      if (node.getRight() instanceof NumberNode right) {
        if (right.getValue() == 0) {
          throw new ValidationException("Division by zero detected" + " in expression");
        }
      }
    }

    return null;
  }

  @Override
  public Void visit(final UnaryMinusNode node) {
    node.getOperand().accept(this);
    return null;
  }
}
