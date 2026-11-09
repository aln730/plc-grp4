package provided;

import created.*;

public class JotlinMain {

    public static void main(String[] args) {
        SymbolTable.reset();
        JotlinTree tree = JotlinParser.parse(JotlinTokenizer.tokenize(args[0]));
        JotlinAnalyzer.analyze(tree);
    }
}
