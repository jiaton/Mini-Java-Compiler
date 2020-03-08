package RegisterAllocation;

import cs132.util.ProblemException;
import cs132.util.SourcePos;
import cs132.vapor.ast.*;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;


import java.io.*;
import java.util.*;

import static java.lang.System.exit;


public class V2VM {
    private Printer printer = new Printer();
    public static VaporProgram tree;
    //public static HashMap<String, HashSet<String>> calleeMap = new HashMap<>();
    //public static HashMap<String, HashSet<String>> callerMap = new HashMap<>();
    public static HashMap<String, Integer> outValue = new HashMap<>();
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

    public static int getInSize(String ident){
        int paras = 0;
        for(VFunction function : tree.functions){
            if(function.ident.equals(ident)){
                paras=function.params.length;
            }
        }

        return paras;
    }

//    public static int getOutSize(String ident){
//        int paras = 0;
//        for(String fstr : callerMap.get(ident)){
//            for(VFunction function : tree.functions) {
//                if (function.ident.equals(fstr)) {
//                    if(paras<function.params.length)
//                        paras = function.params.length;
//                }
//            }
//        }
//
//        return paras;
//    }

    public static int getOutSize(String ident){
        int paras = 0;
//        HashSet<String> s;
//        s=callerMap.get(ident);
//        if(s == null){
//            return 0;
//        }
//        for(String fstr : s){
//                for(VFunction function : tree.functions) {
//                    //System.out.println(function.ident+"    "+fstr);
//                    if (function.ident.equals(fstr)) {
//
//                        if(paras<function.params.length-3)
//                            paras = function.params.length-3;
//                    }
//                }
//        }
//        if(paras < 0)   paras = 0;
        paras = outValue.get(ident);

        return paras;
    }

    public static boolean updateSets(Node node) {
        boolean changed = false;
        HashSet<String> temp = new HashSet<>();
        for (String str : node.sets.useSet) {
            if (!node.sets.inSet.contains(str)) {
                node.sets.inSet.add(str);
                changed = true;
            }

        }
        for (String str : node.sets.outSet) {
//            System.out.println("here for "+node.sourcePos.toString());
//            System.out.println("out for "+node.sourcePos.toString());
            //node.sets.printOut();
            if (!temp.contains(str)) {
                temp.add(str);
                //System.out.println("here + "+str);
            }
        }
        for (String str : node.sets.defSet) {
            if (temp.contains(str))
                temp.remove(str);
        }
        for (String str : temp) {
            //System.out.println("In for "+node.sourcePos.toString()+": ");
            if (!node.sets.inSet.contains(str)) {
                node.sets.inSet.add(str);
                changed = true;
            }
        }
        for (Map.Entry<String, Node> entry : node.relatednodes.succNodes.entrySet()) {
            for (String str : entry.getValue().sets.inSet) {
                if (!node.sets.outSet.contains(str)) {
                    node.sets.addOut(str);
                    //System.out.println("out for "+node.sourcePos.toString()+"(+"+str);
                    //node.sets.printOut();
                    changed = true;
                }
            }
        }
        return changed;
    }

