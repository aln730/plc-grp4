package provided;
import java.util.ArrayList;

public class ClassNode implements JotlinTree {
    private final ArrayList<IncludeNode> includes;
    private final ClassDefNode classDef;

    private ClassNode(ArrayList<IncludeNode> includes, ClassDefNode classDef) {
        this.includes = includes;
        this.classDef = classDef;
    }

    public static ClassNode parse(ArrayList<JotlinToken> tokens) {
        ArrayList<IncludeNode> includes = new ArrayList<>();
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.Keyword && tokens.get(0).getToken().equals("Include")) {
            includes.add(IncludeNode.parse(tokens));
        }
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expected a class definition");
        }
        ClassDefNode classDef = ClassDefNode.parse(tokens);
        return new ClassNode(includes, classDef);
    }

    @Override
    public boolean validate() throws Exception {
        for (IncludeNode include : includes) {
            if (!include.validate()) {
                return false;
            }
        }
        return classDef.validate();
    }

    @Override
    public String convertToJava() {
        StringBuilder result = new StringBuilder();
        for (IncludeNode include : includes) {
            result.append(include.convertToJava());
            result.append("\n");
        }
        result.append(classDef.convertToJava());
        return result.toString();
    }

    @Override
    public String convertToJotlin() {
        StringBuilder result = new StringBuilder();
        for (IncludeNode include : includes) {
            result.append(include.convertToJotlin());
            result.append("\n");
        }
        result.append(classDef.convertToJotlin());
        return result.toString();
    }
}