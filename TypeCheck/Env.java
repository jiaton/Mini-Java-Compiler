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
            System.out.println("Please set methodtype");
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
            System.out.println(next.getKey()+" : "+next.getValue().toString());
        }
    }

    public MyType getVariableType(Identifier n) {
        //System.out.println("getting the variable type "+n.f0.toString());
        String identifier = n.f0.toString();
        MyType returnType = null;
        if(!varTable.isEmpty()){
            returnType = varTable.get(identifier);
            //System.out.println("Getting the returnType "+returnType.toString());
        }

        if (isMethod && returnType == null) {                               //If it's in the parameterlist
            MethodType methodtype = methodTable.get(id);
            if(methodtype == null){
                return null;
            }
            //System.out.println("Now list the methods:");
            //ListMethods();
            //System.out.println("Finish listing");
            returnType =methodtype.parameterList.get(identifier);
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

        if (methodTable.get(id)!=null && methodTable.get(id).parameterList.get(n.f1.f0.toString()) != null) {                     // TODO: 1/27/2020 check the return value of a hashmap is null if not found
            System.out.println("The parameters for method are not distincted!");
            exit(-1);
        }
        //System.out.println(id);
        if(methodTable.get(id)==null){
            System.out.println("method parameterlist init error!");
            exit(-1);
        }else{
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
}
