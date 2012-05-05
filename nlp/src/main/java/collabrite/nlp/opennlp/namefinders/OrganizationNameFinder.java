package collabrite.nlp.opennlp.namefinders;

public class OrganizationNameFinder extends AbstractNameFinder {
    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-organization.bin";
    }
}