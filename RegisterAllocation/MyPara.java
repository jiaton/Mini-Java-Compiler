package RegisterAllocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class MyPara {
    DFGraph dfGraph;
    LinkedHashMap<String, HashSet<Interval>> intervalMap; //varName to Interval
    LinkedHashMap<Interval, Register> registerAllocation;
    LinkedHashMap<Interval, String> memoryAllocation;

    public MyPara(DFGraph dfGraph, LinkedHashMap<String, HashSet<Interval>> intervalMap, LinkedHashMap<Interval, Register> registerAllocation, LinkedHashMap<Interval, String> memoryAllocation) {
        this.dfGraph = dfGraph;
        this.intervalMap = intervalMap;
        this.registerAllocation = registerAllocation;
        this.memoryAllocation = memoryAllocation;
    }
}
