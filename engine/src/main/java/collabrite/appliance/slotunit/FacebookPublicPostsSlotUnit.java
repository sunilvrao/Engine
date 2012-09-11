package collabrite.appliance.slotunit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.restfb.Connection;
import com.restfb.types.Post;

import collabrite.appliance.SlotUnit;
import collabrite.data.source.facebook.FacebookPublicSearching;

/**
 * An instance of {@link SlotUnit} for Facebook Public Posts
 *
 * @author anil
 */
public class FacebookPublicPostsSlotUnit extends AbstractSlotUnit {

    private static final long serialVersionUID = 1L;

    protected String searchTerm = null;

    protected List<String> ignoreList = new ArrayList<String>();

    protected int messageLengthFilter = -1;

    /**
     * Set the search term
     *
     * @param searchTerm
     */
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    /**
     * Add a set of terms which when present will make the facebook post, ignored for insertion into data store
     *
     * @param list
     */
    public void setIgnoreList(List<String> list) {
        ignoreList.addAll(list);
    }

    /**
     * If the message is greater than the set limit, it will be discarded Default is -1: No limit
     *
     * @param messageLengthFilter
     */
    public void setMessageLengthFilter(int messageLengthFilter) {
        this.messageLengthFilter = messageLengthFilter;
    }

    @Override
    public void execute() {
        if (dataOutput == null)
            throw new RuntimeException("Data Output not injected");
        if (searchTerm == null)
            throw new RuntimeException("Search Term not injected");

        FacebookPublicSearching search = new FacebookPublicSearching();

        Connection<Post> connection = search.getConnectionForPublicPosts(searchTerm);

        Iterator<List<Post>> iterator = connection.iterator();
        while (iterator.hasNext()) {
            List<Post> list = iterator.next();
            for (Post post : list) {
                // Go through the ignore list
                if (!continueWithPost(post.getMessage())) {
                    continue;
                }
                try {
                    dataOutput.store(post);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        this.finishedExecutionFlag = true;
    }

    private boolean continueWithPost(String message) {
        if (message != null) {
            message = message.trim();
        }
        if (message != null) {
            for (String token : ignoreList) {
                if (message.contains(token)) {
                    return false;
                }
            }
        }
        if (messageLengthFilter != -1) {
            // there is a limit
            if (message != null && message.length() > messageLengthFilter) {
                return false;
            }
        }
        return true;
    }
}