package collabrite.nlp.opennlp.namefinders;

public class PersonNameFinder extends AbstractNameFinder {
    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-person.bin";
    }
}