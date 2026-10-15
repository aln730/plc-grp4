package provided;

import java.util.ArrayList;

public class ClassBodyNode implements JotlinTree {
    private final ArrayList<InstVarNode> instVars;
    private final ArrayList<ConstNode> constants;
    private final ArrayList<FuncDefNode> functions;

    private ClassBodyNode(ArrayList<InstVarNode> instVars, ArrayList<ConstNode> constants, ArrayList<FuncDefNode> functions) {
        this.instVars = instVars;
        this.constants = constants;
        this.functions = functions;
    }

    public static ClassBodyNode parse(ArrayList<JotlinToken> tokens) {
        ArrayList<InstVarNode> instVars = new ArrayList<>();
        ArrayList<ConstNode> constants = new ArrayList<>();
        ArrayList<FuncDefNode> functions = new ArrayList<>();
        while (startsInstVar(tokens)) {
            instVars.add(InstVarNode.parse(tokens));
        }
        consumeNewlines(tokens);
        while (startsConst(tokens)) {
            constants.add(ConstNode.parse(tokens));
        }
        consumeNewlines(tokens);
        while (startsFuncDef(tokens)) {
            functions.add(FuncDefNode.parse(tokens));
        }
        consumeNewlines(tokens);
        return new ClassBodyNode(instVars, constants, functions);
    }

    private static boolean startsInstVar(ArrayList<JotlinToken> tokens) {
        if (tokens.size() < 3) {
            return false;
        }
        return tokens.get(0).getTokenType() == TokenType.Indent && tokens.get(1).getTokenType() == TokenType.Keyword
                && tokens.get(2).getTokenType() == TokenType.Id;
    }

    private static boolean startsConst(ArrayList<JotlinToken> tokens) {
        if (tokens.size() < 2) {
            return false;
        }
        if (tokens.get(0).getTokenType() != TokenType.Indent) {
            return false;
        }
        String modifier = tokens.get(1).getToken();
        return modifier.equals("Open") || modifier.equals("Closed") || modifier.equals("Shared");
    }

    private static boolean startsFuncDef(ArrayList<JotlinToken> tokens) {
        if (tokens.size() < 3) {
            return false;
        }
        if (tokens.get(0).getTokenType() != TokenType.Indent) {
            return false;
        }
        String modifier = tokens.get(1).getToken();
        if (!modifier.equals("Open") && !modifier.equals("Closed") && !modifier.equals("Shared")) {
            return false;
        }
        int next = 2;
        if (tokens.get(next).getTokenType() == TokenType.Keyword && tokens.get(next).getToken().equals("Class")) {
            next++;
        }
        return next < tokens.size() && tokens.get(next).getTokenType() == TokenType.Id;
    }

    private static void consumeNewlines(ArrayList<JotlinToken> tokens) {
        while (!tokens.isEmpty()
                && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }
    }

    @Override
    public boolean validate() throws Exception {
        //todo
    }

    @Override
    public String convertToJava() {
        //todo
    }

    @Override
    public String convertToJotlin() {
        //todo
    }
}