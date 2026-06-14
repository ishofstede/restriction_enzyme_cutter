package nl.bioinf.ishofstede.service;

import nl.bioinf.ishofstede.model.Fragment;
import org.springframework.stereotype.Service;
import nl.bioinf.ishofstede.model.RestrictionEnzyme;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalysisService {
    /**
     * Service responsible for DNA restriction analysis and all its calculations, its functions are:
     * - Parses raw and FASTA formatted DNA sequences
     * - Validates DNA input
     * - Stores available restriction enzymes
     * - Finds restriction enzyme cut sites
     * - Generates DNA fragments
     * - Calculates fragment properties such as GC percentage
     *   and molecular weight
     * - Produces FASTA output for restriction fragments
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
        return sequence.toString().replaceAll("\\s+", "").toUpperCase();
    }

    public String validateSequence(String sequence) {

        if (sequence == null || sequence.isBlank()){
            return "Sequence input is empty.";
        }
        if (!sequence.matches("[ATCG]+")){
            return "invalid DNA sequence, only ACGT allowed.";
        }
        return null;
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

    public RestrictionEnzyme findEnzymeByName(String name){

        for (RestrictionEnzyme enzyme : getEnzymes()){
            if (enzyme.getName().equals(name)){
                return enzyme;
            }
        }
        return null;
    }

    public int countCutSites(String sequence, String recognitionsite) {

        int count = 0;

        for (int i = 0; i <= sequence.length() - recognitionsite.length(); i++){
            String sub = sequence.substring(i, i + recognitionsite.length());

            if (sub.equals(recognitionsite)){
                count++;
            }
        }
        return count;
    }

    public List<Integer> findCutPositions(String sequence, String recognitionSite) {

    List<Integer> cuts = new ArrayList<>();

    for (int i = 0; i <= sequence.length() - recognitionSite.length();i++){
        String sub = sequence.substring(i, i + recognitionSite.length());

        if (sub.equals(recognitionSite)){
            cuts.add(i);
        }
    }
    return cuts;
    }

    public double calculateGCPercent(String sequence){

        if (sequence.isEmpty()){
            return 0;
        }

        int gc = 0;

        for (char c: sequence.toCharArray()){

            if (c == 'G' || c == 'C') {
                gc++;
            }
        }
        return  (gc * 100.0) / sequence.length();
    }

    public double calculateMolecularWeight(String sequence) {

        double weight = 0;

        for (char c: sequence.toCharArray()){
            switch (c){
                case 'A':
                    weight += 313.21;
                    break;

                case'T':
                    weight += 304.2;
                    break;

                case 'C':
                    weight += 289.18;
                    break;

                case 'G':
                    weight += 329.21;
                    break;
            }
        }
        return weight;
    }

    public List<Fragment> generateFragments(String sequence, String recognitionSite) {

        List<Integer> cuts = findCutPositions(sequence, recognitionSite);
        List<Fragment> fragments = new ArrayList<>();
        int previousCut = 0;

        //loop over all cut positions and create fragments between cuts
        for (int cut : cuts) {
            String fragmentSequence = sequence.substring(previousCut, cut);

            // Create a Fragment object with added properties
            Fragment fragment =
                    new Fragment(
                            previousCut + 1,                      //start
                            cut,                                          //end
                            fragmentSequence.length(),                  //length
                            calculateGCPercent(fragmentSequence),       // GC percentage
                            calculateMolecularWeight(fragmentSequence), //molecular weight
                            fragmentSequence                            //Sequence
                    );

            fragments.add(fragment);
            //move start position forward
            previousCut = cut;
        }
        //handle the last fragment (after the final cut)
        String fragmentSequence = sequence.substring(previousCut);

        Fragment fragment =
                new Fragment(
                        previousCut + 1,
                        sequence.length(),
                        fragmentSequence.length(),
                        calculateGCPercent(fragmentSequence),
                        calculateMolecularWeight(fragmentSequence),
                        fragmentSequence
                );

        fragments.add(fragment);

        return fragments;
    }

    public List<RestrictionEnzyme> findSingleOrDoubleCutters(String sequence, int requiredCuts){
        List<RestrictionEnzyme> matches = new ArrayList<>();

        for (RestrictionEnzyme enzyme : getEnzymes()){
            int cuts = countCutSites(sequence, enzyme.getRecognitionSite());

            if (cuts == requiredCuts) {
                matches.add(enzyme);
            }
        }
        return matches;
    }

    public String formatFasta(String header, String sequence){

        StringBuilder fasta = new StringBuilder();
        fasta.append(">").append(header).append("\n");

        for (int i = 0; i <sequence.length(); i += 80){
            int end = Math.min(i + 80, sequence.length());
            fasta.append(sequence, i, end).append("\n");
        }
        return fasta.toString();
    }
}