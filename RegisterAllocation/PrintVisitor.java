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

	private String findRegOrLocal(Interval interval, LinkedHashMap<Interval, Register> registerAllocation, LinkedHashMap<Interval, String> memoryAllocation, LinkedHashMap<String, String> paramAllocation) {

		String reg = null;
		if (registerAllocation.get(interval) != null) {
			reg = registerAllocation.get(interval).toString();
		} else if (memoryAllocation.get(interval) != null) {
			reg = memoryAllocation.get(interval);
		} else if (!interval.hasPos) {
			if (paramAllocation.get(interval.varName) != null) {
				reg = paramAllocation.get(interval.varName); // is parameter, return its register.
			}
			if (interval.varName.matches("\\d+") || interval.varName.matches("^:.*")) {
				reg = interval.varName; //return the number or name directly.
			}
		} else {
			reg = null;
			System.err.println("error: no reg found");
		}
		return reg;
	}

	private Interval findIntervalOfVar(String varName, SourcePos sourcePos, LinkedHashMap<String, HashSet<Interval>> intervalMap) {
		HashSet<Interval> intervals = intervalMap.get(varName);
		if (varName.matches("\\d+") || varName.matches("^:.*")) { // the var is number or ":" initialized.
			return new Interval(varName);
		} else if (intervals == null) { //no interval or parameter
//            System.err.println("interval of var not found, varName: " + varName + " Pos: " + sourcePos);
//            return null;
//			System.err.println("interval of var not found or is parameter, varName:" + varName + " Pos: " + sourcePos);
			return new Interval(varName); //return varName and check it in findRegOrLocal
		} else {
			for (Interval interval : intervals) {
				int startPosOfInterval = interval.start.line;
				int endPosOfInterval = interval.end.line;
				if (sourcePos.line >= startPosOfInterval && sourcePos.line <= endPosOfInterval) {
					return interval;
				}
			}
//			System.err.println("interval position of var not fit");
			return null;
		}

	}

	@Override
	public MyReturn visit(MyPara myPara, VAssign vAssign) throws Exception {
		/*replace dest with reg or mem location*/
		Interval destInterval = findIntervalOfVar(vAssign.dest.toString(), vAssign.dest.sourcePos, myPara.intervalMap);

		String destRegString = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);


		/*replace source with reg or mem location*/
		Interval sourceInterval = findIntervalOfVar(vAssign.dest.toString(), vAssign.dest.sourcePos, myPara.intervalMap);
		String sourceRegString = findRegOrLocal(sourceInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);

		printer.println(destRegString + " = " + sourceRegString);

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
			if (myPara.registerAllocation.get(varInterval) != null) {
				Register register = myPara.registerAllocation.get(varInterval);
				if (register.type == 't') {
					save$tMap.put(register.toString(), "local[" + localIndex++ + "]");
				}
			}
		}
