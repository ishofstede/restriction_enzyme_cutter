package nl.bioinf.ishofstede.service;

import org.springframework.stereotype.Service;

@Service
public class SequenceService {

    public String cleanSequence(String input) {

        if (input == null) {
            return "";
        }

        StringBuilder sequence = new StringBuilder();

        String[] lines = input.split("\\R");

        for (String line : lines) {

            if (line.startsWith(">")) {
                continue;
            }

            sequence.append(line.trim());
        }

        return sequence.toString()
                .replaceAll("\\s+", "")
                .toUpperCase();
    }

    public String validateSequence(String sequence) {

        if (sequence == null || sequence.isBlank()) {
            return "Sequence input is empty.";
        }

        if (!sequence.matches("[ACGT]+")) {
            return "Invalid DNA sequence. Only A, C, G and T are allowed.";
        }

        return null;
    }
}