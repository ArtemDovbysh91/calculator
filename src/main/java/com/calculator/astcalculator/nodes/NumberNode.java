package com.calculator.astcalculator.nodes;

import com.calculator.astcalculator.AstVisitor;

public final class NumberNode extends AstNode {

  /** The numeric value. */
  private final int value;

  /**
   * Creates a number node.
   *
   * @param aValue the numeric value
   */
  public NumberNode(final int aValue) {
    this.value = aValue;
  }

  /**
   * @return the numeric value.
   */
  public int getValue() {
    return value;
  }

  @Override
  public <T> T accept(final AstVisitor<T> visitor) {
    return visitor.visit(this);
  }

  //    @Override
  //    public boolean equals(Object o) {
  //        if (this == o) return true;
  //        if (!(o instanceof NumberNode that)) return false;
  //        return value == that.value;
  //    }
  //
  //    @Override
  //    public int hashCode() {
  //        return Objects.hash(value);
  //    }
  //
  //    @Override
  //    public String toString() {
  //        return String.valueOf(value);
  //    }
}
