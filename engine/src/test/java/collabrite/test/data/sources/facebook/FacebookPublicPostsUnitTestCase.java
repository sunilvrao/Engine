package collabrite.test.data.sources.facebook;

import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import collabrite.data.source.facebook.FacebookPublicSearching;

import com.restfb.Connection;
import com.restfb.types.Post;

/**
 * Unit Test the Public Posts from Facebook
 * @author anil
 */
public class FacebookPublicPostsUnitTestCase {

    @Test @Ignore
    public void testSearch() throws Exception{
        FacebookPublicSearching search = new FacebookPublicSearching();
        Connection<Post> connection = search.getConnectionForPublicPosts("tennis");
        Iterator<List<Post>> iterator = connection.iterator();
        while(iterator.hasNext()){
            List<Post> list = iterator.next();
            for(Post post: list){
                System.out.println(post);
            }
        }
    }
}