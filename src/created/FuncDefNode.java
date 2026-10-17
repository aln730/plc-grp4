package created;

import java.util.ArrayList;
import provided.*;

//< func_def > -> <tab > < a_mod > < static > < id > < f_params > < f_return > < NLS > < f_body >

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
        StaticNode stat = StaticNode.parse(tokens);
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

        return new FuncDefNode(a_mod, stat, newId, param, ret, newLine, fBody);
    }

    @Override
    public boolean validate() throws Exception {
       return node.validate() && stat.validate() && id.validate() && params.validate() 
       && rtrn.validate() && newline.validate() && body.validate();
    }

    @Override
    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        String modifier = node.convertToJava();
        if (modifier != null && !modifier.isEmpty()) {
            result.append(modifier).append(" ");
        }
        String staticModifier = stat.convertToJava();
        if (staticModifier != null && !staticModifier.isEmpty()) {
            result.append(staticModifier).append(" ");
        }
        result.append(rtrn.convertToJava()).append(" ").append(id.convertToJava()).append("(");
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
        result.append(stat.convertToJotlin());
        result.append(id.convertToJotlin());
        result.append(params.convertToJotlin());
        result.append(rtrn.convertToJotlin());
        if (newline != null) {
            result.append(newline.convertToJotlin());
        }
        result.append(body.convertToJotlin());
        return result.toString();
    }

}
