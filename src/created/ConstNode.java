package created;

import java.util.ArrayList;
import provided.*;

//< const > -> <tab > < a_mod > < keyword > < f_params > < body > < NLS >

public class ConstNode implements JotlinTree{
    private AmodNode node;
    private KeywordNode keyword;
    private FParamNode params;
    private BodyNode body;
    private NewLineNode newLine;

    private ConstNode(AmodNode n, KeywordNode k, FParamNode p, BodyNode b, NewLineNode nl){
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
        FParamNode fparam = FParamNode.parse(tokens);
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    @Override
    public String convertToJotlin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJotlin'");
    }
}