//        print $t save map
		for (Map.Entry<String, String> entry : save$tMap.entrySet()) {
			printer.println(entry.getValue() + " = " + entry.getKey());
		}
		/*backup previous $a*/
		for (int i = 0; i < myPara.paramAllocation.size(); i++) {
			printer.println("in[" + i + "] = $a" + i);
		}

		/*set $a, if more than 4, set out[]*/
		LinkedHashMap<String, String> save$aMap = new LinkedHashMap<>();
		if (vCall.args.length <= 4) {
			for (int i = 0; i < vCall.args.length; i++) {
				Interval varInterval = findIntervalOfVar(vCall.args[i].toString(), vCall.args[i].sourcePos, myPara.intervalMap);
				String reg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
				if (reg == null) {
					printer.println("error reg is null");
				}
				printer.println("$a" + i + " = " + reg);
			}
		}
		if (vCall.args.length > 4) {
			for (int i = 0; i < 4; i++) {
				Interval varInterval = findIntervalOfVar(vCall.args[i].toString(), vCall.args[i].sourcePos, myPara.intervalMap);
				String reg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
				if (reg == null) {
					printer.println("error reg is null");
				}
				printer.println("$a" + i + " = " + reg);
			}
			for (int i = 0; i < vCall.args.length - 4; i++) {
				Interval varInterval = findIntervalOfVar(vCall.args[i].toString(), vCall.args[i].sourcePos, myPara.intervalMap);
				String reg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
				if (reg == null) {
					printer.println("error reg is null");
				}
				printer.println("out[" + i + "] = " + reg);
			}
		}
		/*print call*/
		Interval addrInterval = findIntervalOfVar(vCall.addr.toString(), vCall.sourcePos, myPara.intervalMap); //vCall.addr doesn't have a sourcePos variable.
		String addrReg = findRegOrLocal(addrInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
		printer.println("call " + addrReg);

		Interval destInterval = findIntervalOfVar(vCall.dest.toString(), vCall.dest.sourcePos, myPara.intervalMap);
		String destReg = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
		printer.println(destReg + " = " + "$v0");

		/*restore $t regs*/
		for (Map.Entry<String, String> entry : save$tMap.entrySet()) {
			printer.println(entry.getKey() + " = " + entry.getValue());
		}

		for (int i = 0; i < myPara.paramAllocation.size(); i++) {
			printer.println("$a" + i + " = in[" + i + "]");
		}

		return null;
	}

	@Override
	public MyReturn visit(MyPara myPara, VBuiltIn vBuiltIn) throws Exception {
		/*dest or no dest / 1 param or 2 param*/
		/*check if the BuiltIn has a destination*/
		// TODO: 3/6/2020 args might be from parameter. 
		boolean hasDest = false;
		if (vBuiltIn.dest != null) {
			hasDest = true;
		}
		if (hasDest) {
			Interval destInterval = findIntervalOfVar(vBuiltIn.dest.toString(), vBuiltIn.dest.sourcePos, myPara.intervalMap);
			String destRegString = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
			if (vBuiltIn.op.numParams > 1) {
				Interval firstInterval = findIntervalOfVar(vBuiltIn.args[0].toString(), vBuiltIn.args[0].sourcePos, myPara.intervalMap);
				Interval secondInterval = findIntervalOfVar(vBuiltIn.args[1].toString(), vBuiltIn.args[1].sourcePos, myPara.intervalMap);
				String firstRegString = findRegOrLocal(firstInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
				String secondRegString = findRegOrLocal(secondInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
				printer.println(destRegString + " = " + vBuiltIn.op.name + "(" + firstRegString + " " + secondRegString + ")");
			} else {
				Interval firstInterval = findIntervalOfVar(vBuiltIn.args[0].toString(), vBuiltIn.args[0].sourcePos, myPara.intervalMap);
				String firstRegString = findRegOrLocal(firstInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
				printer.println(destRegString + " = " + vBuiltIn.op.name + "(" + firstRegString + ")");
			}
		} else if (vBuiltIn.op.name.equals("PrintIntS")) { //PrintIntS()
			Interval firstInterval = findIntervalOfVar(vBuiltIn.args[0].toString(), vBuiltIn.args[0].sourcePos, myPara.intervalMap);
			String firstRegString = findRegOrLocal(firstInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
			printer.println(vBuiltIn.op.name + "(" + firstRegString + ")");
		} else { //Error("bla bla bla")
			printer.println(vBuiltIn.op.name + "(" + vBuiltIn.args[0] + ")");
		}

		return null;
	}

	@Override
	public MyReturn visit(MyPara myPara, VMemWrite vMemWrite) throws Exception {
		if (vMemWrite.dest instanceof VMemRef.Global) { //MemRef: Global or Stack
			Interval destInterval = findIntervalOfVar(((VMemRef.Global) vMemWrite.dest).base.toString(), vMemWrite.dest.sourcePos, myPara.intervalMap);
			String destRegString = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
			Interval sourceInteravl = findIntervalOfVar(vMemWrite.source.toString(), vMemWrite.source.sourcePos, myPara.intervalMap);
			String sourceRegString = findRegOrLocal(sourceInteravl, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
			int byteOffset = ((VMemRef.Global) vMemWrite.dest).byteOffset;
			if (byteOffset == 0) {
				printer.println("[" + destRegString + "] = " + sourceRegString);
			} else {
				printer.println("[" + destRegString + "+" + byteOffset + "] = " + sourceRegString);
			}
		}

		return null;
	}

	@Override
	public MyReturn visit(MyPara myPara, VMemRead vMemRead) throws Exception {
		Interval destInterval = findIntervalOfVar(vMemRead.dest.toString(), vMemRead.dest.sourcePos, myPara.intervalMap);
		String destRegString = findRegOrLocal(destInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
		if (vMemRead.source instanceof VMemRef.Global) { //MemRef: Global or Stack
			Interval sourceInterval = findIntervalOfVar(((VMemRef.Global) vMemRead.source).base.toString(), vMemRead.source.sourcePos, myPara.intervalMap);
			String sourceRegString = findRegOrLocal(sourceInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
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
		boolean positive = vBranch.positive;
		String target = vBranch.target.toString();
		String valueName = vBranch.value.toString();
		Interval varInterval = findIntervalOfVar(valueName, vBranch.value.sourcePos, myPara.intervalMap);
		String varReg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
		if (positive) {
			printer.println("if " + varReg + " goto " + target);
		} else {
			printer.println("if0 " + varReg + " goto " + target);
		}
		printer.addIndentation();
		return null;
	}

	@Override
	public MyReturn visit(MyPara myPara, VGoto vGoto) throws Exception {
		printer.println("goto " + vGoto.target);
		return null;
	}

	@Override
	public MyReturn visit(MyPara myPara, VReturn vReturn) throws Exception {
		if (vReturn.value == null) {
			printer.println("ret");
		} else {
			Interval varInterval = findIntervalOfVar(vReturn.value.toString(), vReturn.value.sourcePos, myPara.intervalMap);
			String varReg = findRegOrLocal(varInterval, myPara.registerAllocation, myPara.memoryAllocation, myPara.paramAllocation);
			printer.println("$v0 = " + varReg);
			printer.println("ret");
		}
		return null;
	}
}
