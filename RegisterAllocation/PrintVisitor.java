package RegisterAllocation;

import cs132.util.SourcePos;
import cs132.vapor.ast.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class PrintVisitor extends VInstr.VisitorPR<MyPara, MyReturn, Exception> {
    public Printer printer = new Printer();

    public PrintVisitor() {

    }

    private String findRegOrLocal(Interval interval, LinkedHashMap<Interval, Register> registerAllocation, LinkedHashMap<Interval, String> memoryAllocation) {

        String reg;
        if (registerAllocation.get(interval) != null) {
            reg = registerAllocation.get(interval).toString();
        } else if (memoryAllocation.get(interval) != null) {
            reg = memoryAllocation.get(interval);
        } else if (!interval.hasPos) {
            reg = interval.varName; //return the number or name directly.
        } else {
            reg = null;
        }
        return reg;
    }

    private Interval findIntervalOfVar(String varName, SourcePos sourcePos, LinkedHashMap<String, HashSet<Interval>> intervalMap) {
        HashSet<Interval> intervals = intervalMap.get(varName);

        if (varName.matches("\\d+") || varName.matches("^:.*")) { // the var is number or ":" initialized.
            return new Interval(varName);
        } else if (intervals == null) {
            System.err.println("interval of var not found, varName: " + varName + " Pos: " + sourcePos);
            return null;
        } else {
            for (Interval interval : intervals) {
                int startPosOfInterval = interval.start.line;
                int endPosOfInterval = interval.end.line;
                if (sourcePos.line >= startPosOfInterval && sourcePos.line <= endPosOfInterval) {
                    return interval;
                }
            }
            System.err.println("interval position of var not fit");
            return null;
        }

    }

    @Override
    public MyReturn visit(MyPara myPara, VAssign vAssign) throws Exception {
        /*replace dest with reg or mem location*/
        Interval destInterval = findIntervalOfVar(vAssign.dest.toString(), vAssign.dest.sourcePos, myPara.intervalMap);
        String destVapormName;
        if (myPara.registerAllocation.get(destInterval) != null) {
            destVapormName = myPara.registerAllocation.get(destInterval).toString();
        } else {
            destVapormName = myPara.memoryAllocation.get(destInterval);
        }

        /*replace source with reg or mem location*/
        Interval sourceInterval = findIntervalOfVar(vAssign.dest.toString(), vAssign.dest.sourcePos, myPara.intervalMap);
        String sourceVapormName;
        if (myPara.registerAllocation.get(sourceInterval) != null) {
            sourceVapormName = myPara.registerAllocation.get(sourceInterval).toString();
        } else {
            sourceVapormName = myPara.memoryAllocation.get(sourceInterval);
        }
        printer.println(destVapormName + " = " + sourceVapormName);

        return null;
    }

    @Override
    public MyReturn visit(MyPara myPara, VCall vCall) throws Exception {
        /*construct var,interval to register*/
        int localIndex = myPara.memoryAllocation.size();
        LinkedHashMap<String, String> save$tMap = new LinkedHashMap<>(); // register->local[x]
        HashSet<String> activeSet = myPara.dfGraph.getNode(vCall.sourcePos.toString()).sets.active;
        /*save $t to local*/
        for (String varName : activeSet) {
            Interval varInterval = findIntervalOfVar(varName, vCall.sourcePos, myPara.intervalMap); //use vCall.sourcePos here since they are on the same line
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
                Interval varInterval = findIntervalOfVar(vCall.args[i].toString(), vCall.args[i].sourcePos, myPara.intervalMap);
                String reg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation);
                if (reg == null) {
                    printer.println("error reg is null");
                }
                printer.println("$a" + i + " = " + reg);
            }
        }
        if (vCall.args.length > 4) {
            for (int i = 0; i < 4; i++) {
                Interval varInterval = findIntervalOfVar(vCall.args[i].toString(), vCall.args[i].sourcePos, myPara.intervalMap);
                String reg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation);
                if (reg == null) {
                    printer.println("error reg is null");
                }
                printer.println("$a" + i + " = " + reg);
            }
            for (int i = 0; i < vCall.args.length - 4; i++) {
                Interval varInterval = findIntervalOfVar(vCall.args[i].toString(), vCall.args[i].sourcePos, myPara.intervalMap);
                String reg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation);
                if (reg == null) {
                    printer.println("error reg is null");
                }
                printer.println("out[" + i + "] = " + reg);
            }
        }
        /*print call*/
        Interval addrInterval = findIntervalOfVar(vCall.addr.toString(), vCall.sourcePos, myPara.intervalMap); //vCall.addr doesn't have a sourcePos variable.
        String addrReg = findRegOrLocal(addrInterval, myPara.registerAllocation, myPara.memoryAllocation);
        printer.println("call " + addrReg);

        Interval destInterval = findIntervalOfVar(vCall.dest.toString(), vCall.dest.sourcePos, myPara.intervalMap);
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
        /*dest or no dest / 1 param or 2 param*/
        /*check if the BuiltIn has a destination*/
        boolean hasDest = false;
        if (vBuiltIn.dest != null) {
            hasDest = true;
        }
        if (hasDest) {
            Interval destInterval = findIntervalOfVar(vBuiltIn.dest.toString(), vBuiltIn.dest.sourcePos, myPara.intervalMap);
            String destRegString = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation);
            if (vBuiltIn.op.numParams > 1) {
                printer.println(destRegString + " = " + vBuiltIn.op.name + "(" + vBuiltIn.args[0].toString() + " " + vBuiltIn.args[1].toString() + ")");
            } else {
                printer.println(destRegString + " = " + vBuiltIn.op.name + "(" + vBuiltIn.args[0].toString() + ")");
            }
        } else { //Error("bla bla bla")
            printer.println(vBuiltIn.op.name + "(" + vBuiltIn.args[0] + ")");
        }

        return null;
    }

    @Override
    public MyReturn visit(MyPara myPara, VMemWrite vMemWrite) throws Exception {
        if (vMemWrite.dest instanceof VMemRef.Global) { //MemRef: Global or Stack
            Interval destInterval = findIntervalOfVar(((VMemRef.Global) vMemWrite.dest).base.toString(), vMemWrite.dest.sourcePos, myPara.intervalMap);
            String destRegString = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation);
            int byteOffset = ((VMemRef.Global) vMemWrite.dest).byteOffset;
            if (byteOffset == 0) {
                printer.println("[" + destRegString + "] = " + vMemWrite.source.toString());
            } else {
                printer.println("[" + destRegString + "+" + byteOffset + "] = " + vMemWrite.source.toString());
            }
        }

        return null;
    }

    @Override
    public MyReturn visit(MyPara myPara, VMemRead vMemRead) throws Exception {
        Interval destInterval = findIntervalOfVar(vMemRead.dest.toString(), vMemRead.dest.sourcePos, myPara.intervalMap);
        String destRegString = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation);
        if (vMemRead.source instanceof VMemRef.Global) { //MemRef: Global or Stack
            Interval sourceInterval = findIntervalOfVar(((VMemRef.Global) vMemRead.source).base.toString(), vMemRead.source.sourcePos, myPara.intervalMap);
            String sourceRegString = findRegOrLocal(sourceInterval, myPara.registerAllocation, myPara.memoryAllocation);
            int byteOffset = ((VMemRef.Global) vMemRead.source).byteOffset;
            if (byteOffset == 0) {
                printer.println(destRegString + " = [" + sourceRegString + "]");
            } else {
                printer.println(destRegString + " = [" + sourceRegString + "+" + byteOffset + "]");
            }
        }
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
