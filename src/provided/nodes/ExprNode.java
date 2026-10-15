package provided.nodes;
import java.util.ArrayList;

import provided.*;


public class ExprNode implements JotlinTree{
    private JotlinTree oNode;
    private MathOpNode mNode;
    private RelOpNode rNode;
    private JotlinTree oNode2;
    private StringNode sNode;
    private BooleanNode bNode;


    public ExprNode(JotlinTree oNode, MathOpNode mNode,RelOpNode rNode,JotlinTree oNode2,StringNode sNode, BooleanNode bNode) {
        this.oNode = oNode;
        this.mNode = mNode;
        this.rNode = rNode;
        this.oNode2 = oNode2;
        this.sNode = sNode;
        this.bNode = bNode;
    }

    public static ExprNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.getFirst();
        //Checks if expression node is a string or a boolean
        if(first.getTokenType() == TokenType.String) {
            StringNode string = StringNode.parse(tokens);
            return new ExprNode(null,null,null,null,string,null); 
        }
        if(first.getToken().equals("False") || first.getToken().equals("True")){
            BooleanNode bool = BooleanNode.parse(tokens);
            return new ExprNode(null,null,null,null,null,bool);
        }

        //If not a string or boolean, it must start with an operand
        JotlinTree op1 = null;
        try {
            op1 = OperandNode.parse(tokens);
        }
        catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Expression node expects at least one operand node if not boolean or string");
        }
        RelOpNode rel = null;
        MathOpNode math = null;
        JotlinToken second = tokens.getFirst();
        if(second.getTokenType() == TokenType.RelOp) {
            rel = RelOpNode.parse(tokens);
        }
        else if(second.getTokenType() == TokenType.MathOp) {
            math = MathOpNode.parse(tokens);
        }
        else {
            return new ExprNode(op1,null,null,null,null,null); 
        }
        JotlinTree op2 = null;
        try {
            op2 = OperandNode.parse(tokens);
        }
        catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Expression node with an operand and relop/mathop expects a final operand");
        }
        return new ExprNode(op1,math,rel,op2,null,null);
    }

    @Override
    public String convertToJotlin() {
        if(oNode != null && oNode2 == null) {
            return oNode.convertToJotlin();
        }
        else if (oNode != null && oNode2 != null) {
            if(rNode != null) {
                return oNode.convertToJotlin() + rNode.convertToJotlin() + oNode2.convertToJotlin();
            }
            else {
                return oNode.convertToJotlin() + mNode.convertToJotlin() + oNode2.convertToJotlin();
            }
        }
        else if (bNode != null) {
            return bNode.convertToJotlin();
        }
        else {
            return sNode.convertToJotlin();
        }
    }

    @Override
    public String convertToJava() {
        if(oNode != null && oNode2 == null) {
            return oNode.convertToJava();
        }
        else if (oNode != null && oNode2 != null) {
            if(rNode != null) {
                return oNode.convertToJava() + rNode.convertToJava() + oNode2.convertToJava();
            }
            else {
                return oNode.convertToJava() + mNode.convertToJava() + oNode2.convertToJava();
            }
        }
        else if (bNode != null) {
            return bNode.convertToJava();
        }
        else {
            return sNode.convertToJava();
        }
    }
}
