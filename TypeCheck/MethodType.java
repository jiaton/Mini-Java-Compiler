package TypeCheck;

import syntaxtree.FormalParameterList;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodType {

    ArrayList<MyType> parameterList;
    MyType returnValue;


    public MethodType(ArrayList<MyType> parameterList, MyType returnValue) {
        this.parameterList = parameterList;
        this.returnValue = returnValue;
    }
}
