package collabrite.test.data.sources.twitter;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.junit.Ignore;
import org.junit.Test;

import twitter4j.QueryResult;
import collabrite.appliance.slot.transform.TweetToSqlTransform;

public class TwitterToPostgres {

    @Test
    @Ignore
    public void store() throws Exception {
        FileInputStream fos = new FileInputStream("/tmp/tweet.dat");
        ObjectInputStream in = new ObjectInputStream(fos);
        QueryResult result = (QueryResult) in.readObject();
        storeInPostgres(result);
    }

    private void storeInPostgres(QueryResult result) throws Exception {

        TweetToSqlTransform transform = new TweetToSqlTransform();
        transform.setDatabaseDriverName("org.postgresql.Driver");

        transform.setJdbcURL("jdbc:postgresql://localhost/collabrite");

        transform.setUsername("collabrite");
        transform.setPassword("collabrite");

        transform.transform(result);
    }
}