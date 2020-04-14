

import cs132.util.SourcePos;
import cs132.vapor.ast.*;

import java.util.*;

import cs132.vapor.ast.*;

public class VM2MPrinter<MyPara, Sets, Throwable extends java.lang.Throwable> extends VInstr.VisitorPR<MyPara, Sets, Throwable> {
	LinkedHashMap<SourcePos, String> labelMap;
	static int strnum = 0;
	HashMap<Integer, String> strMap = new HashMap<>();
	Printer printer;

	public boolean IsDigit(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'))
				return false;
		}
		return true;
	}

	public void PrintLabel(SourcePos sourcePos) {
		HashSet<SourcePos> deleteMap = new HashSet<>();
		printer.removeIndentation();
		for (Map.Entry<SourcePos, String> entry : labelMap.entrySet()) {
			if (entry.getKey().line <= sourcePos.line) {
				printer.println(entry.getValue() + ":");
				deleteMap.add(entry.getKey());
			}
		}
		for (SourcePos sourcepos : deleteMap) {
			labelMap.remove(sourcepos);
		}
		printer.addIndentation();
	}

	public String NoColon(String str) {
		String r = "";
		if (str.charAt(0) == ':') {

			for (int i = 1; i < str.length(); i++) {
				r = r + str.charAt(i);
			}
			return r;
		}
		return str;
	}

	//        public String String4MWR(String str,int index){
