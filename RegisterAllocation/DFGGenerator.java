package RegisterAllocation;

import cs132.util.SourcePos;
import cs132.vapor.ast.*;

import java.util.*;

public class DFGGenerator<MyPara, Sets, Throwable extends java.lang.Throwable> extends VInstr.VisitorPR<MyPara, Sets, Throwable> {

	public String ident;
	DFGraph DFG = new DFGraph();
	DFGraph functionGraph = new DFGraph();
	boolean initial = true;
	SourcePos lastpos;
	IdentityHashMap<String, String> inheritTable = new IdentityHashMap<>();  //<sourcePos.toString prenode, label or null(no goto)>
	public HashMap<String, SourcePos> labelTable = new HashMap<>();  //<label, sourcePos>

	public HashSet<String> getStrVariable(String s) {
		boolean f = false;
		String var = "";
		HashSet<String> varTable = new HashSet<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!f) {
				if (!(c == ' ' || c == '[' || c == ']' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9')) {
					var = var + c;
					f = true;
				}
			} else {
				if (!(c == '[' || c == ']' || c == ' '))
					var = var + c;
				else {
					varTable.add(var);
					var = "";
					f = false;
				}
			}

		}
		if (f) {
			varTable.add(var);
		}
		return varTable;
	}


	public boolean isInt(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	public void nodeSetupNoGoto(VInstr thisInstr) {
		if (initial) {
			DFG.initial(thisInstr.sourcePos);
			lastpos = thisInstr.sourcePos;
			inheritTable.put(lastpos.toString(), null);
			initial = false;
		} else {
			Node thisnode = new Node(thisInstr.sourcePos);
			DFG.addNode(thisnode);
			Node prenode = null;
			Vector<IdentityHashMap.Entry<String, String>> entryset = new Vector<>();
			for (IdentityHashMap.Entry<String, String> entry : inheritTable.entrySet()) {
				if (entry.getValue() == null) {
					//System.out.println("try to get "+entry.getKey());
					prenode = DFG.getNode(entry.getKey());
					entryset.add(entry);
				}
			}
			for (IdentityHashMap.Entry<String, String> entry : entryset) {
				inheritTable.remove(entry.getKey(), entry.getValue());
			}
			if (prenode == null) {
				lastpos = thisInstr.sourcePos;
				inheritTable.put(lastpos.toString(), null);
				return;
			}
			prenode.addSucc(thisnode);
			thisnode.addPre(prenode);
			lastpos = thisInstr.sourcePos;
			inheritTable.put(lastpos.toString(), null);
		}
	}

	public void nodeSetupBranch(VBranch thisInstr) {
		if (initial) {
			DFG.initial(thisInstr.sourcePos);
			lastpos = thisInstr.sourcePos;
			inheritTable.put(lastpos.toString(), null);
			inheritTable.put(lastpos.toString(), thisInstr.target.ident);
			initial = false;
		} else {
			Node thisnode = new Node(thisInstr.sourcePos);
			DFG.addNode(thisnode);
			Node prenode = null;
			Vector<IdentityHashMap.Entry<String, String>> entryset = new Vector<>();
			for (IdentityHashMap.Entry<String, String> entry : inheritTable.entrySet()) {
				if (entry.getValue() == null) {
					//System.out.println("try to get "+entry.getKey());
					prenode = DFG.getNode(entry.getKey());
					entryset.add(entry);
				}
			}
			for (IdentityHashMap.Entry<String, String> entry : entryset) {
				inheritTable.remove(entry.getKey(), entry.getValue());
			}
			if (prenode == null) {
				lastpos = thisInstr.sourcePos;
				inheritTable.put(lastpos.toString(), null);
				inheritTable.put(lastpos.toString(), thisInstr.target.ident);
				return;
			}
			prenode.addSucc(thisnode);
			thisnode.addPre(prenode);
			lastpos = thisInstr.sourcePos;
			inheritTable.put(lastpos.toString(), null);
			inheritTable.put(lastpos.toString(), thisInstr.target.ident);
		}
	}

	public void nodeSetupGoto(VGoto thisInstr) {

		if (initial) {
			DFG.initial(thisInstr.sourcePos);
			lastpos = thisInstr.sourcePos;
			inheritTable.put(lastpos.toString(), thisInstr.target.toString());
			initial = false;
		} else {

			Node thisnode = new Node(thisInstr.sourcePos);
			DFG.addNode(thisnode);
			Node prenode = null;
			Vector<IdentityHashMap.Entry<String, String>> entryset = new Vector<>();
			for (IdentityHashMap.Entry<String, String> entry : inheritTable.entrySet()) {
				if (entry.getValue() == null) {
					//System.out.println("try to get "+entry.getKey());
					prenode = DFG.getNode(entry.getKey());
					entryset.add(entry);
				}
			}
			for (IdentityHashMap.Entry<String, String> entry : entryset) {
				inheritTable.remove(entry.getKey(), entry.getValue());
			}
			if (prenode == null) {
				lastpos = thisInstr.sourcePos;
				inheritTable.put(lastpos.toString(), thisInstr.target.toString());
				return;
			}
			prenode.addSucc(thisnode);
			thisnode.addPre(prenode);
			lastpos = thisInstr.sourcePos;
			inheritTable.put(lastpos.toString(), thisInstr.target.toString());
		}

	}

	public boolean isMessage(String str) {
		boolean is = false;
		for (int i = 0; i < str.length(); i++) {
			if (i == 0 && str.charAt(i) == '"')
				is = true;
			if (i == str.length() - 1 && str.charAt(i) == '"')
				return true;
		}
		return false;
	}


	public Sets visit(MyPara var1, VAssign var2) throws Throwable {
		nodeSetupNoGoto(var2);
		Node thisnode = DFG.getNode(var2.sourcePos.toString());
		if (var2.dest != null) {
			thisnode.sets.addDef(var2.dest.toString());
		}
		if (!isInt(var2.source.toString()) && !isMessage(var2.source.toString()) && var2.source.toString().charAt(0) != ':')
			thisnode.sets.addUse(var2.source.toString());
		return null;
	}

	public Sets visit(MyPara var1, VCall var2) throws Throwable {
		nodeSetupNoGoto(var2);
		Node thisnode = DFG.getNode(var2.sourcePos.toString());
		if (var2.dest != null) {
			thisnode.sets.addDef(var2.dest.toString());
		}
		for (VOperand op : var2.args) {
			if (!isInt(op.toString()) && !isMessage(op.toString()) && op.toString().charAt(0) != ':')
				thisnode.sets.addUse(op.toString());
		}
		thisnode.sets.addUse(var2.addr.toString());
		return null;
	}

	public Sets visit(MyPara var1, VBuiltIn var2) throws Throwable {
		nodeSetupNoGoto(var2);
		Node thisnode = DFG.getNode(var2.sourcePos.toString());
		if (var2.dest != null) {
			thisnode.sets.addDef(var2.dest.toString());
		}

		for (VOperand op : var2.args) {
			if (!isInt(op.toString()) && !isMessage(op.toString()) && op.toString().charAt(0) != ':')
				thisnode.sets.addUse(op.toString());
		}
		return null;
	}

	public Sets visit(MyPara var1, VMemWrite var2) throws Throwable {
		nodeSetupNoGoto(var2);
		Node thisnode = DFG.getNode(var2.sourcePos.toString());
		if (!isInt(var2.source.toString()) && !isMessage(var2.source.toString()) && var2.source.toString().charAt(0) != ':')
			thisnode.sets.addUse(var2.source.toString());
		if (var2.dest != null) {
			thisnode.sets.addUse(((VMemRef.Global) var2.dest).base.toString());
		}
		return null;
	}

	public Sets visit(MyPara var1, VMemRead var2) throws Throwable {
		nodeSetupNoGoto(var2);
		Node thisnode = DFG.getNode(var2.sourcePos.toString());
		for (String var : getStrVariable(((VMemRef.Global) var2.source).base.toString())) {
			thisnode.sets.addUse(var);
		}
		if (var2.dest != null) {
			thisnode.sets.addDef(var2.dest.toString());
		}
		return null;
	}

	public Sets visit(MyPara var1, VBranch var2) throws Throwable {
		nodeSetupBranch(var2);
		Node thisnode = DFG.getNode(var2.sourcePos.toString());
		if (!isInt(var2.value.toString()) && !isMessage(var2.value.toString()) && var2.value.toString().charAt(0) != ':')
			thisnode.sets.addUse(var2.value.toString());
		return null;
	}

	public Sets visit(MyPara var1, VGoto var2) throws Throwable {
		nodeSetupGoto(var2);
		return null;
	}

	public Sets visit(MyPara var1, VReturn var2) throws Throwable {
		nodeSetupNoGoto(var2);
		Node thisnode = DFG.getNode(var2.sourcePos.toString());
		if (var2.value == null) return null;
		if (!isInt(var2.value.toString()) && !isMessage(var2.value.toString()) && var2.value.toString().charAt(0) != ':')
			thisnode.sets.addUse(var2.value.toString());
		return null;
	}
}
