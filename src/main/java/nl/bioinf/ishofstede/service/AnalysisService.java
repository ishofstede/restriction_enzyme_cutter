package nl.bioinf.ishofstede.service;

import org.springframework.stereotype.Service;
import nl.bioinf.ishofstede.model.RestrictionEnzyme;

@Service
public class AnalysisService {
    /**
     * Convert FASTA string or raw input into a clean DNA sequence by removing header lines starting with '>',
     * whitespace and line breaks. Returns DNA in uppercase.
     * <p>
     * @param input a string of a DNA sequence or FASTA input
     * @return sequence of DNA
     */

    public String cleanSequence(String input) {

        if (input == null) {
            return "";
        }

        StringBuilder sequence = new StringBuilder();

        String[] lines = input.split("\\R");

        for (String line : lines) {

            line = line.trim();

            if (line.startsWith(">")) {
                continue;
            }

            if (line.isEmpty()) {
                continue;
            }
            sequence.append(line);
        }

        return sequence.toString()
                .replaceAll("\\s+", "")
                .toUpperCase();
    }
    public boolean isValidDNA(String sequence) {

        return sequence.matches("[ACGT]+");
    }
}