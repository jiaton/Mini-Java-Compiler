package RegisterAllocation;

import cs132.vapor.ast.VInstr;

public class Interval {
    public VInstr start = null;
    public VInstr end = null;

    public static class ActiveInterval extends Interval implements Comparable<Interval> {
        public ActiveInterval(VInstr start, VInstr end) {
            super(start, end);
        }

        @Override
        public int compareTo(Interval o) {
            return this.end.sourcePos.line - o.end.sourcePos.line;
        }
    }

    public static class CandidateInterval extends Interval implements Comparable<Interval> {

        public CandidateInterval(VInstr start, VInstr end) {
            super(start, end);
        }

        @Override
        public int compareTo(Interval o) {
            return this.start.sourcePos.line - o.start.sourcePos.line;
        }
    }

    public Interval(VInstr start, VInstr end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start.sourcePos.line;
    }

    public void setStart(VInstr start) {
        this.start = start;
    }

    public int getEnd() {
        return end.sourcePos.line;
    }

    public void setEnd(VInstr end) {
        this.end = end;
    }
}
