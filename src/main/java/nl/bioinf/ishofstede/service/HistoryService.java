package nl.bioinf.ishofstede.service;

import org.springframework.stereotype.Service;
import nl.bioinf.ishofstede.model.AnalysisResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class HistoryService {

    private final LinkedList<AnalysisResult> history =
            new LinkedList<>();

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
