package RegisterAllocation;

import java.util.LinkedHashMap;

public class MyPara {
    DFGraph dfGraph;
    LinkedHashMap<String, Interval> intervalMap; //varName to Interval
    LinkedHashMap<Interval, Register> registerAllocation;
    LinkedHashMap<Interval, String> memoryAllocation;

    public MyPara(LinkedHashMap<String, Interval> intervalMap, LinkedHashMap<Interval, Register> registerAllocation, LinkedHashMap<Interval, String> memoryAllocation) {
        this.intervalMap = intervalMap;
        this.registerAllocation = registerAllocation;
        this.memoryAllocation = memoryAllocation;
    }
}
