package TypeCheck;

import syntaxtree.FormalParameterList;

import java.util.*;

public class MethodType {
    LinkedHashMap<String, MyType> parameterList;
    MyType returnValue;

    public MethodType(LinkedHashMap<String, MyType> parameterList, MyType returnValue) {
        this.parameterList = parameterList;
        this.returnValue = returnValue;
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
