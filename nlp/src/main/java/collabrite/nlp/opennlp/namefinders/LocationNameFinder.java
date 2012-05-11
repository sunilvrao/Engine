package collabrite.nlp.opennlp.namefinders;

/**
 * A NameFinder to detect location in names
 *
 * @author anil
 */
public class LocationNameFinder extends AbstractNameFinder {
    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-location.bin";
    }
}