package RegisterAllocation;

import cs132.vapor.ast.VInstr;

public class Interval {
    public VInstr start = null;
    public VInstr end = null;

    public Interval(VInstr start, VInstr end) {
        this.start = start;
        this.end = end;
    }

    public VInstr getStart() {
        return start;
    }

    public void setStart(VInstr start) {
        this.start = start;
    }

    public VInstr getEnd() {
        return end;
    }

    public void setEnd(VInstr end) {
        this.end = end;
    }
}
