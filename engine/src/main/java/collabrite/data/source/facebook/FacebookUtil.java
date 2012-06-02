package collabrite.data.source.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.types.Page;

/**
 * General Utility for Facebook Interaction
 *
 * @author anil
 */
public class FacebookUtil {
    
    public enum FacebookSearchType{ post};
    
    /**
     * Given an ID, get a public Page
     *
     * @param id The ID of the page
     * @return {@link Page} representing a facebook page
     */
    public static Page getPageWithID(String id) {
        return (new DefaultFacebookClient()).fetchObject(id, Page.class);
    }

}