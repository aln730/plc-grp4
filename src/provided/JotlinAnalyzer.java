package provided;

import created.SymbolTable;

public class JotlinAnalyzer {

    public static boolean analyze(JotlinTree root){
        SymbolTable.reset();
        try {
            return root.validate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
