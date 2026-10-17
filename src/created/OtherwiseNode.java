package created;
import java.util.ArrayList;

import provided.*;


public class OtherwiseNode implements JotlinTree {
    private final TabNode tabs;
    private final BodyNode body;
    private final NewLineNode newLine;

    private OtherwiseNode(TabNode tabs,BodyNode body, NewLineNode newLine) {
        this.tabs = tabs;
        this.body = body;
        this.newLine = newLine;
    }

    public static OtherwiseNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Indent) {
            throw new IllegalArgumentException("Expected indentation before Otherwise");
        }
        TabNode tabs = TabNode.parse(tokens);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Keyword || !tokens.get(0).getToken().equals("Otherwise")) {
            throw new IllegalArgumentException("Expected Otherwise keyword");
        }
        tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected newline after Otherwise");
        }
        NewLineNode newLine = NewLineNode.parse(tokens);
        BodyNode body = BodyNode.parse(tokens);
        return new OtherwiseNode(tabs,body,newLine);
    }

    @Override
    public boolean validate() throws Exception {
        return body.validate();
    }

    @Override
    public String convertToJava() {
        return "else " + body.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        return "Otherwise\n" + body.convertToJotlin();
    }
}