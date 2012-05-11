package collabrite.nlp.opennlp.namefinders;

/**
 * A NameFinder to detect organization names
 *
 * @author anil
 */
public class OrganizationNameFinder extends AbstractNameFinder {
    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-organization.bin";
    }
}