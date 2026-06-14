package nl.bioinf.ishofstede.service;

import nl.bioinf.ishofstede.model.Fragment;
import nl.bioinf.ishofstede.model.RestrictionEnzyme;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FragmentAnalysisService {

    private final EnzymeService enzymeService;

    public FragmentAnalysisService( EnzymeService enzymeService){
       this.enzymeService = enzymeService;
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

        for (RestrictionEnzyme enzyme : enzymeService.getEnzymes()){
            int cuts = countCutSites(sequence, enzyme.getRecognitionSite());

            if (cuts == requiredCuts) {
                matches.add(enzyme);
            }
        }
        return matches;
    }
}
