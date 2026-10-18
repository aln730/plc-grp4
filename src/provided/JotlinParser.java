package provided;
import created.*;
import java.util.ArrayList;

public class JotlinParser {

    public static JotlinTree parse(ArrayList<JotlinToken> tokens) {
        JotlinTree root = ClassNode.parse(tokens);

        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.LineEnd) {
            tokens.remove(0);
        }
        if (!tokens.isEmpty()) {
            throw new IllegalArgumentException(
                "Unexpected tokens after the end of the class: " + tokens.get(0));
        }

        return root;
    }

    public static void main(String[] args) {
        for (String path : args) {
            System.out.println(path);
            try {
                JotlinTree tree = parse(JotlinTokenizer.tokenize(path));
                System.out.println(tree.convertToJotlin());
                System.out.println("PASS");
            } catch (Exception e) {
                System.out.println("FAIL: " + e);
            }
        }
    }
}
