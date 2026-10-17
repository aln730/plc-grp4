package created;

import java.util.ArrayList;
import provided.*;

public class ClassBodyNode implements JotlinTree {
    private final ArrayList<InstVarNode> instVars;
    private final NewLineNode newLines;
    private final ArrayList<ConstNode> constants;
    private final ArrayList<FuncDefNode> functions;


    private ClassBodyNode(ArrayList<InstVarNode> instVars, NewLineNode newLines,ArrayList<ConstNode> constants, ArrayList<FuncDefNode> functions) {
        this.instVars = instVars;
        this.constants = constants;
        this.functions = functions;
        this.newLines = newLines;

    }

    public static ClassBodyNode parse(ArrayList<JotlinToken> tokens) {
        ArrayList<InstVarNode> instVars = new ArrayList<>();
        ArrayList<ConstNode> constants = new ArrayList<>();
        ArrayList<FuncDefNode> functions = new ArrayList<>();
        NewLineNode nl = null;
        if(!startsInstVar(tokens)) {
            nl = NewLineNode.parse(tokens);
        }
        while (startsInstVar(tokens)) {
            instVars.add(InstVarNode.parse(tokens));
        }
        while (startsConst(tokens)) {
            constants.add(ConstNode.parse(tokens));
        }
        // consumeNewlines(tokens);
        while (startsFuncDef(tokens)) {
            functions.add(FuncDefNode.parse(tokens));
        }
        // consumeNewlines(tokens);
        return new ClassBodyNode(instVars, nl,constants, functions);
    }

    private static boolean startsInstVar(ArrayList<JotlinToken> tokens) {
        if (tokens.size() < 3) {
            return false;
        }
        return tokens.get(0).getTokenType() == TokenType.Indent && tokens.get(1).getTokenType() == TokenType.Keyword
                && (tokens.get(2).getTokenType() == TokenType.Id);
    }

    private static boolean startsConst(ArrayList<JotlinToken> tokens) {
        if (tokens.size() < 2 || tokens.get(0).getTokenType() != TokenType.Indent) {
            return false;
        }
        String modifier = tokens.get(1).getToken();
        return modifier.equals("Open") || modifier.equals("Closed") || modifier.equals("Shared");
    }

    private static boolean startsFuncDef(ArrayList<JotlinToken> tokens) {
        if (tokens.size() < 3 || tokens.get(0).getTokenType() != TokenType.Indent) {
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
        return next < tokens.size() && (tokens.get(next).getTokenType() == TokenType.Id || tokens.get(next).getTokenType() == TokenType.Keyword);
    }

    private static void consumeNewlines(ArrayList<JotlinToken> tokens) {
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }
    }

    @Override
    public boolean validate() throws Exception {
        for (InstVarNode instVar : instVars) {
            if (!instVar.validate()) {
                return false;
            }
        }
        for (ConstNode constant : constants) {
            if (!constant.validate()) {
                return false;
            }
        }
        for (FuncDefNode function : functions) {
            if (!function.validate()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        for (InstVarNode instVar : instVars) {
            result.append(instVar.convertToJava()).append("\n");
        }
        for (ConstNode constant : constants) {
            result.append(constant.convertToJava()).append("\n");
        }
        for (FuncDefNode function : functions) {
            result.append(function.convertToJava()).append("\n");
        }
        return result.toString();
    }

    @Override
    public String convertToJotlin() {
        StringBuilder result = new StringBuilder();
        for (InstVarNode instVar : instVars) {
            result.append(instVar.convertToJotlin()).append("\n");
        }
        for (ConstNode constant : constants) {
            result.append(constant.convertToJotlin()).append("\n");
        }
        for (FuncDefNode function : functions) {
            result.append(function.convertToJotlin()).append("\n");
        }
        return result.toString();
    }
}