package nl.bioinf.ishofstede.model;

import java.util.List;
import java.time.LocalDateTime;

public class AnalysisResult {

    private final String sequence;
    private final String enzymeName;
    private final List<Fragment> fragments;
    private final LocalDateTime timestamp;

    public AnalysisResult(
            String sequence,
            String enzymeName,
            List<Fragment> fragments,
            LocalDateTime timestamp
    ) {
        this.sequence = sequence;
        this.enzymeName = enzymeName;
        this.fragments = fragments;
        this.timestamp = timestamp;
    }

    public String getSequence() {
        return sequence;
    }

    public String getEnzymeName() {
        return enzymeName;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}