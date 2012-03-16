package collabrite.data.source.facebook;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

/**
 * <p>
 * Represents interaction with a Facebook Account
 * </p>
 * <p>
 * Also see the documentation for RestFB OSS. <a href="http://www.restfb.com">RestFB</a>
 * </p>
 * @author anil
 */
public class FacebookAccount {
    private String accessToken = null;
    
    FacebookClient facebookClient = null;
    
    public FacebookAccount(String accessToken) {
        super();
        this.accessToken = accessToken;
        facebookClient = new DefaultFacebookClient(this.accessToken);
    }
    
    /**
     * Get an object about this account
     * @return
     */
    public User getSelf(){
        return facebookClient.fetchObject("me", User.class);
    }
    
    /**
     * Get the friends
     * @return a {@link Connection} that is iteratable
     */
    public Connection<User> myFriends(){
        return facebookClient.fetchConnection("me/friends", User.class);
    }
    
    /**
     * Get all my posts
     * @return
     */
    public Connection<Post> posts() {
        return facebookClient.fetchConnection("me/feed", Post.class);
    }
    
    /**
     * Get the Facebook page
     * @param pageName name of the page such as CocoCola
     * @return a {@link Page} representing Facebook page
     */
    public Page getPage(String pageName){
        return facebookClient.fetchObject(pageName, Page.class);
    }
}