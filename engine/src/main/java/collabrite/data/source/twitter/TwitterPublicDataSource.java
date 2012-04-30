package collabrite.data.source.twitter;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * A data source for twitter public feeds
 *
 * @author anil
 */
public class TwitterPublicDataSource {

    /**
     * <p>
     * Given a string and a language, this method returns tweets from the public stream.
     * </p>
     * <p>
     * The {@link QueryResult} will have information such as results per page, max tweet id etc.
     * </p>
     *
     * @param queryStr any string to search (such as Yonex)
     * @param lang
     * @return
     * @throws TwitterException
     */
    public QueryResult query(String queryStr, String lang) throws TwitterException {
        Twitter twitter = new TwitterFactory().getInstance();
        Query query = new Query(queryStr);
        query.setLang(lang);
        return twitter.search(query);
    }
}