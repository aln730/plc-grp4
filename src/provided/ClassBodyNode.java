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
        while (startsWithIndent(tokens) && !startsWithAccessModifier(tokens)) {
            instVars.add(InstVarNode.parse(tokens));
        }
        consumeNewlines(tokens);
        while (startsWithConstant(tokens)) {
            constants.add(ConstNode.parse(tokens));
        }
        while (startsWithFunction(tokens)) {
            functions.add(FuncDefNode.parse(tokens));
        }
        consumeNewlines(tokens);
        return new ClassBodyNode(instVars, constants, functions);
    }

    private static boolean startsWithIndent(ArrayList<JotlinToken> tokens) {
        return !tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Indent;
    }

    private static boolean startsWithAccessModifier(ArrayList<JotlinToken> tokens) {
        if (tokens.size() < 2 || !startsWithIndent(tokens)) {
            return false;
        }
        String token = tokens.get(1).getToken();
        return token.equals("Open") || token.equals("Closed") || token.equals("Shared");
    }

    private static boolean startsWithConstant(ArrayList<JotlinToken> tokens) {
        if (!startsWithAccessModifier(tokens) || tokens.size() < 3) {
            return false;
        }
        return tokens.get(2).getTokenType() == TokenType.Keyword;
    }

    private static boolean startsWithFunction(ArrayList<JotlinToken> tokens) {
        if (!startsWithAccessModifier(tokens) || tokens.size() < 3) {
            return false;
        }
        String second = tokens.get(2).getToken();
        return second.equals("Class") || tokens.get(2).getTokenType() == TokenType.Id;
    }

    private static void consumeNewlines(ArrayList<JotlinToken> tokens) {
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
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