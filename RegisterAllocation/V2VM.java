package RegisterAllocation;

import cs132.vapor.ast.*;
import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class V2VM extends VInstr.Visitor<Throwable> {
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
        } catch (ProblemException ex) {
            err.println(ex.getMessage());
            return null;
        }

        return tree;
    }

    public V2VM() {
        super();
    }

    @Override
    public void visit(VAssign vAssign) throws Throwable {

    }

    @Override
    public void visit(VCall vCall) throws Throwable {

    }

    @Override
    public void visit(VBuiltIn vBuiltIn) throws Throwable {

    }

    @Override
    public void visit(VMemWrite vMemWrite) throws Throwable {

    }

    @Override
    public void visit(VMemRead vMemRead) throws Throwable {

    }

    @Override
    public void visit(VBranch vBranch) throws Throwable {

    }

    @Override
    public void visit(VGoto vGoto) throws Throwable {

    }

    @Override
    public void visit(VReturn vReturn) throws Throwable {

    }
}
