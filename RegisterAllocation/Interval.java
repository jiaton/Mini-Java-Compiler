package RegisterAllocation;


import cs132.util.SourcePos;




public class Interval {
	public SourcePos start = null;
	public SourcePos end = null;
	public String varName = null;
	public boolean hasPos = true; //temporal var has a position

	public Interval(String varName) {
		this.hasPos = false;
		this.varName = varName; //number or start with ":"
	}

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
			if (this.end.line != o.end.line) {
				return this.end.line - o.end.line;
			} else if (this.end.column != o.end.column) {
				return this.end.column - o.end.column;
			} else {
				return this.varName.compareTo(o.varName);
			}
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
			if (this.start.line != o.start.line) {
				return this.start.line - o.start.line;
			} else if (this.start.column != o.start.column) {
				return this.start.column - o.start.column;
			} else {
				return this.varName.compareTo(o.varName);
			}
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
			return (this.varName == null || interval.varName == null || this.varName.equals(interval.varName)) && this.start.line == interval.start.line && this.start.column == interval.start.column
					&& this.end.line == interval.end.line && this.end.column == interval.end.column;
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hasPos) {
			return start.line * 12 + end.line * 45 + (varName != null ? varName.hashCode() : 0);
		} else {
			return varName.hashCode();
		}
	}
}
