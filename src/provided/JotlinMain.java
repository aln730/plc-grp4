package provided;

import created.*;

public class JotlinMain {

    public static void main(String[] args) {
        SymbolTable.reset();
        JotlinTree tree = JotlinParser.parse(JotlinTokenizer.tokenize(args[0]));
        boolean valid = JotlinAnalyzer.analyze(tree);
        if (!valid) {
            System.out.println("Semantic validation failure");
            return;
        }
        System.out.println(tree.convertToJava());
    }
}
