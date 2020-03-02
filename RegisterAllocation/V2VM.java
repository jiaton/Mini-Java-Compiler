package RegisterAllocation;
import cs132.util.ProblemException;
import cs132.util.SourcePos;
import cs132.vapor.ast.*;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;

import java.io.*;
import java.util.*;

import RegisterAllocation.*;


public class V2VM {
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
    public static boolean updateSets(Node node){
        boolean changed = false;
        HashSet<String> temp = new HashSet<>();
        for(String str : node.sets.useSet){
            if(!node.sets.inSet.contains(str)){
                node.sets.inSet.add(str);
                changed=true;
            }

        }
        for(String str : node.sets.outSet){
            if(!temp.contains(str))
                temp.add(str);
        }
        for(String str : node.sets.defSet){
            if(temp.contains(str))
                temp.remove(str);
        }
        for(String str : temp){
            if(!node.sets.inSet.contains(str)){
                node.sets.inSet.add(str);
                changed = true;
            }
        }
        for(Map.Entry<String,Node> entry : node.relatednodes.succNodes.entrySet()){
            for(String str : entry.getValue().sets.inSet){
                if(!node.sets.outSet.contains(str)){
                    node.sets.outSet.add(str);
                    changed = true;
                }
            }
        }
        return changed;
    }

    public static void main(String[] args) throws IOException {

        /*Create graph and sets*/

        PrintStream err = new PrintStream(System.out);
        //VaporProgram tree = parseVapor(System.in,err);
        FileInputStream file = new FileInputStream("test.vapor");
        VaporProgram tree = parseVapor(file,err);
        HashMap<String,DFGGenerator> graphTable=new HashMap<>();

        //Generation DFG
        for(VFunction function : tree.functions){
            DFGGenerator graph=new DFGGenerator();
            graphTable.put(function.ident,graph);
            for(VCodeLabel label : function.labels){
                graph.labelTable.put(label.ident,label.sourcePos);
            }
            for(VInstr Instr : function.body){
                Instr.accept(null,graph);
            }
            for(Map.Entry<String,SourcePos> entry : (Set<Map.Entry<String,SourcePos>>)graph.labelTable.entrySet()){
                for(VInstr Instr : function.body){
                    if(Instr.sourcePos.line>=entry.getValue().line){
                        Node thisnode = graph.DFG.getNode(Instr.sourcePos.toString());
                        for(Map.Entry<String,String>entry2 : (Set<Map.Entry<String,String>>)graph.inheritTable.entrySet()){
//                            if(entry2.getValue()!=null){
//                                System.out.println(entry2.getKey()+":"+entry2.getValue()+" "+entry.getKey());
//                            }
                            if(entry2.getValue()!=null&&entry2.getValue().equals(":"+entry.getKey())){
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
            while(changed){
                changed = false;
                for(Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()){
                    changed = updateSets(entry.getValue());
                    entry.getValue().sets.setActive();
                }
            }
            for(Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()){
                System.out.println("Active for "+entry.getValue().sourcePos.toString());
                entry.getValue().sets.printActive();
                System.out.println("------------------------");
            }

            /*generate intervals*/
            HashMap<String, SourcePos[]> start = new HashMap<>();
            TreeSet<Interval.CandidateInterval> candidateIntervals = new TreeSet<>();
            for(Map.Entry<String, Node> entry : graph.DFG.nodes.entrySet()){
                Node thisnode = entry.getValue();
                for(String str : thisnode.sets.active){
                    if(!start.containsKey(str)){
                        SourcePos[] sp = {thisnode.sourcePos,thisnode.sourcePos};
                        start.put(str, sp);
                    }
                    else{
                        start.get(str)[1] = thisnode.sourcePos;
                    }
                }
                for (Map.Entry<String, SourcePos[]> e : start.entrySet()) {
                    if (!thisnode.sets.active.contains(e.getKey())) {
                        Interval interval = new Interval.CandidateInterval(e.getValue()[0], e.getValue()[1]);
                        candidateIntervals.add((Interval.CandidateInterval) interval);
                    }
                }
            }

            HashMap<Interval, Register> regAllocationMap = new LinearScanRegisterAllocation(candidateIntervals).allocate();
            for (Map.Entry<Interval, Register> entry : regAllocationMap.entrySet()) {
                System.out.println(entry.getKey().toString() + " " + entry.getValue().toString());
            }
        }




    }


}
