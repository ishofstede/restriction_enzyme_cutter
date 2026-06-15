package nl.bioinf.ishofstede.service;

import org.springframework.stereotype.Service;

/**
 * Service responsible for formatting DNA sequences into FASTA format.
 * This class converts raw DNA sequences into FASTA output.
 */
@Service
public class FastaService {

    /**
     * Converts a DNA sequence into FASTA format.
     * The output includes header line
     * @param header FASTA header
     * @param sequence DNA sequence
     * @return formatted FASTA string
     */
    public String formatFasta(String header, String sequence) {

        StringBuilder fasta = new StringBuilder();

        fasta.append(">")
                .append(header)
                .append("\n");

        for (int i = 0; i < sequence.length(); i += 80) {

            int end =
                    Math.min(i + 80,
                            sequence.length());

            fasta.append(sequence, i, end)
                    .append("\n");
        }

        return fasta.toString();
    }
}