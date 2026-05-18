package nl.bioinf.ishofstede.service;

import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    public boolean isValidDNA(String sequence) {

        if (sequence == null) {
            return false;
        }

        sequence = sequence
                .replaceAll("\\s+", "")
                .toUpperCase();

        return sequence.matches("[ACGT]+");
    }
}