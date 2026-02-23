package com.calculator.astcalculator.nodes;

import com.calculator.astcalculator.AstVisitor;
import java.util.Objects;

public final class UnaryMinusNode extends AstNode {

  /** The operand to negate. */
  private final AstNode operand;

  /**
   * Creates a unary minus node.
   *
   * @param anOperand the operand to negate
   */
  public UnaryMinusNode(final AstNode anOperand) {
    this.operand = Objects.requireNonNull(anOperand, "Operand must not be null");
  }

  /**
   * @return the operand.
   */
  public AstNode getOperand() {
    return operand;
  }

  @Override
  public <T> T accept(final AstVisitor<T> visitor) {
    return visitor.visit(this);
  }

  //    @Override
  //    public boolean equals(Object o) {
  //        if (this == o) return true;
  //        if (!(o instanceof UnaryMinusNode that)) return false;
  //        return Objects.equals(operand, that.operand);
  //    }
  //
  //    @Override
  //    public int hashCode() {
  //        return Objects.hash(operand);
  //    }
  //
  //    @Override
  //    public String toString() {
  //        return "(-" + operand + ")";
  //    }
}
