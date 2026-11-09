package created;
import java.util.ArrayList;

import provided.*;


public class ExprNode implements JotlinTree{
    private JotlinTree node1;
    private JotlinTree op;
    private JotlinTree node2;


    public ExprNode(JotlinTree node1, JotlinTree op,JotlinTree node2) {
        this.node1 = node1;
        this.op = op;
        this.node2 = node2;
    }

    public static ExprNode parse(ArrayList<JotlinToken> tokens) {
        JotlinToken first = tokens.getFirst();
        //Checks if expression node is a string or a boolean
        if(first.getTokenType() == TokenType.String) {
            StringNode string = StringNode.parse(tokens);
            return new ExprNode(string, null, null); 
        }
        else if(first.getToken().equals("False") || first.getToken().equals("True")){
            BooleanNode bool = BooleanNode.parse(tokens);
            return new ExprNode(bool, null, null);
        }

        //If not a string or boolean, it must start with an operand
        JotlinTree op1 = null;
        try {
            op1 = OperandNode.parse(tokens);
        }
        catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Expression node expects at least one operand node if not boolean or string");
        }
        JotlinTree op = null;
        JotlinToken second = tokens.getFirst();
        if(second.getTokenType() == TokenType.RelOp) {
            op = RelOpNode.parse(tokens);
        }
        else if(second.getTokenType() == TokenType.MathOp) {
            op = MathOpNode.parse(tokens);
        }
        else {
            return new ExprNode(op1,null,null); 
        }
        JotlinTree op2 = null;
        try {
            op2 = OperandNode.parse(tokens);
        }
        catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Expression node with an operand and relop/mathop expects a final operand");
        }
        return new ExprNode(op1,op,op2);
    }

    @Override
    public String convertToJotlin() {
        if(node1 != null && node2 == null) {
            return node1.convertToJotlin();
        }
        else if (node1 != null && node2 != null) {
            return node1.convertToJotlin() + op.convertToJotlin() + node2.convertToJotlin();
        }
        return null;
    }

    @Override
    public String convertToJava() {
        if(node1 != null && node2 == null) {
            return node1.convertToJava();
        }
        else if (node1 != null && node2 != null) {
            return node1.convertToJava() + op.convertToJava() + node2.convertToJava();
        }
        return null;
    }

    @Override
    public boolean validate() throws Exception {
        if(node1 != null && node2 == null) {
            return node1.validate();
        }
        else if (node1 != null && node2 != null) {
            OperandNode op1 = (OperandNode) node1;
            OperandNode op2 = (OperandNode) node2;
            return node1.validate() && op.validate() && node2.validate() && op1.getToken().getTokenType() == op2.getToken().getTokenType();
        }
        return false;
    }
}
