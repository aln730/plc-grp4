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

    consumeNewlines(tokens);
    while (startsInstVar(tokens)) {
        instVars.add(InstVarNode.parse(tokens));
        consumeNewlines(tokens);
    }
    while (startsConst(tokens)) {
        constants.add(ConstNode.parse(tokens));
        consumeNewlines(tokens);
    }
    while (startsFuncDef(tokens)) {
        functions.add(FuncDefNode.parse(tokens));
        consumeNewlines(tokens);
    }
    return new ClassBodyNode(instVars, null, constants, functions);
}

private static boolean isAccessModifier(String text) {
    return text.equals("Open") || text.equals("Closed") || text.equals("Shared");
}

private static boolean startsInstVar(ArrayList<JotlinToken> t) {
    if (t.size() < 3 || t.get(0).getTokenType() != TokenType.Indent) {
        return false;
    }
    int i = 1;
    if (isAccessModifier(t.get(i).getToken())) {
        i++;
        if (i + 1 >= t.size() || t.get(i).getToken().equals("Class")) {
            return false;
        }
    }
    return i + 1 < t.size()
            && t.get(i).getTokenType() == TokenType.Keyword
            && t.get(i + 1).getTokenType() == TokenType.Id;
}

private static boolean startsConst(ArrayList<JotlinToken> t) {
    if (t.size() < 4 || t.get(0).getTokenType() != TokenType.Indent) {
        return false;
    }
    if (!isAccessModifier(t.get(1).getToken())) {
        return false;
    }
    JotlinToken type = t.get(2);
    TokenType after = t.get(3).getTokenType();
    return type.getTokenType() == TokenType.Keyword
            && !type.getToken().equals("Class")
            && (after == TokenType.R_Arrow || after == TokenType.LineEnd);
}

private static boolean startsFuncDef(ArrayList<JotlinToken> tokens) {
    if (tokens.size() < 3 || tokens.get(0).getTokenType() != TokenType.Indent) {
        return false;
    }
    if (!isAccessModifier(tokens.get(1).getToken())) {
        return false;
    }
    int next = 2;
    if (tokens.get(next).getTokenType() == TokenType.Keyword
            && tokens.get(next).getToken().equals("Class")) {
        next++;
    }
    return next < tokens.size() && tokens.get(next).getTokenType() == TokenType.Id;
}

private static void consumeNewlines(ArrayList<JotlinToken> t) {
    while (!t.isEmpty()) {
        int i = 0;
        while (i < t.size() && t.get(i).getTokenType() == TokenType.Indent) {
            i++;
        }
        if (i < t.size() && t.get(i).getTokenType() != TokenType.LineEnd) {
            return;
        }
        for (int k = 0; k < i; k++) {
            t.remove(0);
        }
        if (!t.isEmpty()) {
            t.remove(0);
        }
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