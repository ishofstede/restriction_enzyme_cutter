package nl.bioinf.ishofstede.model;

public class RestrictionEnzyme {

    private String name;
    private String recognitionSite;

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