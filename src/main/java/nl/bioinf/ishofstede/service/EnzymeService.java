package nl.bioinf.ishofstede.service;

import nl.bioinf.ishofstede.model.RestrictionEnzyme;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for managing restriction enzyme data.
 * This class provides a in-memory list of restriction enzymes.
 * The enzyme database includes at least 15 restriction enzymes.
 */
@Service
public class EnzymeService {

    /**
     * Retrieves the list of supported restriction enzymes.
     * Each enzyme consists of a name and a recognition DNA sequence.
     * @return list of restriction enzymes
     */
    public List<RestrictionEnzyme> getEnzymes() {

        List<RestrictionEnzyme> enzymes = new ArrayList<>();

        enzymes.add(new RestrictionEnzyme("EcoRI", "GAATTC"));
        enzymes.add(new RestrictionEnzyme("BamHI", "GGATCC"));
        enzymes.add(new RestrictionEnzyme("HindIII", "AAGCTT"));
        enzymes.add(new RestrictionEnzyme("NotI", "GCGGCCGC"));
        enzymes.add(new RestrictionEnzyme("XhoI", "CTCGAG"));
        enzymes.add(new RestrictionEnzyme("SalI", "GTCGAC"));
        enzymes.add(new RestrictionEnzyme("PstI", "CTGCAG"));
        enzymes.add(new RestrictionEnzyme("KpnI", "GGTACC"));
        enzymes.add(new RestrictionEnzyme("SmaI", "CCCGGG"));
        enzymes.add(new RestrictionEnzyme("EcoRV", "GATATC"));
        enzymes.add(new RestrictionEnzyme("AluI", "AGCT"));
        enzymes.add(new RestrictionEnzyme("HaeIII", "GGCC"));
        enzymes.add(new RestrictionEnzyme("MspI", "CCGG"));
        enzymes.add(new RestrictionEnzyme("DraI", "TTTAAA"));
        enzymes.add(new RestrictionEnzyme("NcoI", "CCATGG"));

        return enzymes;
    }

    /**
     * Finds a restriction enzyme by name
     * @param name name of the enzyme (bvb EcoRI)
     * @return matching RestrictionEnzyme object, or null if not found
     */
    public RestrictionEnzyme findEnzymeByName(String name) {

        for (RestrictionEnzyme enzyme : getEnzymes()) {

            if (enzyme.getName().equals(name)) {
                return enzyme;
            }
        }

        return null;
    }
}