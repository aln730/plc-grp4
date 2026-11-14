package created;

import java.util.ArrayList;
import provided.*;


public class IdentifierNode implements JotlinTree{
    private JotlinToken token1;
    private JotlinToken token2;

    public IdentifierNode(JotlinToken t1, JotlinToken t2){
        token1 = t1;
        token2 = t2;
    }

    private JotlinToken prefix;   // first part of a 3-part name such as Self.list.count

    public static IdentifierNode parse(ArrayList<JotlinToken> tokens){
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword && first.getTokenType() != TokenType.Id) {
            throw new IllegalArgumentException("Expected Keyword or Id");
        }
        tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.Dot) {
            if (first.getTokenType() == TokenType.Keyword) {
                throw new IllegalArgumentException("Expected dot");
            }
            return new IdentifierNode(null, first);
        }
        tokens.remove(0);
        JotlinToken second = tokens.remove(0);
        if (second.getTokenType() != TokenType.Id) {
            throw new IllegalArgumentException("Expected Id");
        }
        if (tokens.size() > 1 && tokens.get(0).getTokenType() == TokenType.Dot
                && tokens.get(1).getTokenType() == TokenType.Id) {
            tokens.remove(0);
            JotlinToken third = tokens.remove(0);
            IdentifierNode n = new IdentifierNode(second, third);
            n.prefix = first;
            return n;
        }
        return new IdentifierNode(first, second);
    }

    private String pre() {
        return prefix == null ? "" : prefix.getToken() + ".";
    }

    public String getQualifier() {
        return token1 == null ? null : token1.getToken();
    }

    public String getType() {
        if (token1 == null) {
            return SymbolTable.typeOf(token2.getToken());
        }
        if (prefix != null) {
            return null;                         // fields of other objects are virtual in phase 3
        }
        String q = token1.getToken();
        if (q.equals("Self") || q.equals(SymbolTable.getCurrClass())) {
            return SymbolTable.typeOf(token2.getToken());
        }
        return null;                             // obj.field / OtherClass.field are virtual in phase 3
    }

    public String evaluateValue() {
        if (token1 == null || token1.getToken().equals("Self")) {
            return SymbolTable.valueOf(token2.getToken());
        }
        return null;
    }

    public String evaluateType() {
        return getType();
    }

    public String getMemberName() {
        return token2.getToken();
    }

    @Override
    public boolean validate() throws Exception {
        if (token2 == null || token2.getToken().isEmpty()) {
            return false;
        }

        if (token1 == null) {
            SymbolTable.typeOf(token2.getToken());
        }

        return token1 == null || !token1.getToken().isEmpty();
    }

	@Override
    public String convertToJava() {
        if (token1 == null) {
            return token2.getToken();
        }
        return pre() + token1.getToken() + "." + token2.getToken();
    }

    @Override
    public String convertToJotlin() {
        if (token1 == null) {
            return (token2 != null? token2.getToken():"");
        }
        return pre() + (token1 != null? token1.getToken():"") + "." + (token2 != null? token2.getToken():"");
    }

    public String getMember() {
        return token2 == null ? null : token2.getToken();
    }
}
