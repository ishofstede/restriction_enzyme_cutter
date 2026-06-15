package nl.bioinf.ishofstede.service;

import org.springframework.stereotype.Service;

/**
 * This service is responsible for processing and validating DNA sequence input.
 * Converts raw user input, including FASTA format, into a clean DNA sequence
 * and checks whether the sequence only contains valid nucleotide characters (ATCG)
 */

@Service
public class SequenceService {
    /**
     * Cleans raw DNA input by removing FASTA headers, whitespace, and formatting characters
     * @param input raw user input sequence or FASTA format
     * @return cleaned DNA sequence in uppercase format
     */
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

    /**
     * Validates whether a DNA sequence contains only valid nucleotide characters.
     * @param sequence cleaned DNA sequence
     * @return null if valid, otherwise an error message
     */
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