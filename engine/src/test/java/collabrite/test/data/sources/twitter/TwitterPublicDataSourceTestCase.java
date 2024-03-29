package collabrite.test.data.sources.twitter;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.junit.Test;

import twitter4j.QueryResult;
import twitter4j.Tweet;
import collabrite.data.source.twitter.TwitterPublicDataSource;

/**
 * Unit test the {@code TwitterPublicDataSource} class
 *
 * @author anil
 */
public class TwitterPublicDataSourceTestCase {
    @Test
    public void query() throws Exception {
        System.setProperty("twitter4j.debug", "true");
        TwitterPublicDataSource ds = new TwitterPublicDataSource();
        QueryResult result = ds.query("bosch tools", "en");

        FileOutputStream fos = new FileOutputStream("/tmp/tweet.dat");
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(result);
        out.close();
        display(result);
        List<Tweet> tweets = result.getTweets();
        System.out.println("Number of tweet=" + tweets.size());
        for (Tweet tweet : result.getTweets()) {
            System.out.println(tweet.getFromUser() + ":" + tweet.getText());
        }
    }

    private void display(QueryResult result) {
        System.out.println("***Query Result=" + result);
    }
}