package TypeCheck;

import syntaxtree.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        if(isMethod){

            System.out.println("Type error");
            exit(-1);
        }
        classOfMethod = null;
        superClass = s;

        varTable = new HashMap<String, MyType>();
        methodTable = new HashMap<String, MethodType>();
        //if(methodTable.containsValue(null))
    }

    public Env(String i,boolean is, String s, MethodType methodtype){
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
        //if(methodTable.containsValue(null))
        methodTable.put(id,methodtype);
    }


    public void ListMethods(){
        Iterator<Map.Entry<String, MethodType>> iterator = methodTable.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MethodType> next = iterator.next();

        }
    }

    public MyType getVariableType(Identifier n) {

        String identifier = n.f0.toString();
        MyType returnType = null;
        if(!varTable.isEmpty()){
            returnType = varTable.get(identifier);

        }

        if (isMethod && returnType == null) {                               //If it's in the parameterlist
            MethodType methodtype = methodTable.get(id);
            if(methodtype == null){
                return null;
            }

            //ListMethods();

            returnType =methodtype.parameterList.get(identifier);
        }
        return returnType;
    }

    public MethodType getMethodType(String methodId) {
        return methodTable.get(methodId);
    }

    public void add(FormalParameter n) {
        if (!isMethod) {

            System.out.println("Type error");
            exit(-1);
        }

        if (methodTable.get(id) != null && methodTable.get(id).parameterList.get(n.f1.f0.toString()) != null) {                     // TODO: 1/27/2020 check the return value of a hashmap is null if not found

            System.out.println("Type error");
            exit(-1);
        }

        if (methodTable.get(id) == null) {

            System.out.println("Type error");
            exit(-1);
        } else {
            methodTable.get(id).parameterList.put(n.f1.f0.toString(), MyType.toMyType(n.f0));
        }


    }

    public void addVar(Type t, Identifier i) {
        if (varTable.get(i.f0.toString()) != null) {

            System.out.println("Type error");
            exit(-1);
        }
        varTable.put(i.f0.toString(), MyType.toMyType(t));
    }

    public void addVar(MyType myType, Identifier identifier) {
        if (varTable.get(identifier.f0.toString()) != null) {

            System.out.println("Type error");
            exit(-1);
        }
        varTable.put(identifier.f0.toString(), myType);
    }

    public void addVar(MyType myType, String identifierName) {
        if (varTable.get(identifierName) != null) {

            System.out.println("Type error");
            exit(-1);
        }
        varTable.put(identifierName, myType);
    }

}
