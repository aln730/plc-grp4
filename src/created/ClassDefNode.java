package created;

import java.util.ArrayList;
import provided.*;

public class ClassDefNode implements JotlinTree {
    private final JotlinToken moduleName;
    private final JotlinToken subclassName;
    private final ClassBodyNode classBody;

    private ClassDefNode(JotlinToken moduleName, JotlinToken subclassName, ClassBodyNode classBody) {
        this.moduleName = moduleName;
        this.subclassName = subclassName;
        this.classBody = classBody;
    }

    public static ClassDefNode parse(ArrayList<JotlinToken> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected Module");
        }
        JotlinToken first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword || !first.getToken().equals("Module")) {
            throw new IllegalArgumentException("Expected 'Module'");
        }
        tokens.remove(0);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected module name");
        }
        first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword) {
            throw new IllegalArgumentException("Expected module name");
        }
        JotlinToken moduleName = first;
        tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected line ending after module name");
        }
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected Subclass");
        }
        first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword || !first.getToken().equals("Subclass")) {
            throw new IllegalArgumentException("Expected 'Subclass'");
        }
        tokens.remove(0);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected subclass name");
        }
        first = tokens.get(0);
        if (first.getTokenType() != TokenType.Keyword) {
            throw new IllegalArgumentException("Expected subclass name");
        }
        JotlinToken subclassName = first;
        tokens.remove(0);
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.LineEnd) {
            throw new IllegalArgumentException("Expected line ending after subclass name");
        }
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }
        ClassBodyNode classBody = ClassBodyNode.parse(tokens);
        return new ClassDefNode(moduleName, subclassName, classBody);
    }

    @Override
    public boolean validate() throws Exception {
        return classBody.validate();
    }

    @Override
    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        result.append("class ").append(moduleName.getToken()).append(" extends ").append(subclassName.getToken()).append(" {\n");
        result.append(classBody.convertToJava());
        result.append("}\n");
        return result.toString();
    }

    @Override
    public String convertToJotlin() {
        return "Module " + moduleName.getToken() + "\nSubclass " + subclassName.getToken() + "\n" + classBody.convertToJotlin();
    }
}