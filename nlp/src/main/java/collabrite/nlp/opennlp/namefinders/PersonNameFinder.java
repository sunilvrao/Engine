package collabrite.nlp.opennlp.namefinders;

/**
 * A NameFinder to detect person names
 *
 * @author anil
 */
public class PersonNameFinder extends AbstractNameFinder {
    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-person.bin";
    }
}