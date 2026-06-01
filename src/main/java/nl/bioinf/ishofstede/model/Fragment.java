package nl.bioinf.ishofstede.model;

public class Fragment {

    private int start;
    private int end;
    private int length;
    private double gcPercent;
    private double molecularWeight;
    private String sequence;

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