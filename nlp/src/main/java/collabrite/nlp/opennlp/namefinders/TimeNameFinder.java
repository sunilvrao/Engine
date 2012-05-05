package collabrite.nlp.opennlp.namefinders;

public class TimeNameFinder extends AbstractNameFinder {
    public void tearDown() {
        model = null;
        nameFinder.clearAdaptiveData();
    }

    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-time.bin";
    }
}