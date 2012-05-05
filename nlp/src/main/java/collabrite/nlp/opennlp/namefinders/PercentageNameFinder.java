package collabrite.nlp.opennlp.namefinders;

public class PercentageNameFinder extends AbstractNameFinder {
    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-percentage.bin";
    }
}