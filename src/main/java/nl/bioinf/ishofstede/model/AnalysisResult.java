package nl.bioinf.ishofstede.model;

import java.util.List;
import java.time.LocalDateTime;

/**
 * Represents the result of a DNA restriction enzyme analysis.
 * This class acts as a container for a single analysis run and is used to store results in the application history.
 * Uses the following:
 * - original DNA sequence input
 * - selected restriction enzyme
 * - generated DNA fragments
 * - timestamp of during the analysis
 * This model is also used by HistoryService to store the last five analyses
 * performed by the user.
 */
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