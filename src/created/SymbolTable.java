
package created;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SymbolTable {
    private static final ArrayList<HashMap<String, String>> scopes = new ArrayList<>();

    public static class FunctionInfo {
        public final ArrayList<String> paramTypes;
        public final String returnType;

        public FunctionInfo(ArrayList<String> paramTypes, String returnType) {
            this.paramTypes = paramTypes;
            this.returnType = returnType;
        }
    }

    private static final HashMap<String, FunctionInfo> functions = new HashMap<>();
    private static String currReturn;
    private static String currClass;

    private static final HashSet<String> includes = new HashSet<>();
    private static final ArrayList<ArrayList<String>> constructors = new ArrayList<>();

    public static void reset() {
        scopes.clear();
        functions.clear();
        currReturn = null;
        currClass = null;
        includes.clear();
        constructors.clear();
    }

    public static void pushScope() {
        scopes.add(new HashMap<>());
    }

    public static void popScope() {
        scopes.remove(scopes.size() - 1);
    }

    public static void declare(String name, String type) {
        if (scopes.isEmpty()) {
            throw new IllegalStateException("No scope has been declared");
        }
        if (scopes.get(scopes.size() - 1).containsKey(name)) {
            throw new IllegalStateException("Variable has already been declared: " + name);
        }
        scopes.get(scopes.size() - 1).put(name, type);
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

    public static void setCurrClass(String c) {
        currClass = c;
    }

    public static String getCurrClass() {
        return currClass;
    }

    public static void addInclude(String c) {
        includes.add(c);
    }

    public static boolean isIncludedClass(String type) {
        return type != null && includes.contains(type);
    }

    public static boolean canDeferTypeMismatch(String type1, String type2) {
        return isIncludedClass(type1) || isIncludedClass(type2);
    }

    public static boolean isKnownClass(String type) {
        if (type == null) return false;
        switch (type) {
            case "Integer":
            case "Double":
            case "String":
            case "Bool":
            case "Boolean":
            case "List":
                return true;
            default:
                return type.equals(currClass) || includes.contains(type);
        }
    }

    public static void addConstructor(ArrayList<String> types) {
        constructors.add(types);
    }

    public static ArrayList<ArrayList<String>> getConstructors() {
        return constructors;
    }
}