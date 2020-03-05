package RegisterAllocation;

import cs132.util.SourcePos;
import cs132.vapor.ast.*;

import java.util.*;

public class CallVisitor <MyPara,Sets,Throwable extends java.lang.Throwable> extends VInstr.VisitorPR<MyPara, Sets,Throwable> {
    public HashMap<String, HashSet<String>> functionMap = new HashMap<>();
    public String ident;

    public Sets visit(MyPara var1, VAssign var2) throws Throwable{

        return null;
    }

    public Sets visit(MyPara var1, VCall var2) throws Throwable{
        //set function graph
        if(functionMap.containsKey(var2.addr.toString())){
            functionMap.get(var2.addr.toString()).add(ident);
        }else{
            functionMap.put(var2.addr.toString(),new HashSet<>());
        }
        return null;
    }

    public Sets visit(MyPara var1, VBuiltIn var2) throws Throwable{

        return null;
    }

    public Sets visit(MyPara var1, VMemWrite var2) throws Throwable{

        return null;
    }

    public Sets visit(MyPara var1, VMemRead var2) throws Throwable{

        return null;
    }

    public Sets visit(MyPara var1, VBranch var2) throws Throwable{

        return null;
    }

    public Sets visit(MyPara var1, VGoto var2) throws Throwable{

        return null;
    }

    public Sets visit(MyPara var1, VReturn var2) throws Throwable{

        return null;
    }
}
