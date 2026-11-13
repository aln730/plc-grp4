package created;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private static final ArrayList<HashMap<String, String>> scopes = new ArrayList<>();

    // Stores the function's parameters and the type of what it should return
    public static class FunctionInfo {
        public final ArrayList<String> paramTypes;
        public final String returnType;

        public FunctionInfo(ArrayList<String> paramTypes, String returnType) {
            this.paramTypes = paramTypes;
            this.returnType = returnType;
        }
    }

    // HashMap that holds all of the existing functions 
    private static final HashMap<String, FunctionInfo> functions = new HashMap<>();
    private static String currReturn;

    // Resets variables to ensure that old code doesn't override new code
    public static void reset() {
        scopes.clear();
        functions.clear();
        currReturn = null;
    }

    // If there is a new scope (function body or if/while statements), add a new scope
    public static void pushScope() {
        scopes.add(new HashMap<>());
    }

    // When exiting statement or function body, removes the scope
    public static void popScope() {
        scopes.remove(scopes.size() - 1);
    }

    // Declares a new variable 
    public static void declare(String name, String type) {
        if (scopes.isEmpty()) {
            throw new IllegalStateException("No scope has been declared");
        }
        if (scopes.get(scopes.size() - 1).containsKey(name)) {
            throw new IllegalStateException("Variable has already been declared: " + name);
        }
        scopes.get(scopes.size() - 1). put(name, type);
    }

    public static String typeOf(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                return scopes.get(i).get(name);
            }
        }
        throw new IllegalStateException("Variable has not been declared: " + name);
    }

    public static void addFunction(String name, FunctionInfo info) {
        functions.put(name, info);
    }

    public static FunctionInfo getFunctionName(String name) {
        return functions.get(name);
    }

    public static void setCurrReturn(String type) {
        currReturn = type;
    }

    public static String getCurrReturn() {
        return currReturn;
    }
}
