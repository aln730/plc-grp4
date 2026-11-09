package created;

import java.util.ArrayList;

import provided.*;


public class StaticNode implements JotlinTree{
    private JotlinToken keyword;

    public StaticNode(JotlinToken keyword) {
        this.keyword = keyword;
    }

    public static StaticNode parse(ArrayList<JotlinToken> tokens) {
        if(tokens.size() < 0) {
            throw new IllegalArgumentException("Excepted static token, instead got no tokens");
        }
        JotlinToken first = tokens.getFirst();
        if(first.getTokenType() == TokenType.Keyword && first.getToken().equals("Class")) {
            tokens.remove(0);
            return new StaticNode(first);
        }
        return new StaticNode(null);
    }

    @Override
    public boolean validate() throws Exception {
        if(keyword != null) {
            return !keyword.getToken().isEmpty();
        }
        return true;
    }

    @Override
    public String convertToJava() {
        // TODO Auto-generated method stub
        return " static ";
    }

    @Override
    public String convertToJotlin() {
        // TODO Auto-generated method stub
        return keyword != null ?  keyword.getToken() + " ":"";
    }

    
}
