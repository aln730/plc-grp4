package provided;
import java.util.ArrayList;


public class ExprNode {
    private OperandNode oNode;
    private MathOpNode mNode;
    private RelOpNode rNode;
    private OperandNode oNode2;
    private StringNode sNode;
    private BooleanNode bNode;


    public ExprNode(OperandNode oNode, MathOpNode mNode,RelOpNode rNode,OperandNode oNode2,StringNode sNode, BooleanNode bNode) {
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
        OperandNode op1 = null;
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
        OperandNode op2 = null;
        try {
            op2 = OperandNode.parse(tokens);
        }
        catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Expression node with an operand and relop/mathop expects a final operand");
        }
        return new ExprNode(op1,math,rel,op2,null,null);
        // else if(first.getTokenType() == TokenType.Keyword || first.getTokenType() == TokenType.Colon || 
        //     first.getTokenType() == TokenType.Integer || first.getTokenType() == TokenType.Double || 
        //     first.getTokenType() == TokenType.Id) {
        //         OperandNode op1 = OperandNode.parse(tokens);
        //         RelOpNode rel = null;
        //         MathOpNode math = null;
        //         JotlinToken second = tokens.getFirst();
        //         if(second.getTokenType() == TokenType.RelOp) {
        //             rel = RelOpNode.parse(tokens);
        //         }
        //         else if(second.getTokenType() == TokenType.MathOp) {
        //             math = MathOpNode.parse(tokens);
        //         }
        //         else {
        //             return new ExprNode(op1,null,null,null,null,null); 
        //         }
        //         if(first.getTokenType() == TokenType.Keyword || first.getTokenType() == TokenType.Colon || 
        //         first.getTokenType() == TokenType.Integer || first.getTokenType() == TokenType.Double || 
        //         first.getTokenType() == TokenType.Id) {
        //             if(first.getToken().equals("False") || first.getToken().equals("True")){
        //                 throw new IllegalArgumentException("relOp or mathOp must be followed by an operand");
        //             }
        //             else {
        //                 OperandNode op2 = OperandNode.parse(tokens);
        //                 if(rel == null){
        //                     return new ExprNode(op1,math,null,op2,null,null); 
        //                 }
        //                 return new ExprNode(op1,null,rel,op2,null,null); 
        //             }      
        //         }
        // else {
        //     throw new IllegalArgumentException("Doesn't follow expression node format");
        // }
    }

    public String convertToJott() {
        if(oNode != null && oNode2 == null) {
            return oNode.convertToJott();
        }
        else if (oNode != null && oNode2 != null) {
            if(rNode != null) {
                return oNode.convertToJott() + rNode.convertToJott() + oNode2.convertToJott();
            }
            else {
                return oNode.convertToJott() + mNode.convertToJott() + oNode2.convertToJott();
            }
        }
        else if (bNode != null) {
            return bNode.convertToJott();
        }
        else {
            return sNode.convertToJott();
        }
    }

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
