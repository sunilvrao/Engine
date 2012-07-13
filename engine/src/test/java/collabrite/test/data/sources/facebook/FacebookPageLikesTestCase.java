package collabrite.test.data.sources.facebook;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import collabrite.data.source.facebook.FacebookPageLikes;

/**
 * Unit test the {@link FacebookPageLikes}
 * @author anil
 */
public class FacebookPageLikesTestCase {
    @Test
    public void testOPPL() throws Exception{
        FacebookPageLikes likes = new FacebookPageLikes("oakparkpubliclibrary");
        long count = likes.getNumberOfLikes();
        System.out.println("Like Count=" + count);
        assertTrue(count > 1000);
    }
}