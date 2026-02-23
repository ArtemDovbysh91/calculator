package com.calculator.astcalculator.nodes;

import com.calculator.astcalculator.AstVisitor;
import java.util.Objects;

public final class BinaryOpNode extends AstNode {

  public enum Operator {
    /** Addition. */
    ADD,
    /** Subtraction. */
    SUBTRACT,
    /** Multiplication. */
    MULTIPLY,
    /** Division. */
    DIVIDE
  }

  /** Left operand. */
  private final AstNode left;

  /** The operator. */
  private final Operator operator;

  /** Right operand. */
  private final AstNode right;

  /**
   * Creates a binary operation node.
   *
   * @param aLeft the left operand
   * @param anOperator the operator
   * @param aRight the right operand
   */
  public BinaryOpNode(final AstNode aLeft, final Operator anOperator, final AstNode aRight) {
    this.left = Objects.requireNonNull(aLeft, "Left operand must not be null");
    this.operator = Objects.requireNonNull(anOperator, "Operator must not be null");
    this.right = Objects.requireNonNull(aRight, "Right operand must not be null");
  }

  /**
   * @return the left operand.
   */
  public AstNode getLeft() {
    return left;
  }

  /**
   * @return the operator.
   */
  public Operator getOperator() {
    return operator;
  }

  /**
   * @return the right operand.
   */
  public AstNode getRight() {
    return right;
  }

  @Override
  public <T> T accept(final AstVisitor<T> visitor) {
    return visitor.visit(this);
  }

  //    @Override
  //    public boolean equals(Object o) {
  //        if (this == o) return true;
  //        if (!(o instanceof BinaryOpNode that)) return false;
  //        return Objects.equals(left, that.left)
  //                && operator == that.operator
  //                && Objects.equals(right, that.right);
  //    }
  //
  //    @Override
  //    public int hashCode() {
  //        return Objects.hash(left, operator, right);
  //    }
  //
  //    @Override
  //    public String toString() {
  //        return "(" + left + " " + operator + " " + right + ")";
  //    }
}