//            String s = "";
//            String var = "";
//            String d = "";
//            for(int i=0;i<str.length();i++){
//                char c = str.charAt(i);
//                if(c == '-')    s = "-";
//                if(!(c ==' '||c=='['||c==']'||c=='+'||c=='-')) var=var+c;
////                if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9') d=d+c;
//            }
//            return index+"("+var+")";
//        }
//        public boolean IsLocal(String str){
//            String local = "local[";
//            if(str.length()<=7)return false;
//            for(int i=0;i<6;i++){
//                if(str.charAt(i)!=local.charAt(i))
//                    return false;
//            }
//            return true;
//        }
//        public boolean IsIn(String str){
//            String local = "in[";
//            if(str.length()<=4)return false;
//            for(int i=0;i<3;i++){
//               if(str.charAt(i)!=local.charAt(i))
//                   return false;
//            }
//            return true;
//        }
//        public boolean IsOut(String str){
//            String local = "out[";
//            if(str.length()<=5)return false;
//            for(int i=0;i<4;i++){
//                if(str.charAt(i)!=local.charAt(i))
//                    return false;
//            }
//            return true;
//         }
//        public boolean isMem(String str){
//            if(str.charAt(0)=='['&&str.charAt(str.length()-1)==']') return true;
//            else return false;
//        }
//        public int GetNum(String str){
//            String d="";
//            for(int i=0;i<str.length();i++){
//                char c = str.charAt(i);
//                if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9') d=d+c;
//            }
//            return Integer.parseInt(d);
//        }
	public String MemTransGlobel(String source, int index) {
		if (IsDigit(source)) {
			return source;
		} else {
			//return String4MWR(source,index);
			return index + "(" + source + ")";
		}
	}

	public String MemTransStack(String which, int index) {

		if (which.equals("Local")) return index * 4 + "($sp)";
		else if (which.equals("In")) return index * 4 + "($fp)";
		else if (which.equals("Out")) return index * 4 + "($sp)";
		return "Not Stack";

	}

	public boolean isData(String str) {
		if (str.charAt(0) == ':') return true;
		else return false;
	}

	public Sets visit(MyPara var1, VAssign var2) throws Throwable {
		PrintLabel(var2.sourcePos);
		if (!IsDigit(var2.source.toString()))
			printer.println("move " + var2.dest.toString() + " " + var2.source.toString());
		else
			printer.println("li " + var2.dest.toString() + " " + var2.source.toString());
		return null;
	}

	public boolean isReg(String str){
		if(str.charAt(0)=='$')	return true;
		else	return false;
	}
	public Sets visit(MyPara var1, VCall var2) throws Throwable {
		PrintLabel(var2.sourcePos);
		if(isReg(var2.addr.toString())){
			printer.println("jalr " + var2.addr.toString());
		}else{
			printer.println("jal " + NoColon(var2.addr.toString()));
		}

		return null;
	}

	public Sets visit(MyPara var1, VBuiltIn var2) throws Throwable {
		PrintLabel(var2.sourcePos);
		if (var2.op.name.equals("Add")) {
			String first,next;
			if(IsDigit(var2.args[0].toString())){
				next = var2.args[1].toString();
				first = "$t9";
				printer.println("li $t9 "+var2.args[0].toString());
			}else if(IsDigit(var2.args[1].toString())){
				next = "$t9";
				first = var2.args[0].toString();
				printer.println("li $t9 " + var2.args[1].toString());
			}else {
				next = var2.args[1].toString();
				first = var2.args[0].toString();
			}
			printer.println("addu " + var2.dest.toString() + " " + first + " " + next);
		} else if (var2.op.name.equals("And")) {

		} else if (var2.op.name.equals("Sub")) {
			if (var2.args[0].toString().matches("\\d+")) {
				printer.println("li $t9 " + var2.args[0].toString());
				printer.println("subu " + var2.dest.toString() + " " + "$t9" + " " + var2.args[1].toString());
			} else if (var2.args[1].toString().matches("\\d+")) {
				printer.println("li $t9 " + var2.args[1].toString());
				printer.println("subu " + var2.dest.toString() + " " + var2.args[0].toString() + " $t9");
			} else {
				printer.println("subu " + var2.dest.toString() + " " + var2.args[0].toString() + " " + var2.args[1].toString());
			}
		} else if (var2.op.name.equals("MulS")) {
			String first,next;
			if(IsDigit(var2.args[0].toString())&&IsDigit(var2.args[1].toString())){
				printer.println("li "+var2.dest.toString()+" "+Integer.parseInt(var2.args[0].toString())*Integer.parseInt(var2.args[1].toString()));
				return null;
			}
			else if(IsDigit(var2.args[0].toString())){
				next = var2.args[0].toString();
				first = var2.args[1].toString();
			}else if(IsDigit(var2.args[1].toString())){
				next = var2.args[1].toString();
				first = var2.args[0].toString();
			}
			else {
				next = var2.args[0].toString();
				first = var2.args[1].toString();
			}
			printer.println("mul " + var2.dest.toString() + " " + first + " " + next);
		} else if (var2.op.name.equals("DivS")) {

		} else if (var2.op.name.equals("Eq")) {

		} else if (var2.op.name.equals("Lt")) {
			String first,next;
			if(IsDigit(var2.args[0].toString())){
				next = var2.args[1].toString();
				first = "$t9";
				printer.println("li $t9 "+var2.args[0].toString());
			}else if(IsDigit(var2.args[1].toString())){
				next = "t9";
				first = var2.args[0].toString();
				printer.println("li $t9 "+var2.args[1].toString());
			}else {
				next = var2.args[1].toString();
				first = var2.args[0].toString();
			}
			printer.println("sltu " + var2.dest.toString() + " " + first + " " + next);
		} else if (var2.op.name.equals("LtS")) {
			if (var2.args[0].toString().matches("\\d+") || var2.args[1].toString().matches("\\d+")) {
				printer.println("slti " + var2.dest.toString() + " " + var2.args[0].toString() + " " + var2.args[1].toString());
			} else {
				printer.println("slt " + var2.dest.toString() + " " + var2.args[0].toString() + " " + var2.args[1].toString());
			}
		} else if (var2.op.name.equals("HeapAllocZ")) {
			if(IsDigit(var2.args[0].toString())){
				printer.println("li $a0 " + var2.args[0].toString());
				printer.println("jal _heapAlloc");
				printer.println("move " + var2.dest.toString() + " $v0");
			}else{
				printer.println("move $a0 " + var2.args[0].toString());
				printer.println("jal _heapAlloc");
				printer.println("move "+var2.dest.toString()+" $v0");
			}

		} else if (var2.op.name.equals("Error")) {
			printer.println("la $a0 _str" + strnum);
			strMap.put(strnum, var2.args[0].toString());
			strnum++;
			printer.println("j _error");
		} else if (var2.op.name.equals("PrintIntS")) {
			if (var2.args[0].toString().matches("^\\d+$")) {
				printer.println("li $a0 " + var2.args[0].toString());
			} else {
				printer.println("move $a0 " + var2.args[0].toString());
			}
			printer.println("jal _print");
		}

		return null;
	}

	public Sets visit(MyPara var1, VMemWrite var2) throws Throwable {
		PrintLabel(var2.sourcePos);
		//if(isData(var2.source.toString())){
		if (var2.dest instanceof VMemRef.Global) {
			String source = "";
			String dest = "";
			source = var2.source.toString();
			dest = MemTransGlobel(((VMemRef.Global) var2.dest).base.toString(), ((VMemRef.Global) var2.dest).byteOffset);
			if (source.matches("^:.*")) {
				printer.println("la $t9 " + NoColon(source));
				printer.println("sw $t9 " + NoColon(dest));
			} else if (source.equals("0")) {
				printer.println("sw " + "$" + source + " " + NoColon(dest));
			} else if (source.matches("\\d+")) {
				printer.println("li $t9 " + source);
				printer.println("sw " + "$t9" + " " + NoColon(dest));
			} else {
				printer.println("sw " + source + " " + NoColon(dest));
			}
		} else if (var2.dest instanceof VMemRef.Stack) {
			String source = "";
			String dest = "";
			source = var2.source.toString();
			dest = MemTransStack(((VMemRef.Stack) var2.dest).region.toString(), ((VMemRef.Stack) var2.dest).index);
			if (source.matches("^:.*")) {
				printer.println("la $t9 " + NoColon(source));
				printer.println("sw $s0 " + NoColon(dest));
			} else if (source.equals("0")) {
				printer.println("sw " + "$" + source + " " + NoColon(dest));
			} else if (source.matches("\\d+")) {
				printer.println("li $t9 " + source);
				printer.println("sw " + "$t9" + " " + NoColon(dest));
			} else {
				printer.println("sw " + source + " " + NoColon(dest));
			}
		}
//            }else{
//                if (var2.dest instanceof VMemRef.Global){
//                    String source = "";
//                    String dest = "";
//                    source = var2.source.toString();
//                    dest = MemTransGlobel(((VMemRef.Global) var2.dest).base.toString(),((VMemRef.Global) var2.dest).byteOffset);
//
//                    printer.println("sw "+NoColon(source)+" "+NoColon(dest));
//                }else if(var2.dest instanceof VMemRef.Stack){
//                    String source = "";
//                    String dest = "";
//                    source = var2.source.toString();
//                    dest = MemTransStack(((VMemRef.Stack) var2.dest).region.toString(),((VMemRef.Stack) var2.dest).index);
//                    printer.println("sw "+NoColon(source)+" "+NoColon(dest));
//                }
//            }


		return null;
	}

	public Sets visit(MyPara var1, VMemRead var2) throws Throwable {
		PrintLabel(var2.sourcePos);
		if (var2.source instanceof VMemRef.Global) {
			String source = MemTransGlobel(((VMemRef.Global) var2.source).base.toString(), ((VMemRef.Global) var2.source).byteOffset);
			String dest = var2.dest.toString();
			printer.println("lw " + dest + " " + source);
		} else if (var2.source instanceof VMemRef.Stack) {
			String source = MemTransStack(((VMemRef.Stack) var2.source).region.toString(), ((VMemRef.Stack) var2.source).index);
			String dest = var2.dest.toString();
			printer.println("lw " + dest + " " + source);
		}

		return null;
	}

	public Sets visit(MyPara var1, VBranch var2) throws Throwable {
		PrintLabel(var2.sourcePos);
		if (var2.positive) {
			printer.println("bnez " + var2.value.toString() + " " + var2.target.ident);
		} else {
			printer.println("beqz " + var2.value.toString() + " " + var2.target.ident);
		}
		return null;
	}

	public Sets visit(MyPara var1, VGoto var2) throws Throwable {
		PrintLabel(var2.sourcePos);
		printer.println("j " + NoColon(var2.target.toString()));
		return null;
	}

	public Sets visit(MyPara var1, VReturn var2) throws Throwable {
		PrintLabel(var2.sourcePos);

		return null;
	}
}
