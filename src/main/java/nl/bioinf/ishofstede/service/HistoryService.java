package nl.bioinf.ishofstede.service;

import org.springframework.stereotype.Service;
import nl.bioinf.ishofstede.model.AnalysisResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Service responsible for storing the history of DNA analyses.
 * This class keeps an in-memory record of the last five analysis results.
 * Act as a simple storage per session without using a database.
 */
@Service
public class HistoryService {

    private final LinkedList<AnalysisResult> history =
            new LinkedList<>();
    /**
     * Adds a new analysis result to the history. The newest result is stored at the front of the list.
     * If the history exceeds five entries, the oldest entry is removed.
     * @param result the analysis result to store
     */
    public void addAnalysis(AnalysisResult result) {

        history.addFirst(result);

        if (history.size() > 5) {
            history.removeLast();
        }
    }

    public List<AnalysisResult> getHistory() {
        return new ArrayList<>(history);
    }
}