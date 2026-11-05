package created;
import java.util.ArrayList;

import provided.*;


public class VarDecNode implements JotlinTree {
    private final TabNode tabs;
    private final KeywordNode type;
    private final IdNode id;
    private final NewLineNode newLine;

    private VarDecNode(TabNode tabs, KeywordNode type, IdNode id, NewLineNode newLine) {
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
        KeywordNode type = KeywordNode.parse(tokens);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Id) {
            throw new IllegalArgumentException("Expected an identifier in variable declaration");
        }
        IdNode id = IdNode.parse(tokens);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected a newline after variable declaration");
        }
        NewLineNode nl = NewLineNode.parse(tokens);
        return new VarDecNode(tabs,type, id,nl);
    }

    @Override
    public boolean validate() throws Exception {
        return type.validate() && type.isBuiltinType() && id.validate() && tabs.validate() && newLine.validate();
    }

    @Override
    public String convertToJava() {
        String javaType = type.getValue();
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
        return tabs.convertToJava() + javaType + " " + id.convertToJava() + ";" + newLine.convertToJava();
    }

    public String getType() {
        return type.getValue();
    }

    @Override
    public String convertToJotlin() {
        return tabs.convertToJotlin() + type.convertToJotlin() + " " + id.convertToJotlin() + newLine.convertToJotlin();
    }
}