package RegisterAllocation;

import cs132.util.SourcePos;
import cs132.vapor.ast.VInstr;

import java.util.HashMap;

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

		public ActiveInterval(Interval interval) {
			super(interval.start, interval.end, interval.varName);
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

		public CandidateInterval(Interval interval) {
			super(interval.start, interval.end, interval.varName);
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Interval) {
			Interval interval = (Interval) obj;
			return (this.varName == null & interval.varName == null || this.varName.equals(interval.varName)) && this.start.line == interval.start.line && this.start.column == interval.start.column
					&& this.end.line == interval.end.line && this.end.column == interval.end.column;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return start.line * 12 + end.line * 45 + (varName != null ? varName.hashCode() : 0);
	}
}
