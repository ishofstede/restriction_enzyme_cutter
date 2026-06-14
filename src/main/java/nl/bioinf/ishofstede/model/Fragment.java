package nl.bioinf.ishofstede.model;

public class Fragment {

    private final int start;
    private final int end;
    private final int length;
    private final double gcPercent;
    private final double molecularWeight;
    private final String sequence;

    public Fragment(int start,
            int end,
            int length,
            double gcPercent,
            double molecularWeight,
            String sequence
    ) {
        this.start = start;
        this.end = end;
        this.length = length;
        this.gcPercent = gcPercent;
        this.molecularWeight = molecularWeight;
        this.sequence = sequence;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLength() {
        return length;
    }

    public double getGcPercent() {
        return gcPercent;
    }

    public double getMolecularWeight() {
        return molecularWeight;
    }

    public String getSequence() {
        return sequence;
    }
}