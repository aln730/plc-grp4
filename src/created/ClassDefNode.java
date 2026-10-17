package created;
import java.util.ArrayList;
import provided.*;


public class ClassDefNode implements JotlinTree {
    private final JotlinToken moduleName;
    private final JotlinToken subclassName;
    private final NewLineNode newLines;
    private final ClassBodyNode classBody;

    private ClassDefNode(JotlinToken moduleName, JotlinToken subclassName, NewLineNode newLines, ClassBodyNode classBody) {
        this.moduleName = moduleName;
        this.subclassName = subclassName;
        this.classBody = classBody;
        this.newLines = newLines;
    }

    public static ClassDefNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected Module");
        }
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword || !first.getToken().equals("Module")) {
            throw new IllegalArgumentException("expected 'Module'");
        }
        tokens.remove(0);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected module name");
        }
        first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword) {
            throw new IllegalArgumentException("expected keyword");
        }
        JotlinToken moduleName = first;
        tokens.remove(0);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected Subclass");
        }
        first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword || !first.getToken().equals("Subclass")) {
            throw new IllegalArgumentException("expected 'Subclass'");
        }
        tokens.remove(0);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected subclass name");
        }
        first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword) {
            throw new IllegalArgumentException("expected keyword");
        }
        JotlinToken subclassName = first;
        tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected line ending after class definition header");
        }
        NewLineNode nl = NewLineNode.parse(tokens);
        ClassBodyNode classBody = ClassBodyNode.parse(tokens);
        return new ClassDefNode(moduleName, subclassName, nl,classBody);
    }

    @Override
    public boolean validate() throws Exception {
        return classBody.validate();
    }

    @Override
    public String convertToJava() {
        return classBody.convertToJava();
    }

    @Override
    public String convertToJotlin() {
        return "Module " + moduleName.getToken() + " Subclass " + subclassName.getToken() + "\n" + classBody.convertToJotlin();
    }
}