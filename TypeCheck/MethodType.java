package TypeCheck;

import syntaxtree.FormalParameter;
import syntaxtree.FormalParameterList;
import syntaxtree.MethodDeclaration;
import syntaxtree.Node;

import java.util.*;

public class MethodType {
    LinkedHashMap<String, MyType> parameterList;
    MyType returnValue;

    public MethodType(MyType returntype){
        parameterList=new LinkedHashMap<>();
        returnValue=returntype;
    }

    public void setParameterList(FormalParameterList n) {
        if(n==null) return;
        parameterList.put(n.f0.f1.f0.tokenImage,MyType.toMyType(n.f0.f0));

        Vector<Node> nodes = n.f1.nodes;
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Node next = iterator.next();                    // TODO: 2/1/2020 check iterator.next
            this.setParameterList((FormalParameter)next);
        }
    }

    public void setParameterList(FormalParameter n){
        parameterList.put(n.f1.f0.tokenImage,MyType.toMyType(n.f0));
    }

    public MethodType(LinkedHashMap<String, MyType> parameterList, MyType returnValue) {
        this.parameterList = parameterList;
        this.returnValue = returnValue;
    }

    public String toString(){
        String str = "";
        Iterator<Map.Entry<String, MyType>> iterator = parameterList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MyType> next = iterator.next();
            str+=" "+next.getKey()+" "+next.getValue().toString();
        }
        return str;
    }

    public List<MyType> getParameterTypeList() {
        ArrayList<MyType> parameterTypeList = new ArrayList();
        Iterator<Map.Entry<String, MyType>> iterator = parameterList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MyType> next = iterator.next();
            parameterTypeList.add(next.getValue());
        }
        return parameterTypeList;
    }
}
