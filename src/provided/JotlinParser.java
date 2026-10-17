package provided;
import created.*;

import created.ClassNode;
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
        ArrayList<JotlinToken> tokens = JotlinTokenizer.tokenize("C:\\Users\\Alora\\plc-grp4\\src\\provided\\test.jott");
        JotlinTree tree = parse(tokens);
        System.out.println(tree);
    }
}