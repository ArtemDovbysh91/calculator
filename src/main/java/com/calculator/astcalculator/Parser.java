package com.calculator.astcalculator;

import com.calculator.astcalculator.exception.InvalidExpressionException;
import com.calculator.astcalculator.nodes.AstNode;
import com.calculator.astcalculator.nodes.BinaryOpNode;
import com.calculator.astcalculator.nodes.NumberNode;
import com.calculator.astcalculator.nodes.UnaryMinusNode;
import java.util.List;

/**
 * Recursive-descent parser that converts a list of {@link Token}s into an AST.
 *
 * <p>Grammar:
 *
 * <pre>
 *   expression = term (('+' | '-') term)*
 *   term       = unary (('*' | '/') unary)*
 *   unary      = '-' unary | primary
 *   primary    = NUMBER | '(' expression ')'
 * </pre>
 */
public final class Parser {

  /** The token stream to parse. */
  private final List<Token> tokens;

  /** Current position in the token stream. */
  private int pos;

  private Parser(final List<Token> theTokens) {
    this.tokens = theTokens;
    this.pos = 0;
  }

  /**
   * Parses a token list into an AST.
   *
   * @param tokens the token list to parse
   * @return the root AST node
   */
  public static AstNode parse(final List<Token> tokens) {
    return new Parser(tokens).doParse();
  }

  private AstNode doParse() {
    AstNode node = parseExpression();

    if (current().type() != Token.Type.EOF) {
      throw new InvalidExpressionException(
          "Unexpected token '" + current().value() + "' at position " + current().position());
    }

    return node;
  }

  // expression = term (('+' | '-') term)*
  private AstNode parseExpression() {
    AstNode left = parseTerm();

    while (current().type() == Token.Type.PLUS || current().type() == Token.Type.MINUS) {
      Token op = consume();
      AstNode right = parseTerm();
      BinaryOpNode.Operator operator =
          op.type() == Token.Type.PLUS ? BinaryOpNode.Operator.ADD : BinaryOpNode.Operator.SUBTRACT;
      left = new BinaryOpNode(left, operator, right);
    }

    return left;
  }

  // term = unary (('*' | '/') unary)*
  private AstNode parseTerm() {
    AstNode left = parseUnary();

    while (current().type() == Token.Type.MULTIPLY || current().type() == Token.Type.DIVIDE) {
      Token op = consume();
      AstNode right = parseUnary();
      BinaryOpNode.Operator operator =
          op.type() == Token.Type.MULTIPLY
              ? BinaryOpNode.Operator.MULTIPLY
              : BinaryOpNode.Operator.DIVIDE;
      left = new BinaryOpNode(left, operator, right);
    }

    return left;
  }

  // unary = '-' unary | primary
  private AstNode parseUnary() {
    if (current().type() == Token.Type.MINUS) {
      consume();
      AstNode operand = parseUnary();
      return new UnaryMinusNode(operand);
    }

    return parsePrimary();
  }

  // primary = NUMBER | '(' expression ')'
  private AstNode parsePrimary() {
    Token token = current();

    if (token.type() == Token.Type.NUMBER) {
      consume();
      try {
        return new NumberNode(Integer.parseInt(token.value()));
      } catch (NumberFormatException e) {
        throw new InvalidExpressionException(
            "Number too large: '" + token.value() + "' at position " + token.position());
      }
    }

    if (token.type() == Token.Type.LPAREN) {
      consume();
      AstNode node = parseExpression();

      if (current().type() != Token.Type.RPAREN) {
        throw new InvalidExpressionException("Expected ')' at position " + current().position());
      }
      consume();
      return node;
    }

    throw new InvalidExpressionException(
        "Expected number or '(' at position " + token.position() + ", got '" + token.value() + "'");
  }

  private Token current() {
    return tokens.get(pos);
  }

  private Token consume() {
    Token token = tokens.get(pos);
    pos++;
    return token;
  }
}
