package com.calculator.astcalculator;

import com.calculator.astcalculator.nodes.BinaryOpNode;
import com.calculator.astcalculator.nodes.NumberNode;
import com.calculator.astcalculator.nodes.UnaryMinusNode;

/**
 * Visitor interface for the AST node hierarchy.
 *
 * <p>Implementations traverse the tree by calling {@link
 * com.calculator.astcalculator.nodes.AstNode#accept(AstVisitor)} and provide type-specific logic
 * for each node kind.
 *
 * @param <T> the return type produced by each {@code visit} method
 * @see Evaluator
 * @see Validator
 */
public interface AstVisitor<T> {

  /**
   * Visits a numeric literal node.
   *
   * @param node the number node to visit
   * @return the result of visiting this node
   */
  T visit(NumberNode node);

  /**
   * Visits a binary operation node (e.g. addition, subtraction).
   *
   * @param node the binary operation node to visit
   * @return the result of visiting this node
   */
  T visit(BinaryOpNode node);

  /**
   * Visits a unary minus node.
   *
   * @param node the unary minus node to visit
   * @return the result of visiting this node
   */
  T visit(UnaryMinusNode node);
}
