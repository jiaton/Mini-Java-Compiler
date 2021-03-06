package J2V;

import syntaxtree.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.System.exit;

public class Env {
    String id;
    boolean isMethod;
    String classOfMethod;
    String superClass;
    HashMap<String, MyType> varTable;
    HashMap<String, MethodType> methodTable;
    LinkedHashMap<String, Integer> record;
    LinkedHashMap<String, Integer> vtable;

    public void printvTable() {
        System.out.println("const vmt_" + id);
        for (Map.Entry<String, Integer> entry : vtable.entrySet()) {
            System.out.println("   :" + entry.getKey());
            //System.out.println("   :"+entry.getKey()+"     test: offset:"+entry.getValue());
        }
    }

    public void vtableDelete(String name) {
        boolean get = false;
        for (Map.Entry<String, Integer> entry : vtable.entrySet()) {
            // TODO: 2/5/2020
            if (get) {
                vtable.replace(entry.getKey(), entry.getValue(), entry.getValue() + 1);
            } else {
                if (name.equals(entry.getKey())) {
                    vtable.remove(name);
                    get = true;
                }
            }

        }
    }

    public Env(String i, boolean is, String s) {
        id = i;
        isMethod = is;
        if (isMethod) {
            System.out.println("Please set methodtype");
            exit(-1);
        }
        classOfMethod = null;
        superClass = s;

        varTable = new HashMap<String, MyType>();
        methodTable = new HashMap<String, MethodType>();
        record = new LinkedHashMap<>();
        vtable = new LinkedHashMap<>();
        //if(methodTable.containsValue(null))
    }

    public Env(String i, boolean is, String s, MethodType methodtype) {
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
        record = new LinkedHashMap<>();
        vtable = new LinkedHashMap<>();
        //if(methodTable.containsValue(null))
        methodTable.put(id, methodtype);
    }


    public void ListMethods() {
        Iterator<Map.Entry<String, MethodType>> iterator = methodTable.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MethodType> next = iterator.next();
            System.out.println(next.getKey() + " : " + next.getValue().toString());
        }
    }

    public MyType getVariableType(Identifier n) {
        //System.out.println("getting the variable type "+n.f0.toString());
        String identifier = n.f0.toString();
        MyType returnType = null;
        if (!varTable.isEmpty()) {
            returnType = varTable.get(identifier);
            //System.out.println("Getting the returnType "+returnType.toString());
        }

        if (isMethod && returnType == null) {                               //If it's in the parameterlist
            MethodType methodtype = methodTable.get(id);
            if (methodtype == null) {
                return null;
            }
            //System.out.println("Now list the methods:");
            //ListMethods();
            //System.out.println("Finish listing");
            returnType = methodtype.parameterList.get(identifier);
        }
        return returnType;
    }

    public MethodType getMethodType(String methodId) {
        return methodTable.get(methodId);
    }

    public void add(FormalParameter n) {
        if (!isMethod) {
            System.out.println("Try to add parameter to a class!");
            exit(-1);
        }

        if (methodTable.get(id) != null && methodTable.get(id).parameterList.get(n.f1.f0.toString()) != null) {                     // TODO: 1/27/2020 check the return value of a hashmap is null if not found
            System.out.println("The parameters for method are not distincted!");
            exit(-1);
        }
        //System.out.println(id);
        if (methodTable.get(id) == null) {
            System.out.println("method parameterlist init error!");
            exit(-1);
        } else {
            methodTable.get(id).parameterList.put(n.f1.f0.toString(), MyType.toMyType(n.f0));
        }

        //System.out.println("Add parameterList to "+id);
    }

    public void addVar(Type t, Identifier i) {
        if (varTable.get(i.f0.toString()) != null) {
            System.out.println("The varibles are not distincted!");
            exit(-1);
        }
        varTable.put(i.f0.toString(), MyType.toMyType(t));
    }

    public void addVar(MyType myType, Identifier identifier) {
        if (varTable.get(identifier.f0.toString()) != null) {
            System.out.println("The varibles are not distincted!");
            exit(-1);
        }
        varTable.put(identifier.f0.toString(), myType);
    }

    public void addVar(MyType myType, String identifierName) {
        if (varTable.get(identifierName) != null) {
            System.out.println("The varibles are not distincted!");
            exit(-1);
        }
        varTable.put(identifierName, myType);
    }

}
