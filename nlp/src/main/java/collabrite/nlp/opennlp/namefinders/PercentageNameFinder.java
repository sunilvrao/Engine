package collabrite.nlp.opennlp.namefinders;

/**
 * A NameFinder to detect percentage
 *
 * @author anil
 */
public class PercentageNameFinder extends AbstractNameFinder {
    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-percentage.bin";
    }
}