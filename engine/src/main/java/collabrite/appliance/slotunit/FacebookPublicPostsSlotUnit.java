package collabrite.appliance.slotunit;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.restfb.Connection;
import com.restfb.types.Post;

import collabrite.appliance.SlotUnit;
import collabrite.data.source.facebook.FacebookPublicSearching;

/**
 * An instance of {@link SlotUnit} for Facebook Public Posts
 * @author anil
 */
public class FacebookPublicPostsSlotUnit extends AbstractSlotUnit {

    private static final long serialVersionUID = 1L;

    protected String searchTerm = null;
    
    /**
     * Set the search term
     * @param searchTerm
     */
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public void execute() {
        if(dataOutput == null)
            throw new RuntimeException("Data Output not injected");
        if(searchTerm == null)
            throw new RuntimeException("Search Term not injected");
        
        FacebookPublicSearching search = new FacebookPublicSearching();
        
        Connection<Post> connection = search.getConnectionForPublicPosts(searchTerm);

        Iterator<List<Post>> iterator = connection.iterator();
        while(iterator.hasNext()){
            List<Post> list = iterator.next();
            for(Post post: list){
                try {
                    dataOutput.store(post);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } 
        }
        
        this.finishedExecutionFlag = true;
    }
}