package TypeCheck;

import syntaxtree.*;

import java.util.HashMap;

import static java.lang.System.exit;

public class Env {
    String id;
    boolean isMethod;
    String classOfMethod;
    String superClass;
    HashMap<String, MyType> varTable;
    HashMap<String, MethodType> methodTable;

    public Env(String i, boolean is, String s) {
        id = i;
        isMethod = is;
        if (is) {
            classOfMethod = s;
            superClass = null;
        } else {
            classOfMethod = null;
            superClass = s;
        }
        varTable = new HashMap<String, MyType>();
        methodTable = new HashMap<String, MethodType>();
    }

    public MyType getType(Identifier n) {
        String identifier = n.toString();
        MyType returnType = null;
        returnType = varTable.get(identifier);
        if (isMethod && returnType == null) {
            returnType = methodTable.get(id).parameterList.get(identifier);
        }
        return returnType;
    }

    public void add(FormalParameter n) {
        if (!isMethod) {
            System.out.println("Try to add parameter to a class!");
            exit(-1);
        }
        if (methodTable.get(id).parameterList.get(n.f1.toString()) != null) {                     // TODO: 1/27/2020 check the return value of a hashmap is null if not found
            System.out.println("The parameters for method are not distincted!");
            exit(-1);
        }
        methodTable.get(id).parameterList.put(n.f1.toString(), MyType.toMyType(n.f0));
    }

    public void addVar(Type t, Identifier i) {
        if (varTable.get(i.toString()) != null) {
            System.out.println("The varibles are not distincted!");
            exit(-1);
        }
        varTable.put(i.toString(), MyType.toMyType(t));
    }
}
