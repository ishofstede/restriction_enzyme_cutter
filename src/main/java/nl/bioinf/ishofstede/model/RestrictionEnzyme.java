package nl.bioinf.ishofstede.model;

/**
 * Represents a restriction enzyme used in DNA analysis.
 * A restriction enzyme recognizes a specific DNA sequence and used to simulate DNA cutting
 * This model stores only immutable metadata:
 * - enzyme name
 * - recognition sequence
 */
public class RestrictionEnzyme {

    private final String name;
    private final String recognitionSite;


    public RestrictionEnzyme(
            String name,
            String recognitionSite
    ) {
        this.name = name;
        this.recognitionSite = recognitionSite;
    }

    public String getName() {
        return name;
    }

    public String getRecognitionSite() {
        return recognitionSite;
    }
}