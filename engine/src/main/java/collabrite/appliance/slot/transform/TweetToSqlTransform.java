package collabrite.appliance.slot.transform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;
import collabrite.appliance.DataTransform;

/**
 * Transform a Tweet to SQL storage
 * @author anil
 */
public class TweetToSqlTransform implements DataTransform {

    private String databaseDriverName = null;
    private String jdbcURL = null;
    private String username = null, password = null;

    public void setDatabaseDriverName(String databaseDriver) {
        this.databaseDriverName = databaseDriver;
    } 

    public void setJdbcURL(String jdbcURL) {
        this.jdbcURL = jdbcURL;
    } 

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object transform(Object t) throws IOException {
        QueryResult query = (QueryResult) t;
        List<Tweet> tweets = query.getTweets();
        PreparedStatement preparedStatement =  null;
        Connection conn = null;
        try {
            for(Tweet tweet: tweets){ 
                try{
                    conn = getConnection();
                    if(exists(tweet, conn)) continue;

                    preparedStatement = getStatement(conn);   
                    GeoLocation geo = tweet.getGeoLocation();
                    String lat = geo != null ? "" + geo.getLatitude() : null;
                    String lon = geo != null ? "" + geo.getLongitude() : null; 

                    preparedStatement.setTimestamp(1, new Timestamp(tweet.getCreatedAt().getTime()));
                    preparedStatement.setString(2, tweet.getFromUser() );
                    preparedStatement.setInt(3, (int)tweet.getFromUserId() );
                    preparedStatement.setString(4, "" + lat );
                    preparedStatement.setString(5, "" + lon );
                    preparedStatement.setString(6, getHashTags(tweet.getHashtagEntities()) );
                    preparedStatement.setLong(7, tweet.getId());
                    preparedStatement.setString(8, tweet.getIsoLanguageCode() );
                    preparedStatement.setString(9, tweet.getProfileImageUrl() );
                    preparedStatement.setString(10, tweet.getSource() );
                    preparedStatement.setString(11, tweet.getText());
                    preparedStatement.setString(12, tweet.getToUser() + "," + tweet.getToUserId() );
                    preparedStatement.setString(13, getURLs(tweet.getURLEntities()) );
                    preparedStatement.setString(14, getURLs(tweet.getUserMentionEntities()) );

                    int count = preparedStatement.executeUpdate();
                    System.out.println("Count=" + count);
                }finally{
                    safeClose(preparedStatement);
                    safeClose(conn);
                }

            }
        } catch (Exception e) { 
            throw new IOException(e);
        } finally{
            safeClose(preparedStatement);
            safeClose(conn);
        }

        return true;
    }

    private boolean exists(Tweet tweet, Connection conn){
        PreparedStatement stmt = null;
        try{
            String sql = "SELECT COUNT(*) FROM twitterdata where tweetid = ?";
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, tweet.getId());

            ResultSet resultSet = stmt.executeQuery();

            // Get the number of rows from the result set
            resultSet.next();
            int rowcount = resultSet.getInt(1);  
            if(rowcount > 0)
                return true;
            else
                return false;
        } catch(Exception e){
            return false;
        }finally{
            safeClose(stmt); 
        }
    }
    private void safeClose(Statement stmt){
        if(stmt != null){
            try {
                safeClose(stmt.getConnection());
                stmt.close();
            } catch (SQLException e) {
            }
        }
    }

    private void safeClose(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    private String getHashTags(HashtagEntity[] hashes){
        StringBuilder b = new StringBuilder();
        int len = hashes != null ? hashes.length:0;
        for(int i = 0 ; i < len; i++){
            b.append(hashes[i].getText()).append(",");
        }
        return b.toString();
    }

    private String getURLs(URLEntity[] urls){
        StringBuilder b = new StringBuilder();
        int len = urls != null ? urls.length : 0;
        for(int i = 0 ; i < len; i++){
            b.append(urls[i].getDisplayURL()).append(",");
        }
        return b.toString();
    }

    private String getURLs(UserMentionEntity[] users){
        StringBuilder b = new StringBuilder();
        int len = users != null ? users.length : 0;
        for(int i = 0 ; i < len; i++){
            UserMentionEntity user = users[i];
            b.append("(" + user.getName()+ "::" + user.getScreenName() + "::" + user.getId() + ")" + "," );
        }
        return b.toString();
    }

    private PreparedStatement getStatement(Connection conn) throws Exception {

        /*
             CREATE TABLE twitterdata
(
  "createdAt" date, -- When was the tweet created?
  "screenName" text,
  userid integer, -- Twitter ID
  "geoLat" text,
  "geoLong" text,
  hashtags text, -- A comma separated list of hashtags in the tweet
  tweetid text NOT NULL, -- Twitter assigned id  to the tweet.
  lang text, -- Two digit ISO language code "en"
  "profileImageUrl" text, -- URL of the profile image
  source text, -- Source of tweet.
  text text, -- Text of the tweet
  "toUser" text, -- To which user:  username,userid
  "urlEntities" text, -- Any URLs mentioned? Comma separated
  "userRef" text, -- User References. (User Full Name, screenName,id) are comma separated
)

         */
        // Prepare a statement to insert a record
        String sql = "INSERT INTO twitterdata (createdat,screenname,userid,geolat,geolong," +
                "hashtags,tweetid,lang,profileimage,source,text,touser,urlentities, userref)" + 
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return conn.prepareStatement(sql);   
    }

    private Connection getConnection() throws Exception{
        Class.forName(databaseDriverName);
        return DriverManager.getConnection(jdbcURL, username, password);
    }
}