package RegisterAllocation;

import cs132.util.SourcePos;
import cs132.vapor.ast.VInstr;

public class Interval {
	public SourcePos start = null;
	public SourcePos end = null;
	public String varName = null;

	public static class ActiveInterval extends Interval implements Comparable<Interval> {
		public ActiveInterval(SourcePos start, SourcePos end) {
			super(start, end);
		}

		public ActiveInterval(SourcePos start, SourcePos end, String varName) {
			super(start, end, varName);
		}

		@Override
		public int compareTo(Interval o) {
			return this.end.line - o.end.line;
		}
	}

	public static class CandidateInterval extends Interval implements Comparable<Interval> {

		public CandidateInterval(SourcePos start, SourcePos end) {
			super(start, end);
		}

		public CandidateInterval(SourcePos start, SourcePos end, String varName) {
			super(start, end, varName);
		}

		@Override
		public int compareTo(Interval o) {
			return this.start.line - o.start.line;
		}
	}

	public Interval(SourcePos start, SourcePos end) {
		this.start = start;
		this.end = end;
	}

	public Interval(SourcePos start, SourcePos end, String varName) {
		this.start = start;
		this.end = end;
		this.varName = varName;
	}

	public int getStart() {
		return start.line;
	}

	public void setStart(SourcePos start) {
		this.start = start;
	}

	public int getEnd() {
		return end.line;
    }

	public void setEnd(SourcePos end) {
		this.end = end;
	}
}
