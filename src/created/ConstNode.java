package created;

import java.util.ArrayList;
import provided.*;

//< const > -> <tab > < a_mod > < keyword > < f_params > < body > < NLS >

public class ConstNode implements JotlinTree{
    private AmodNode node;
    private KeywordNode keyword;
    private FParamsNode params;
    private BodyNode body;
    private NewLineNode newLine;

    private ConstNode(AmodNode n, KeywordNode k, FParamsNode p, BodyNode b, NewLineNode nl){
        node = n;
        keyword = k;
        params = p;
        body = b;
        newLine = nl;
    }

    public static ConstNode parse(ArrayList<JotlinToken> tokens){
        if(tokens.get(0).getTokenType() != TokenType.Indent){
            throw new IllegalArgumentException("expected tab");
        }
        tokens.remove(0);
        AmodNode a_mod = AmodNode.parse(tokens);
        KeywordNode key = KeywordNode.parse(tokens);
         //keywordNode.parse(tokens)
        FParamsNode fparam = FParamsNode.parse(tokens);
        BodyNode bdy = BodyNode.parse(tokens);
        if(tokens.get(0).getTokenType() != TokenType.LineEnd){
            throw new IllegalArgumentException("Expected new line");
        }
        NewLineNode nl = NewLineNode.parse(tokens);
        return new ConstNode(a_mod,key,fparam,bdy,nl);
    }

    @Override
    public boolean validate() throws Exception {
        return node.validate() && keyword.validate() && params.validate() && body.validate() && newLine.validate();
    }

    @Override
    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        result.append(node.convertToJava()).append(" ");
        String modifier = node.convertToJava();
        if (modifier.isEmpty()) {
            result.setLength(0);
        }
        result.append("static final ");
        result.append(keyword.convertToJava());
        result.append("(");
        result.append(params.convertToJava());
        result.append(") {\n");
        result.append(body.convertToJava());
        result.append("\n}");
        return result.toString();
    }

    @Override
    public String convertToJotlin() {
        StringBuilder result = new StringBuilder();
        result.append("\t");
        result.append(node.convertToJotlin()).append(" ");
        result.append(keyword.convertToJotlin());
        result.append(params.convertToJotlin());
        result.append("\n");
        result.append(body.convertToJotlin());
        if (newLine != null) {
            result.append(newLine.convertToJotlin());
        }
        return result.toString();
    }
}
