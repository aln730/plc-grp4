package created;
import java.util.ArrayList;

import provided.*;


public class VarDecNode implements JotlinTree {
    private final TabNode tabs;
    private final JotlinToken type;
    private final JotlinToken id;
    private final NewLineNode newLine;

    private VarDecNode(TabNode tabs, JotlinToken type, JotlinToken id, NewLineNode newLine) {
        this.tabs = tabs;
        this.type = type;
        this.id = id;
        this.newLine = newLine;
    }

    public static VarDecNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty()
                || tokens.get(0).getTokenType() != TokenType.Indent) {
            throw new IllegalArgumentException("Expected indentation before variable declaration");
        }
        TabNode tabs = TabNode.parse(tokens);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Keyword) {
            throw new IllegalArgumentException("Expected a type keyword in variable declaration");
        }
        JotlinToken type = tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Id) {
            throw new IllegalArgumentException("Expected an identifier in variable declaration");
        }
        JotlinToken id = tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected a newline after variable declaration");
        }
        NewLineNode nl = NewLineNode.parse(tokens);
        return new VarDecNode(tabs,type, id,nl);
    }

    @Override
    public boolean validate() throws Exception {
        return type != null && id != null && !type.getToken().isEmpty() && !id.getToken().isEmpty();
    }

    @Override
    public String convertToJava() {
        String javaType = type.getToken();
        switch (javaType) {
            case "Integer":
                javaType = "int";
                break;
            case "Double":
                javaType = "double";
                break;
            case "String":
                javaType = "String";
                break;
            case "Boolean":
                javaType = "boolean";
                break;
        }
        return javaType + " " + id.getToken() + ";";
    }

    @Override
    public String convertToJotlin() {
        return type.getToken() + " " + id.getToken();
    }
}