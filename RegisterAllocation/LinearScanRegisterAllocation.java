package RegisterAllocation;

import java.util.*;


public class LinearScanRegisterAllocation {
    TreeSet<Interval.ActiveInterval> active = new TreeSet<>();        // in order of increasing end point
    TreeSet<Interval.CandidateInterval> candidateInvervals;  // in order of increasing start point
    TreeSet<Register> freeRegisterPool = new TreeSet<>();
    LinkedHashMap<Interval, Register> allocationRecord = new LinkedHashMap<>();
    LinkedHashMap<Interval, Integer> local = new LinkedHashMap<>();
    int locaStackIndex = 0;

    public LinearScanRegisterAllocation(TreeSet<Interval.CandidateInterval> candidateInvervals) {
        this.candidateInvervals = candidateInvervals;
        for (int i = 0; i < 8; i++) {
            freeRegisterPool.add(new Register("$s" + i));
            freeRegisterPool.add(new Register("$t" + i));
        }
        freeRegisterPool.add(new Register("$t8"));
    }

    private Register allocateANewRegister() {
        /*return a reg of type $s or $t*/
        Register retReg = freeRegisterPool.first();
        freeRegisterPool.remove(retReg);
        return retReg;
    }

    private void expireOldIntervals(Interval interval) {

        Iterator<Interval.ActiveInterval> iterator = active.iterator();
        while (iterator.hasNext()) {
            Interval j = iterator.next();
            if (j.end.line > interval.start.line) {
                return;
            }
            freeRegisterPool.add(allocationRecord.get(j));
            iterator.remove();
        }
    }

    private void spillAtInterval(Interval interval) {
        Interval spill = active.last();
        if (spill.getEnd() > interval.getEnd()) {
            allocationRecord.put(interval, allocationRecord.get(spill));
            local.put(spill, locaStackIndex++);
            active.remove(spill);
            active.add(new Interval.ActiveInterval(interval));
        } else {
            local.put(interval, locaStackIndex++);
        }
    }

    public HashMap<Interval, Register> allocate() {
        int R = 17;
        for (Interval i : candidateInvervals) {
            expireOldIntervals(i);
            if (active.size() == R) {
                /*Spill*/
                spillAtInterval(i);
            } else { // No need to spill
                allocationRecord.put(i, allocateANewRegister());
                active.add(new Interval.ActiveInterval(i));
            }

        }
        return allocationRecord;
    }


}
