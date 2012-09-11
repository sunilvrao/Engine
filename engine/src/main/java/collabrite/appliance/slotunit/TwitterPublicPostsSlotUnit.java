package collabrite.appliance.slotunit;

import java.io.IOException;
import twitter4j.QueryResult;
import twitter4j.TwitterException;
import collabrite.appliance.SlotUnit;
import collabrite.data.source.twitter.TwitterPublicDataSource;

/**
 * An instance of {@link SlotUnit} for Twitter Public Posts Used Via Searching
 *
 * @author anil
 */
public class TwitterPublicPostsSlotUnit extends AbstractSlotUnit {

    private static final long serialVersionUID = 1L;

    protected String searchTerm = null;

    protected String lang = "en";

    protected boolean iterate = true;

    protected int maxPage = 50; // Maximum number of pages we will search

    protected int delay = 10000; // 10 secs between pages

    /**
     * Set the delay between pages
     *
     * @param delay
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * Set the search term
     *
     * @param searchTerm
     */
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    /**
     * Optionally set the language. Default is "en"
     *
     * @param lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Set whether we need to fetch next page
     *
     * @param iterate
     */
    public void setIterate(boolean iterate) {
        this.iterate = iterate;
    }

    /**
     * Set the maximum number of pages
     *
     * @param maxPage
     */
    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    @Override
    public void execute() {
        if (searchTerm == null)
            throw new RuntimeException("Search Term not injected");

        TwitterPublicDataSource ds = new TwitterPublicDataSource();
        int page = 1;
        do {
            try {
                ds.setPage(page);
                System.out.println("Seeking :" + searchTerm + "::page=" + page);
                QueryResult result = ds.query(searchTerm, lang);
                page = result.getPage();
                try {
                    dataOutput.store(result);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                page = page + 1;
                System.out.println("[TwitterPublicPostsSU]:sleeping secs:" + delay / 1000);
                Thread.sleep(delay);
            } catch (TwitterException e1) {
                throw new RuntimeException(e1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (page < maxPage);

        this.finishedExecutionFlag = true;
    }
}