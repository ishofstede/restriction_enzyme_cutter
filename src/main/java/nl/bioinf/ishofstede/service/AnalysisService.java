package nl.bioinf.ishofstede.service;

import org.springframework.stereotype.Service;
import nl.bioinf.ishofstede.model.RestrictionEnzyme;

import java.util.ArrayList;
import java.util.List;

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

    public List<RestrictionEnzyme> getEnzymes() {

        List<RestrictionEnzyme> enzymes = new ArrayList<>();

        enzymes.add(new RestrictionEnzyme("AluI", "AGCT"));
        enzymes.add(new RestrictionEnzyme("HaeIII", "GGCC"));
        enzymes.add(new RestrictionEnzyme("MspI", "CCGG"));
        enzymes.add(new RestrictionEnzyme("XhoI", "CTCGAG"));
        enzymes.add(new RestrictionEnzyme("SalI", "GTCGAC"));
        enzymes.add(new RestrictionEnzyme("PstI", "CTGCAG"));
        enzymes.add(new RestrictionEnzyme("EcoRI", "GAATTC"));
        enzymes.add(new RestrictionEnzyme("BamHI", "GGATCC"));
        enzymes.add(new RestrictionEnzyme("HindIII", "AAGCTT"));
        enzymes.add(new RestrictionEnzyme("NotI", "GCGGCCGC"));
        enzymes.add(new RestrictionEnzyme("KpnI", "GGTACC"));
        enzymes.add(new RestrictionEnzyme("SmaI", "CCCGGG"));
        enzymes.add(new RestrictionEnzyme("EcoRV", "GATATC"));
        enzymes.add(new RestrictionEnzyme("DraI", "TTTAAA"));
        enzymes.add(new RestrictionEnzyme("NcoI", "CCATGG"));

        return enzymes;
    }

}