    public static void main(String[] args) throws Exception {



        /*Create graph and sets*/

        PrintStream err = new PrintStream(System.out);
        //VaporProgram tree = parseVapor(System.in,err);
        FileInputStream file = new FileInputStream("test.vapor");
        tree = parseVapor(file, err);
        HashMap<String, DFGGenerator> graphTable = new HashMap<>();

        HashSet<CallVisitor> callVisitors = new HashSet<>();

        /*funciton map*/
        for(VFunction function : tree.functions){
            CallVisitor visitor = new CallVisitor();
            visitor.ident=function.ident;
            callVisitors.add(visitor);
            for (VInstr Instr : function.body){
                Instr.accept(null,visitor);
            }
            outValue.put(function.ident,visitor.argms);
        }
//        for(CallVisitor v : callVisitors){
//            for(Map.Entry<String, HashSet<String>> entry : (Set<Map.Entry<String, HashSet<String>>>)v.calleeMap.entrySet()){
//                if(calleeMap.containsKey(entry.getKey())){
//                    for(String s : entry.getValue()){
//                        if(!calleeMap.get(entry.getKey()).contains(s)){
//                            calleeMap.get(entry.getKey()).add(s);
//                            //System.out.println(entry.getKey()+"=========="+s);
//                        }
//                    }
//                } else {
//                    calleeMap.put(entry.getKey(), new HashSet<>());
//                    for (String s : entry.getValue()) {
//                        if (!calleeMap.get(entry.getKey()).contains(s)) {
//                            calleeMap.get(entry.getKey()).add(s);
//                            //System.out.println(entry.getKey()+"=========="+s);
//                        }
//                    }
//                }
//            }
//        }
//        for(CallVisitor v : callVisitors){
//            for(Map.Entry<String, HashSet<String>> entry : (Set<Map.Entry<String, HashSet<String>>>)v.callerMap.entrySet()){
//                if(callerMap.containsKey(entry.getKey())){
//                    for(String s : entry.getValue()){
//                        if(!callerMap.get(entry.getKey()).contains(s)){
//                            callerMap.get(entry.getKey()).add(s);
//                            //System.out.println(entry.getKey()+"=========="+s);
//                        }
//                    }
//                } else {
//                    callerMap.put(entry.getKey(), new HashSet<>());
//                    for (String s : entry.getValue()) {
//                        if (!callerMap.get(entry.getKey()).contains(s)) {
//                            callerMap.get(entry.getKey()).add(s);
//                            //System.out.println(entry.getKey()+"=========="+s);
//                        }
//                    }
//                }
//            }
//        }
        Printer printer = new Printer();
        for (VDataSegment dataSegment : tree.dataSegments) {
            printer.println("const " + dataSegment.ident);
            printer.addIndentation();
            VOperand.Static[] labels = dataSegment.values;
            for (VOperand.Static label : labels) {
                printer.println(":" + ((VLabelRef) label).ident);
            }
            printer.resetIndentation();
        }
        //Generation DFG
        for (VFunction function : tree.functions) {
            DFGGenerator graph = new DFGGenerator();
            graph.ident = function.ident;
            graphTable.put(function.ident, graph);
            for (VCodeLabel label : function.labels) {
                graph.labelTable.put(label.ident, label.sourcePos);
            }
            for (VInstr Instr : function.body) {
                Instr.accept(null, graph);
            }
            for (Map.Entry<String, SourcePos> entry : (Set<Map.Entry<String, SourcePos>>) graph.labelTable.entrySet()) {
                for (VInstr Instr : function.body) {
                    if (Instr.sourcePos.line >= entry.getValue().line) {
                        Node thisnode = graph.DFG.getNode(Instr.sourcePos.toString());
                        for (Map.Entry<String, String> entry2 : (Set<Map.Entry<String, String>>) graph.inheritTable.entrySet()) {
//                            if(entry2.getValue()!=null){
//                                System.out.println(entry2.getKey()+":"+entry2.getValue()+" "+entry.getKey());
//                            }
                            if (entry2.getValue() != null && entry2.getValue().equals(":" + entry.getKey())) {
                                //System.out.println("add "+entry2.getKey()+"->"+thisnode.sourcePos.toString());
                                Node succnode = graph.DFG.getNode(entry2.getKey());
                                thisnode.addPre(succnode);
                                succnode.addSucc(thisnode);
                            }
                        }
                        break;
                    }
                }
            }
            boolean changed = true;
            while (changed) {
                changed = false;
                for (Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()) {
                    boolean c = false;
                    c = updateSets(entry.getValue());
                    if (c)
                        changed = true;
                    entry.getValue().sets.setActive();
                }
                //System.out.println(changed);
            }
//            for(Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()){
//                System.out.println("Active for "+entry.getValue().sourcePos.toString());
//                entry.getValue().sets.printActive();
//                System.out.println("------------------------");
//            }
//
//            for(Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()){
//                Node thisnode = entry.getValue();
//                System.out.println("In set for "+thisnode.sourcePos.toString());
//                for(String str : thisnode.sets.inSet){
//                    System.out.println(str);
//                }
//                System.out.println("---------------");
//            }
//            for(Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()){
//                Node thisnode = entry.getValue();
//                System.out.println("Out set for "+thisnode.sourcePos.toString());
//                for(String str : thisnode.sets.outSet){
//                    System.out.println(str);
//                }
//                System.out.println("---------------");
//            }
//            for(Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()){
//                Node thisnode = entry.getValue();
//                System.out.println("def set for "+thisnode.sourcePos.toString());
//                for(String str : thisnode.sets.defSet){
//                    System.out.println(str);
//                }
//                System.out.println("---------------");
//            }
//            for(Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()){
//                Node thisnode = entry.getValue();
//                System.out.println("use set for "+thisnode.sourcePos.toString());
//                for(String str : thisnode.sets.useSet){
//                    System.out.println(str);
//                }
//                System.out.println("---------------");
//            }

            /*generate intervals*/
            LinkedHashMap<String, HashSet<Interval>> intervalMap = new LinkedHashMap<>();
            HashMap<String, SourcePos[]> start = new HashMap<>();
            TreeSet<Interval.CandidateInterval> candidateIntervals = new TreeSet<>();

            /*set up params*/
            LinkedHashMap<String, String> paramAllocation = new LinkedHashMap<>();
            for (int i = 0; i < function.params.length; i++) {
                paramAllocation.put(function.params[i].toString(), "$a" + i);
                if (i >= 4) {
                    paramAllocation.put(function.params[i].toString(), "in[" + i + "]");
                }
            }

            for (Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()) {
                Node thisnode = entry.getValue();
                for (String str : thisnode.sets.active) {
                    if (!start.containsKey(str)) {
                        SourcePos[] sp = {thisnode.sourcePos, thisnode.sourcePos};
                        start.put(str, sp);
                    } else {
                        start.get(str)[1] = thisnode.sourcePos;
                    }
                }
                ArrayList<String> removeList = new ArrayList<>();
                for (Map.Entry<String, SourcePos[]> e : start.entrySet()) {
                    if (!thisnode.sets.active.contains(e.getKey()) || thisnode.isSuccEmpty()) {
                        /*do not give parameters interval, e.getKey() returns the varName*/
                        if (!paramAllocation.keySet().contains(e.getKey())) {
                            Interval interval = new Interval.CandidateInterval(e.getValue()[0], e.getValue()[1], e.getKey());
                            candidateIntervals.add((Interval.CandidateInterval) interval);
                            HashSet<Interval> previousIntervals = intervalMap.get(e.getKey());
                            /*set up interval Map (varName -> Interval[]: arrayList))*/
                            if (previousIntervals == null) {
                                intervalMap.put(e.getKey(), new HashSet<>(Collections.singletonList(interval)));
                            } else {
                                previousIntervals.add(interval);
//                            intervalMap.put(e.getKey(), previousIntervals);
                            }
                            removeList.add(e.getKey());
                        }
                    }
                }
                for (String key : removeList) {
                    start.remove(key);
                }
            }

            LinearScanRegisterAllocation.AllocationRecord allocationRecord = new LinearScanRegisterAllocation(candidateIntervals).allocate();
            /*print function name*/

//            printer.println("func " + function.ident + "[in "+getInSize(function.ident)+", out "+getOutSize(function.ident)+", local "+allocationRecord.memoryAllocation.size()+17+"]"); // TODO: 3/6/2020 in out local
            printer.println("func " + function.ident + "[in " + getInSize(function.ident) + ", out " + getOutSize(function.ident) + ", local " + 17 + "]"); // TODO: 3/8/2020 change the local later

            printer.addIndentation();
            /*print labels and instructions*/
            int prevLine = function.sourcePos.line;
            int labelIndex = 0;
            VCodeLabel[] labels = function.labels;
            for (VInstr instr : function.body) {
                MyPara myPara = new MyPara(graph.DFG, intervalMap, allocationRecord.registerAllocation, allocationRecord.memoryAllocation, paramAllocation);
                while (instr.sourcePos.line != prevLine + 1) {
                    Printer.resetIndentation();
                    printer.addIndentation();
                    printer.println(labels[labelIndex++].ident + ":");
                    prevLine++;
                }
                instr.accept(myPara, new PrintVisitor());
                prevLine++;
            }
            Printer.resetIndentation();
        }


    }
}
