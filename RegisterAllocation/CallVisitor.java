package RegisterAllocation;

import cs132.util.SourcePos;
import cs132.vapor.ast.*;

import java.util.*;

public class CallVisitor <MyPara,Sets,Throwable extends java.lang.Throwable> extends VInstr.VisitorPR<MyPara, Sets,Throwable> {
    public HashMap<String, HashSet<String>> calleeMap = new HashMap<>();
    public HashMap<String,HashSet<String>> callerMap = new HashMap();
    public String ident;

    public Sets visit(MyPara var1, VAssign var2) throws Throwable{

        return null;
    }

    public Sets visit(MyPara var1, VCall var2) throws Throwable{
        //set function graph
        if(calleeMap.containsKey(var2.addr.toString())){
            calleeMap.get(var2.addr.toString()).add(":"+ident);
            //System.out.println(var2.addr.toString()+"  callee   "+":"+ident);
        }else{
            calleeMap.put(var2.addr.toString(),new HashSet<>());
            calleeMap.get(var2.addr.toString()).add(":"+ident);
            //System.out.println(var2.addr.toString()+"  callee   "+":"+ident);
        }
        if(callerMap.containsKey(":"+ident)){
            callerMap.get(":"+ident).add(var2.addr.toString());
            //System.out.println(":"+ident+"   caller  "+var2.addr.toString());
        }else{
            callerMap.put(":"+ident,new HashSet<>());
            callerMap.get(":"+ident).add(var2.addr.toString());
            //System.out.println(":"+ident+"   caller  "+var2.addr.toString());
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
