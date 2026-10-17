package created;

import java.util.ArrayList;
import provided.*;


public class FuncDefNode implements JotlinTree{
    private AmodNode node;
    private StaticNode stat;
    private JotlinToken id;
    private FParamsNode params;
    private FReturnNode rtrn;
    private NewLineNode newline;
    private FBodyNode body;

    private FuncDefNode(AmodNode n, StaticNode s, JotlinToken i, FParamsNode p, FReturnNode r, NewLineNode nl, FBodyNode b){
        node = n;
        stat = s;
        id = i;
        params = p;
        rtrn = r;
        newline = nl;
        body = b;
    }

    public static FuncDefNode parse(ArrayList<JotlinToken> tokens){
        if(tokens.get(0).getTokenType() != TokenType.Indent){
            throw new IllegalArgumentException("expected tab");
        }
        tokens.remove(0);
        AmodNode a_mod = AmodNode.parse(tokens);
        //assuming a_mod is not a node
        StaticNode stat = StaticNode.parse(tokens);
        //assuming static is not a node
        JotlinToken id = tokens.get(0);
        if(id.getTokenType() != TokenType.Id){
            throw new IllegalArgumentException("expected id");
        }
        tokens.remove(0);
        FParamsNode param = FParamsNode.parse(tokens);
        FReturnNode ret = FReturnNode.parse(tokens);

        if(tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected new line in function def");
        }
        NewLineNode newLine = NewLineNode.parse(tokens);

        FBodyNode fBody = FBodyNode.parse(tokens);

        return new FuncDefNode(a_mod, stat, id, param, ret, newLine, fBody);
    }

    @Override
    public boolean validate() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
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
