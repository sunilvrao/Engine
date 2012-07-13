package collabrite.data.source.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Page;

/**
 * Determine all the likes for a public facebook page
 * @author anil
 */
public class FacebookPageLikes {
    private String pageName;
    private Page page;
    
    public FacebookPageLikes(String thePageName){
        this.pageName = thePageName;
        FacebookClient client = new DefaultFacebookClient();
        page = client.fetchObject(pageName, Page.class);
    }
    
    public long getNumberOfLikes(){
        return page.getLikes();
    }
    
    public String getID(){
        return page.getId();
    }
}