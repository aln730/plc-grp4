package provided.nodes;

import java.util.ArrayList;

import provided.*;


public class TabNode implements JotlinTree {

    private final int count;

    private TabNode(int count) {
        this.count = count;
    }

    public static TabNode parse(ArrayList<JotlinToken> tokens) {
        if (!startsHere(tokens)) {
            throw new IllegalArgumentException("Syntax Error\nExpected indentation.\n"
                + (tokens.isEmpty() ? "" : tokens.get(0).getFileName() + ":" + tokens.get(0).getLineNumber()));
        }
        int count = 0;
        while (startsHere(tokens)) {
            tokens.remove(0);
            count++;
        }
        return new TabNode(count);
    }

    public static TabNode parseExact(ArrayList<JotlinToken> tokens, int depth) {
        String where = tokens.isEmpty() ? "" : tokens.get(0).getFileName() + ":" + tokens.get(0).getLineNumber();
        TabNode node = parse(tokens);
        if (node.count != depth) {
            throw new IllegalArgumentException("Syntax Error\nExpected " + depth + " tab(s) of indentation but found "
                + node.count + ".\n" + where);
        }
        return node;
    }

    public static boolean startsHere(ArrayList<JotlinToken> tokens) {
        return !tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Indent;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean validate() throws Exception {
        return count >= 1;
    }

    @Override
    public String convertToJava() {
        return "    ".repeat(count);
    }

    @Override
    public String convertToJotlin() {
        return "\t".repeat(count);
    }
}
