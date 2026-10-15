package provided.nodes;

import java.util.ArrayList;

import provided.*;


public class InstVarNode implements JotlinTree {

    private final AmodNode amod;
    private final KeywordNode type;
    private final IdNode id;

    private InstVarNode(AmodNode amod, KeywordNode type, IdNode id) {
        this.amod = amod;
        this.type = type;
        this.id = id;
    }

    public static InstVarNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Indent) {
            throw new IllegalArgumentException("Syntax Error\nExpected one tab before instance variable.\n"
                + location(tokens));
        }
        tokens.remove(0);
        if (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Indent) {
            throw new IllegalArgumentException("Syntax Error\nInstance variable must be indented exactly one tab.\n"
                + location(tokens));
        }

        AmodNode amod = AmodNode.parse(tokens);
        KeywordNode type = KeywordNode.parse(tokens);
        IdNode id = IdNode.parse(tokens);

        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Syntax Error\nExpected a newline after instance variable.\n"
                + location(tokens));
        }
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }
        return new InstVarNode(amod, type, id);
    }

    private static String location(ArrayList<JotlinToken> tokens) {
        return tokens.isEmpty() ? "" : tokens.get(0).getFileName() + ":" + tokens.get(0).getLineNumber();
    }

    @Override
    public boolean validate() throws Exception {
        return amod.validate() && type.validate() && id.validate();
    }

    @Override
    public String convertToJava() {
        return amod.convertToJava() + " " + type.convertToJava() + " " + id.convertToJava() + ";";
    }

    @Override
    public String convertToJotlin() {
        return amod.convertToJotlin() + " " + type.convertToJotlin() + " " + id.convertToJotlin();
    }
}
