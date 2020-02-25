import cs132.vapor.ast.*;

import java.util.HashMap;
import java.util.HashSet;

public class IOGenerator <MyPara,MyReturn,Throwable extends java.lang.Throwable> extends VInstr.VisitorPR<MyPara,MyReturn,Throwable> {

    HashMap<Integer, HashSet<String>> InSet;
    HashMap<Integer, HashSet<String>> OutSet;

    public MyReturn visit(MyPara var1, VAssign var2) throws Throwable{
        var2.sourcePos
    }

    public MyReturn visit(MyPara var1, VCall var2) throws Throwable{

    }

    public MyReturn visit(MyPara var1, VBuiltIn var2) throws Throwable{

    }

    public MyReturn visit(MyPara var1, VMemWrite var2) throws Throwable{

    }

    public MyReturn visit(MyPara var1, VMemRead var2) throws Throwable{

    }

    public MyReturn visit(MyPara var1, VBranch var2) throws Throwable{

    }

    public MyReturn visit(MyPara var1, VGoto var2) throws Throwable{

    }

    public MyReturn visit(MyPara var1, VReturn var2) throws Throwable{

    }
}
