import cs132.util.ProblemException;
import cs132.vapor.ast.*;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;



public class V2VM<MyPara,MyReturn,Throwable extends java.lang.Throwable> extends VInstr.VisitorPR<MyPara,MyReturn,Throwable> {
    public static VaporProgram parseVapor(InputStream in, PrintStream err) throws IOException {
        Op[] ops = {
                Op.Add, Op.Sub, Op.MulS, Op.Eq, Op.Lt, Op.LtS,
                Op.PrintIntS, Op.HeapAllocZ, Op.Error,
        };
        boolean allowLocals = true;
        String[] registers = null;
        boolean allowStack = false;

        VaporProgram tree;
        try {
            tree = VaporParser.run(new InputStreamReader(in), 1, 1,
                    java.util.Arrays.asList(ops),
                    allowLocals, registers, allowStack);
        }
        catch (ProblemException ex) {
            err.println(ex.getMessage());
            return null;
        }
        return tree;
    }
    public static void main(String[] args) throws IOException {
        PrintStream err = new PrintStream(System.out);
        VaporProgram tree = parseVapor(System.in,err);
        IOGenerator io=new IOGenerator();
        for(VFunction function : tree.functions){
            for(VInstr Instr : function.body){
                MyPara para = new MyPara(io);
                Instr.accept(para,io);
            }
        }
    }
    public MyReturn visit(MyPara var1, VAssign var2) throws Throwable{

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
