package RegisterAllocation;

import cs132.vapor.ast.*;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class PrintVisitor extends VInstr.VisitorPR<MyPara, MyReturn, Exception> {
    public Printer printer = new Printer();

    public PrintVisitor() {

    }

    ;

    @Override
    public MyReturn visit(MyPara myPara, VAssign vAssign) throws Exception {
        /*replace dest with reg or mem location*/
        Interval destInterval = myPara.intervalMap.get(vAssign.dest.toString());
        String destVapormName;
        if (myPara.registerAllocation.get(destInterval) != null) {
            destVapormName = myPara.registerAllocation.get(destInterval).toString();
        } else {
            destVapormName = myPara.memoryAllocation.get(destInterval);
        }

        /*replace source with reg or mem location*/
        Interval sourceInterval = myPara.intervalMap.get(vAssign.dest.toString());
        String sourceVapormName;
        if (myPara.registerAllocation.get(sourceInterval) != null) {
            sourceVapormName = myPara.registerAllocation.get(sourceInterval).toString();
        } else {
            sourceVapormName = myPara.memoryAllocation.get(sourceInterval);
        }
        printer.println(destVapormName + " = " + sourceVapormName);

        return null;
    }

    private String findRegOrLocal(Interval interval, LinkedHashMap<Interval, Register> registerAllocation, LinkedHashMap<Interval, String> memoryAllocation) {

        String reg;
        if (registerAllocation.get(interval) != null) {
            reg = registerAllocation.get(interval).toString();
        } else if (memoryAllocation.get(interval) != null) {
            reg = memoryAllocation.get(interval);
        } else {
            reg = null;
        }
        return reg;
    }

    @Override
    public MyReturn visit(MyPara myPara, VCall vCall) throws Exception {
        /*construct var,interval to register*/
        int localIndex = myPara.memoryAllocation.size();
        LinkedHashMap<String, String> save$tMap = new LinkedHashMap<>(); // register->local[x]
        HashSet<String> activeSet = myPara.dfGraph.getNode(vCall.sourcePos.toString()).sets.active;
        /*save $t to local*/
        for (String varName : activeSet) {
            Interval varInterval = myPara.intervalMap.get(varName);
            varInterval = new Interval(varInterval.start, varInterval.end, varName);
            if (myPara.registerAllocation.get(varInterval) != null) {
                Register register = myPara.registerAllocation.get(varInterval);
                if (register.type == 't') {
                    save$tMap.put(register.toString(), "local[" + localIndex++ + "]");
                }
                /*if variable in the parameter list still active, save $a in in[]*/
                // TODO: 2020/3/3  if variable in the parameter list still active, save $a in in[]
            }
        }
//        print $t save map
        for (Map.Entry<String, String> entry : save$tMap.entrySet()) {
            printer.println(entry.getValue() + " = " + entry.getKey());
        }

        /*set $a, if more than 4, set out[]*/
        LinkedHashMap<String, String> save$aMap = new LinkedHashMap<>();
        if (vCall.args.length <= 4) {
            for (int i = 0; i < vCall.args.length; i++) {
                Interval varInterval = myPara.intervalMap.get(vCall.args[i].toString());
                String reg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation);
                if (reg == null) {
                    printer.println("error reg is null");
                }
                printer.println("$a" + i + " = " + reg);
            }
        }
        if (vCall.args.length > 4) {
            for (int i = 0; i < 4; i++) {
                Interval varInterval = myPara.intervalMap.get(vCall.args[i].toString());
                String reg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation);
                if (reg == null) {
                    printer.println("error reg is null");
                }
                printer.println("$a" + i + " = " + reg);
            }
            for (int i = 0; i < vCall.args.length - 4; i++) {
                Interval varInterval = myPara.intervalMap.get(vCall.args[i].toString());
                String reg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation);
                if (reg == null) {
                    printer.println("error reg is null");
                }
                printer.println("out[" + i + "] = " + reg);
            }
        }
        /*print call*/
        Interval addrInterval = myPara.intervalMap.get(vCall.addr.toString());
        String addrReg = findRegOrLocal(addrInterval, myPara.registerAllocation, myPara.memoryAllocation);
        printer.println("call " + addrReg);

        Interval destInterval = myPara.intervalMap.get(vCall.dest.toString());
        String destReg = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation);
        printer.println(destReg + " = " + "$v0");

        /*restore $t regs*/
        for (Map.Entry<String, String> entry : save$tMap.entrySet()) {
            printer.println(entry.getKey() + " = " + entry.getValue());
        }
        // TODO: 2020/3/3 restore $a regs


        return null;
    }

    @Override
    public MyReturn visit(MyPara myPara, VBuiltIn vBuiltIn) throws Exception {
        Interval destInterval = myPara.intervalMap.get(vBuiltIn.dest.toString());
        String destRegString = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation);
        if (vBuiltIn.op.numParams > 1) {
            printer.println(destRegString + " = " + vBuiltIn.op.name + "(" + vBuiltIn.args[0].toString() + " " + vBuiltIn.args[1].toString() + ")");
        } else {
            printer.println(destRegString + " = " + vBuiltIn.op.name + "(" + vBuiltIn.args[0].toString() + ")");
        }
        return null;
    }

    @Override
    public MyReturn visit(MyPara myPara, VMemWrite vMemWrite) throws Exception {
        printer.println(vMemWrite.dest.toString() + " = " + vMemWrite.source.toString());
        return null;
    }

    @Override
    public MyReturn visit(MyPara myPara, VMemRead vMemRead) throws Exception {
        printer.println(vMemRead.dest.toString() + " = " + vMemRead.source.toString());
        return null;
    }

    @Override
    public MyReturn visit(MyPara myPara, VBranch vBranch) throws Exception {

        return null;
    }

    @Override
    public MyReturn visit(MyPara myPara, VGoto vGoto) throws Exception {
        return null;
    }

    @Override
    public MyReturn visit(MyPara myPara, VReturn vReturn) throws Exception {
        return null;
    }
}
