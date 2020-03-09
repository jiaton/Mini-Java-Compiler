package RegisterAllocation;

import cs132.util.SourcePos;
import cs132.vapor.ast.*;

import java.util.*;

public class CallVisitor <MyPara,Sets,Throwable extends java.lang.Throwable> extends VInstr.VisitorPR<MyPara, Sets,Throwable> {
//    public HashMap<String, HashSet<String>> calleeMap = new HashMap<>();
//    public HashMap<String,HashSet<String>> callerMap = new HashMap();
    public String ident;
//    public HashMap<String, Integer> valueMap = new HashMap<>();
//    public void setValue(String name, int value){
//        if(valueMap.containsKey(name)){
//            valueMap.remove(name);
//            valueMap.put(name,value);
//        }else{
//            valueMap.put(name,value);
//        }
//    }
    public int argms = 0;
    public Sets visit(MyPara var1, VAssign var2) throws Throwable{
//        setValue(var1.toString(),valueMap.get(var2.source.toString()));
        return null;
    }

    public Sets visit(MyPara var1, VCall var2) throws Throwable{
        //set function graph
//        String dest;
//        String source;
//        dest = ident;
//        source = var2.addr.toString();
//        if(valueMap.containsKey(ident)){
//            dest = valueMap.get(ident);
//        }
//        if(valueMap.containsKey(var2.addr.toString())){
//            source = valueMap.get(var2.addr.toString());
//        }
//        if(calleeMap.containsKey(source)){
//            calleeMap.get(source).add(":"+dest);
//            //System.out.println(var2.addr.toString()+"  callee   "+":"+ident);
//        }else{
//            calleeMap.put(source,new HashSet<>());
//            calleeMap.get(source).add(":"+source);
//            //System.out.println(var2.addr.toString()+"  callee   "+":"+ident);
//        }
//        if(callerMap.containsKey(":"+source)){
//            callerMap.get(":"+source).add(dest);
//            System.out.println(":"+source+"   caller  "+dest);
//        }else{
//            callerMap.put(":"+source,new HashSet<>());
//            callerMap.get(":"+source).add(dest);
//            System.out.println(":"+source+"   caller  "+dest);
//        }
        if (var2.args.length > argms) {
            argms = var2.args.length;
        }
        return null;
    }

    public Sets visit(MyPara var1, VBuiltIn var2) throws Throwable{
//        if(var2.op.name.equals("Add")){
//            int v = valueMap.get(var2.args[0].toString());
//            v += Integer.parseInt(var2.args[1].toString());
//            setValue(var1.toString(),v);
//        }else if(var2.op.name.equals("And")){
//            int v = 1;
//            if(valueMap.get(var2.args[0].toString())==0||Integer.parseInt(var2.args[1].toString())==0)
//                v = 0;
//            setValue(var1.toString(),v);
//        }else if(var2.op.name.equals("Sub")){
//            int v = valueMap.get(var2.args[0].toString());
//            v -= Integer.parseInt(var2.args[1].toString());
//            setValue(var1.toString(),v);
//        }else if(var2.op.name.equals("MulS")){
//            int v = valueMap.get(var2.args[0].toString())*Integer.parseInt(var2.args[1].toString());
//            setValue(var1.toString(),v);
//        }else if(var2.op.name.equals("DivS")){
//            int v = valueMap.get(var2.args[0].toString())/Integer.parseInt(var2.args[1].toString());
//            setValue(var1.toString(),v);
//        }else if(var2.op.name.equals("Eq")){
//            int v = valueMap.get(var2.args[0].toString())==Integer.parseInt(var2.args[1].toString())? 1 : 0;
//            setValue(var1.toString(),v);
//        }else if(var2.op.name.equals("Lt")){
//            int v = valueMap.get(var2.args[0].toString())<Integer.parseInt(var2.args[1].toString())? 1 : 0;
//            setValue(var1.toString(),v);
//        } else if(var2.op.name.equals("LtS")){
//            int v = valueMap.get(var2.args[0].toString())<Integer.parseInt(var2.args[1].toString())? 1 : 0;
//            setValue(var1.toString(),v);
//        }

        return null;
    }

    public Sets visit(MyPara var1, VMemWrite var2) throws Throwable{

        return null;
    }

    public Sets visit(MyPara var1, VMemRead var2) throws Throwable{
//        int v = Integer.parseInt(var2.source.toString());
//        setValue(var1.toString(),v);
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
