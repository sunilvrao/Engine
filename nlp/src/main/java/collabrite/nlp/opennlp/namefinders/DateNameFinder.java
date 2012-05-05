package collabrite.nlp.opennlp.namefinders;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;

public class DateNameFinder extends AbstractNameFinder {
    TokenNameFinderModel model = null;
    NameFinderME nameFinder = null;

    @Override
    protected String getModelName() {
        return "opennlp/models/namefinder/en-ner-date.bin";
    }
}