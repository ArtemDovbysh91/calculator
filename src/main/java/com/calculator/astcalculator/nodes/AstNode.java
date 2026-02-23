package com.calculator.astcalculator.nodes;

import com.calculator.astcalculator.AstVisitor;

/** Base class for all AST nodes. Sealed to ensure exhaustive handling of node types. */
public abstract sealed class AstNode permits NumberNode, BinaryOpNode, UnaryMinusNode {

  /**
   * Accepts a visitor.
   *
   * @param visitor the visitor
   * @param <T> the return type
   * @return the visitor result
   */
  public abstract <T> T accept(AstVisitor<T> visitor);
}
