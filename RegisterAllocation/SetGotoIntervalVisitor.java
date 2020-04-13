package RegisterAllocation;

import cs132.util.SourcePos;
import cs132.vapor.ast.*;

import java.util.*;

public class SetGotoIntervalVisitor<MyPara, Sets, Throwable extends java.lang.Throwable> extends VInstr.VisitorPR<MyPara, Sets, Throwable> {
    //    public HashMap<String, HashSet<String>> calleeMap = new HashMap<>();
//    public HashMap<String,HashSet<String>> callerMap = new HashMap();
    public String ident;
    LinkedHashMap<String, HashSet<Interval>> intervalMap;
    DFGraph DFG;
    //    public HashMap<String, Integer> valueMap = new HashMap<>();
//    public void setValue(String name, int value){
//        if(valueMap.containsKey(name)){
//            valueMap.remove(name);
//            valueMap.put(name,value);
//        }else{
//            valueMap.put(name,value);
//        }
//    }

    public Sets visit(MyPara var1, VAssign var2) throws Throwable {

        return null;
    }

    public Sets visit(MyPara var1, VCall var2) throws Throwable {

        return null;
    }

    public Sets visit(MyPara var1, VBuiltIn var2) throws Throwable {

        return null;
    }

    public Sets visit(MyPara var1, VMemWrite var2) throws Throwable {

        return null;
    }

    public Sets visit(MyPara var1, VMemRead var2) throws Throwable {

        return null;
    }

    public Sets visit(MyPara var1, VBranch var2) throws Throwable {
        Node thisnode = DFG.getNode(var2.sourcePos.toString());

        for (String var : thisnode.sets.active) {
            if (intervalMap != null) {
                HashSet<Interval> intervalset = intervalMap.get(var);
                if (intervalset != null) {
                    HashSet<Interval> temp = new HashSet<>();
                    for (Map.Entry<String, Node> succEntry : thisnode.relatednodes.succNodes.entrySet()) {
                        Node succ = succEntry.getValue();
                        if (succ.sets.active.contains(var)) {
                            for (Interval interval : intervalset) {
                                if (interval.start.line <= succ.sourcePos.line && interval.end.line >= succ.sourcePos.line) {
                                    temp.add(interval);
                                } else if (interval.start.line <= thisnode.sourcePos.line && interval.end.line >= thisnode.sourcePos.line) {
                                    temp.add(interval);
                                }
                            }
                        }
                    }
                    for (Interval i : temp) {
                        intervalset.remove(i);
                    }
                    SourcePos start = null;
                    SourcePos end = null;
                    for (Interval i : temp) {
                        start = i.start;
                        end = i.end;
                    }
                    if (start != null && end != null) {
                        for (Interval i : temp) {
                            if (i.start.line <= start.line) {
                                start = i.start;
                            }
                            if (i.end.line >= end.line) {
                                end = i.end;
                            }
                        }
                        Interval newinterval = new Interval(start, end, var);
                        intervalset.add(newinterval);
                        intervalMap.remove(var);
                        intervalMap.put(var, intervalset);
                    }
                }

            }


        }
        return null;
    }

    public Sets visit(MyPara var1, VGoto var2) throws Throwable {
        Node thisnode = DFG.getNode(var2.sourcePos.toString());

        for (String var : thisnode.sets.active) {
            if (intervalMap != null) {
                HashSet<Interval> intervalset = intervalMap.get(var);
                if (intervalset != null) {
                    HashSet<Interval> temp = new HashSet<>();
                    for (Map.Entry<String, Node> succEntry : thisnode.relatednodes.succNodes.entrySet()) {
                        Node succ = succEntry.getValue();
                        if (succ.sets.active.contains(var)) {
                            for (Interval interval : intervalset) {
                                if (interval.start.line <= succ.sourcePos.line && interval.end.line >= succ.sourcePos.line) {
                                    temp.add(interval);
                                } else if (interval.start.line <= thisnode.sourcePos.line && interval.end.line >= thisnode.sourcePos.line) {
                                    temp.add(interval);
                                }
                            }
                        }
                    }
                    for (Interval i : temp) {
                        intervalset.remove(i);
                    }
                    SourcePos start = null;
                    SourcePos end = null;
                    for (Interval i : temp) {
                        start = i.start;
                        end = i.end;
                    }
                    if (start != null && end != null) {
                        for (Interval i : temp) {
                            if (i.start.line <= start.line) {
                                start = i.start;
                            }
                            if (i.end.line >= end.line) {
                                end = i.end;
                            }
                        }
                        Interval newinterval = new Interval(start, end, var);
                        intervalset.add(newinterval);
                        intervalMap.remove(var);
                        intervalMap.put(var, intervalset);
                    }
                }

            }


        }
        return null;
    }

    public Sets visit(MyPara var1, VReturn var2) throws Throwable {

        return null;
    }
}
