package provided;
import created.*;

import java.util.ArrayList;

public class JotlinParser {

    public static JotlinTree parse(ArrayList<JotlinToken> tokens){
        JotlinTree tree = ExprNode.parse(tokens);
        return tree;
    }

    public static void main(String[] args) {
        ArrayList<JotlinToken> tokens = JotlinTokenizer.tokenize("C:\\Users\\Alora\\plc-grp4\\src\\provided\\test.jott");
        JotlinTree tree = parse(tokens);
        System.out.println(tree);
    }
}
