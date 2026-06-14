package nl.bioinf.ishofstede.service;

import org.springframework.stereotype.Service;

@Service
public class FastaService {

    public String formatFasta(
            String header,
            String sequence
    ) {

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