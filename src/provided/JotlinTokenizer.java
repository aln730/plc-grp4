package provided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JotlinTokenizer {

  public static ArrayList<JotlinToken> tokenize(String fileName) {
    File file = new File(fileName);
    try (Scanner reader = new Scanner(file)) {
      ArrayList<JotlinToken> tokens = new ArrayList<>();
      int lineNumber = 1;

      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        ArrayList<Character> lineChars = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
          lineChars.add(line.charAt(i));
        }

        boolean beginning = true;

        while (!lineChars.isEmpty()) {
          char first = lineChars.getFirst();

          if (beginning && (first == '\t' || first == ' ')) {
            if (first == '\t') {
              tokens.add(tokenize_tab(fileName, lineNumber, false));
              lineChars.removeFirst();
            } else {
              int spaceRun = 0;
              while (spaceRun < lineChars.size() && lineChars.get(spaceRun) == ' ') {
                spaceRun++;
              }
              if (spaceRun % 4 != 0) {
                throw new IllegalStateException(
                    "Syntax Error\nIndentation must be a tab or a multiple of 4 spaces.\n"
                        + fileName
                        + ":"
                        + lineNumber);
              }
              int tabCount = spaceRun / 4;
              for (int i = 0; i < spaceRun; i++) {
                lineChars.removeFirst();
              }
              for (int t = 0; t < tabCount; t++) {
                tokens.add(tokenize_tab(fileName, lineNumber, true));
              }
            }
            continue;
          }

          if (first == ' ') {
            lineChars.removeFirst();
            continue;
          }
          if (first == '\t') {
            throw new IllegalStateException(
                "Syntax Error\nTab character found in the middle of a line.\n"
                    + fileName
                    + ":"
                    + lineNumber);
          }

          beginning = false;

          if (first == '#') {
            lineChars.clear();
            continue;
          }

          if (Character.isDigit(first) || first == '.') {
            if (first == '.' && !(lineChars.size() > 1 && Character.isDigit(lineChars.get(1)))) {
              tokens.add(tokenize_symbol(lineChars, fileName, lineNumber));
            } else {
              tokens.add(tokenize_numbers(lineChars, fileName, lineNumber));
            }
            continue;
          }

          if (isSymbolStart(first)) {
            tokens.add(tokenize_symbol(lineChars, fileName, lineNumber));
            continue;
          }

          if (isMathRelOpStart(first)) {
            tokens.add(tokenize_math_rel_op(lineChars, fileName, lineNumber));
            continue;
          }

          if (Character.isUpperCase(first) || Character.isLowerCase(first) || first == '\"') {
            tokens.add(tokenizeStrings(lineChars, fileName, lineNumber));
            continue;
          }

          throw new IllegalStateException(
              "Syntax Error\nUnhandled character '" + first + "'.\n" + fileName + ":" + lineNumber);
        }

        tokens.add(tokenize_newline(fileName, lineNumber));
        lineNumber++;
      }

      return tokens;
    } catch (FileNotFoundException e) {
      System.out.println("Provided filename not found: " + fileName);
      return new ArrayList<>();
    } catch (IllegalStateException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return new ArrayList<>();
    }
  }

  private static JotlinToken tokenizeStrings(
      ArrayList<Character> line, String filename, int lineNum) {
    StringBuilder sb = new StringBuilder();

    if (line.getFirst() == '\"') {
      line.removeFirst();
      boolean closed = false;
      while (!line.isEmpty()) {
        char c = line.getFirst();
        if (c == '\"') {
          line.removeFirst();
          closed = true;
          break;
        }
        if (!(Character.isLetterOrDigit(c) || c == ' ')) {
          throw new IllegalStateException(
              "Syntax Error\nStrings may only contain letters, numbers, and spaces.\n"
                  + filename
                  + ":"
                  + lineNum);
        }
        sb.append(line.removeFirst());
      }
      if (!closed) {
        throw new IllegalStateException(
            "Syntax Error\nUnterminated string literal.\n" + filename + ":" + lineNum);
      }
      return new JotlinToken(sb.toString(), TokenType.String, filename, lineNum);
    }

    if (Character.isUpperCase(line.getFirst())) {
      sb.append(line.removeFirst());
      while (!line.isEmpty()
          && (Character.isLetter(line.getFirst()) || Character.isDigit(line.getFirst()))) {
        sb.append(line.removeFirst());
      }
      return new JotlinToken(sb.toString(), TokenType.Keyword, filename, lineNum);
    }

    if (Character.isLowerCase(line.getFirst())) {
      sb.append(line.removeFirst());
      while (!line.isEmpty()
          && (Character.isLetter(line.getFirst()) || Character.isDigit(line.getFirst()))) {
        sb.append(line.removeFirst());
      }
      return new JotlinToken(sb.toString(), TokenType.Id, filename, lineNum);
    }

    throw new IllegalStateException(
        "Syntax Error\nUnhandled character '"
            + line.getFirst()
            + "'.\n"
            + filename
            + ":"
            + lineNum);
  }

  public static JotlinToken tokenize_numbers(
      ArrayList<Character> line, String filename, int lineNumber) {
    String token = "";

    if (line.getFirst() == '.') {
      token += line.removeFirst();
      if (line.size() > 0 && Character.isDigit(line.getFirst())) {
        while (line.size() > 0 && Character.isDigit(line.getFirst())) {
          token += line.removeFirst();
        }
        return new JotlinToken(token, TokenType.Double, filename, lineNumber);
      } else {
        throw new IllegalArgumentException(
            "Syntax Error\nDouble token must have at least one digit after the dot.\n"
                + filename
                + ":"
                + lineNumber);
      }
    } else {
      while (line.size() > 0 && Character.isDigit(line.getFirst())) {
        token += line.removeFirst();
      }
      if (line.size() > 0 && line.getFirst() == '.') {
        token += line.removeFirst();
        while (line.size() > 0 && Character.isDigit(line.getFirst())) {
          token += line.removeFirst();
        }
        return new JotlinToken(token, TokenType.Double, filename, lineNumber);
      } else {
        return new JotlinToken(token, TokenType.Integer, filename, lineNumber);
      }
    }
  }

  public static JotlinToken tokenize_newline(String fileName, int lineNumber) {
    return new JotlinToken("\\n", TokenType.LineEnd, fileName, lineNumber);
  }

  public static boolean check_tab(ArrayList<Character> line) {
    int spaceCount = 0;
    while (!line.isEmpty()) {
      if (line.getFirst() == ' ') {
        spaceCount++;
        line.removeFirst();
        if (spaceCount == 4) {
          return true;
        }
      } else {
        return false;
      }
    }
    return spaceCount == 4;
  }

  public static JotlinToken tokenize_tab(String fileName, int lineNumber, boolean useSpaces) {
    return new JotlinToken("\\t", TokenType.Indent, fileName, lineNumber);
  }

  private static boolean isSymbolStart(char c) {
    return c == '[' || c == ']' || c == ':' || c == ',' || c == '.';
  }

  public static JotlinToken tokenize_symbol(
      ArrayList<Character> line, String fileName, int lineNumber) {
    char curr = line.getFirst();

    if (curr == '[') {
      line.removeFirst();
      return new JotlinToken("[", TokenType.L_Bracket, fileName, lineNumber);
    }
    if (curr == ']') {
      line.removeFirst();
      return new JotlinToken("]", TokenType.R_Bracket, fileName, lineNumber);
    }
    if (curr == ':') {
      line.removeFirst();
      if (!line.isEmpty() && line.getFirst() == ':') {
        line.removeFirst();
        return new JotlinToken("::", TokenType.FC_Header, fileName, lineNumber);
      }
      return new JotlinToken(":", TokenType.Colon, fileName, lineNumber);
    }
    if (curr == ',') {
      line.removeFirst();
      return new JotlinToken(",", TokenType.Comma, fileName, lineNumber);
    }
    if (curr == '.') {
      line.removeFirst();
      return new JotlinToken(".", TokenType.Dot, fileName, lineNumber);
    }

    throw new IllegalStateException(
        "Syntax Error\nUnhandled symbol character '" + curr + "'.\n" + fileName + ":" + lineNumber);
  }

  private static boolean isMathRelOpStart(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/' || c == '>' || c == '<' || c == '='
        || c == '!';
  }

  public static JotlinToken tokenize_math_rel_op(
      ArrayList<Character> line, String fileName, int lineNumber) {
    char curr = line.getFirst();

    if (curr == '+') {
      line.removeFirst();
      return new JotlinToken("+", TokenType.MathOp, fileName, lineNumber);
    }
    if (curr == '*') {
      line.removeFirst();
      return new JotlinToken("*", TokenType.MathOp, fileName, lineNumber);
    }
    if (curr == '/') {
      line.removeFirst();
      return new JotlinToken("/", TokenType.MathOp, fileName, lineNumber);
    }
    if (curr == '-') {
      line.removeFirst();
      if (!line.isEmpty() && line.getFirst() == '>') {
        line.removeFirst();
        return new JotlinToken("->", TokenType.R_Arrow, fileName, lineNumber);
      }
      return new JotlinToken("-", TokenType.MathOp, fileName, lineNumber);
    }
    if (curr == '>') {
      line.removeFirst();
      if (!line.isEmpty() && line.getFirst() == '=') {
        line.removeFirst();
        return new JotlinToken(">=", TokenType.RelOp, fileName, lineNumber);
      }
      return new JotlinToken(">", TokenType.RelOp, fileName, lineNumber);
    }
    if (curr == '<') {
      line.removeFirst();
      if (!line.isEmpty() && line.getFirst() == '=') {
        line.removeFirst();
        return new JotlinToken("<=", TokenType.RelOp, fileName, lineNumber);
      }
      if (!line.isEmpty() && line.getFirst() == '-') {
        line.removeFirst();
        return new JotlinToken("<-", TokenType.L_Arrow, fileName, lineNumber);
      }
      return new JotlinToken("<", TokenType.RelOp, fileName, lineNumber);
    }
    if (curr == '=') {
      line.removeFirst();
      if (!line.isEmpty() && line.getFirst() == '=') {
        line.removeFirst();
        return new JotlinToken("==", TokenType.RelOp, fileName, lineNumber);
      }
      return new JotlinToken("=", TokenType.Assign, fileName, lineNumber);
    }
    if (curr == '!') {
      line.removeFirst();
      if (!line.isEmpty() && line.getFirst() == '=') {
        line.removeFirst();
        return new JotlinToken("!=", TokenType.RelOp, fileName, lineNumber);
      }
      throw new IllegalStateException(
          "Syntax Error\n! expects a following =.\n" + fileName + ":" + lineNumber);
    }

    throw new IllegalStateException(
        "Syntax Error\nUnhandled operator character '"
            + curr
            + "'.\n"
            + fileName
            + ":"
            + lineNumber);
  }
}
