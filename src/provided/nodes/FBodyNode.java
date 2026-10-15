package provided.nodes;

import java.util.ArrayList;

import provided.*;

public class FBodyNode implements JotlinTree{

    private ArrayList<VarDecNode> varDecNodes;
    private ArrayList<BodyStmtNode> bodyStmtNodes;
    private ReturnNode returnVal;
    private NewLineNode newLines;



    public FBodyNode(ArrayList<VarDecNode> varDecNodes, ArrayList<BodyStmtNode> bodyStmtNodes, ReturnNode returnVal,NewLineNode newLines) {
        this.varDecNodes = varDecNodes;
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnVal = returnVall;
        this.newLines = newLines;
    }

    public static FBodyNode parse(ArrayList<JotlinToken> tokens) {
        if(tokens.size() < 1) {
            throw new IllegalArgumentException("Not enough tokens for fBody");
        }
        JotlinToken first = tokens.getFirst();
        ArrayList<VarDecNode> varNodes = new ArrayList<>();
        ArrayList<BodyStmtNode> bodyNodes = new ArrayList<>();
        boolean loop = true;
        while(loop) {
            try {
                VarDecNode varNode = VarDecNode.parse(tokens);
                varNodes.add(varNode);
            }
            catch (IllegalArgumentException e) {
                loop = false;
            }
        }
        loop = true;
        while (loop) {
            try {
                BodyStmtNode bodyNode = BodyStmtNode.parse(tokens);
                bodyNodes.add(bodyNode);
            }
            catch (IllegalArgumentException e) {
                loop = false;
            }

        }
        ReturnNode returnNode = null;
        NewLineNode newLine = null;
        try {
            returnNode = ReturnNode.parse(tokens);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("FBody missing return");
        }
        try {
            newLine = NewLineNode.parse(tokens);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("FBody missing new line");
        }
        return new FBodyNode(varNodes,bodyNodes,returnNode,newLine);

    }

    @Override
    public boolean validate() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public String convertToJava() {
        String java = "";
        for(int i = 0; i< varDecNodes.size(); i++) {
            java += varDecNodes.get(i).convertToJava();
        }
        for(int i = 0; i< bodyStmtNodes.size(); i++) {
            java += bodyStmtNodes.get(i).convertToJava();
        }
        java += returnVal.convertToJava();
        java += newLines.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        String jotlin = "";
        for(int i = 0; i< varDecNodes.size(); i++) {
            jotlin += varDecNodes.get(i).convertToJotlin();
        }
        for(int i = 0; i< bodyStmtNodes.size(); i++) {
            jotlin += bodyStmtNodes.get(i).convertToJotlin();
        }
        jotlin += returnVal.convertToJotlin();
        jotlin += newLines.convertToJotlin();    
    }

}
