package collabrite.nlp.opennlp.namefinders;

public class LocationNameFinder extends AbstractNameFinder {
    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-location.bin";
    }
}