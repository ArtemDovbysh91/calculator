package com.calculator.astcalculator;

import com.calculator.astcalculator.exception.InvalidExpressionException;
import java.util.ArrayList;
import java.util.List;

public final class Tokenizer {

  /** The input expression string. */
  private final String input;

  /** Current position in the input. */
  private int pos;

  private Tokenizer(final String theInput) {
    this.input = theInput;
    this.pos = 0;
  }

  /**
   * Tokenizes the given input expression.
   *
   * @param input the expression string
   * @return the list of tokens
   */
  public static List<Token> tokenize(final String input) {
    if (input == null) {
      throw new InvalidExpressionException("Input must not be null");
    }
    return new Tokenizer(input).doTokenize();
  }

  private List<Token> doTokenize() {
    List<Token> tokens = new ArrayList<>();

    while (pos < input.length()) {
      char current = input.charAt(pos);

      if (Character.isWhitespace(current)) {
        pos++;
        continue;
      }

      if (Character.isDigit(current)) {
        tokens.add(readNumber());
        continue;
      }

      Token token =
          switch (current) {
            case '+' -> new Token(Token.Type.PLUS, "+", pos);
            case '-' -> new Token(Token.Type.MINUS, "-", pos);
            case '*' -> new Token(Token.Type.MULTIPLY, "*", pos);
            case '/' -> new Token(Token.Type.DIVIDE, "/", pos);
            case '(' -> new Token(Token.Type.LPAREN, "(", pos);
            case ')' -> new Token(Token.Type.RPAREN, ")", pos);
            default ->
                throw new InvalidExpressionException(
                    "Unexpected character '" + current + "' at position " + pos);
          };
      tokens.add(token);
      pos++;
    }

    tokens.add(new Token(Token.Type.EOF, "", pos));
    return tokens;
  }

  private Token readNumber() {
    int start = pos;
    StringBuilder sb = new StringBuilder();

    while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
      sb.append(input.charAt(pos));
      pos++;
    }

    return new Token(Token.Type.NUMBER, sb.toString(), start);
  }
}
