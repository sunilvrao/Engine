package collabrite.test.data.sources.facebook;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.restfb.Connection;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Page;
import com.restfb.types.User;
import com.restfb.types.User.Education;

import collabrite.data.source.facebook.FacebookAccount;
import collabrite.data.source.facebook.FacebookUtil;

/**
 * <p>
 * Unit test the {@code FacebookAccount}
 * </p>
 * <p>
 * This test uses a time sensitive access token.
 *
 * How do you get an access token? It's really easy with the Facebook Graph API. Sign in to Facebook and navigate to the <a
 * href="https://developers.facebook.com/tools/explorer">Graph API Explorer app</a>. Click the "Get Access Token" button. That's
 * it! Keep in mind that this is only useful for quick testing. For production use, you'll need to create a Facebook Application
 * and use it to obtain OAuth tokens for your users. Take a look at the FB Authentication documentation for more details.
 * </p>
 * <p>
 * Also see the documentation for RestFB OSS. <a href="http://www.restfb.com">RestFB</a>
 * </p>
 *
 * @author anil
 */
public class FacebookAccountUnitTestCase {

    String accessToken = "AAAAAAITEghMBACqCIYSjjWwXrKmHwlzlUIty1U4LtC8jJOZAC9o6lU5A6FFroSc5hLS7RkvqIATDjFxO6Od8KUfoZBsWl6cZBRQ2ifVDKRXx54FWZCBC";

    @Ignore
    @Test
    public void testMyTestFB() {
        FacebookAccount acc = new FacebookAccount(accessToken);
        User user = acc.getSelf();
        assertNotNull(user);
        System.out.println("Email:" + user.getEmail());
        List<NamedFacebookType> teams = user.getFavoriteTeams();
        if (teams != null) {
            for (NamedFacebookType team : teams) {
                System.out.println("Favorite Team:" + team.getName() + "and more info:" + team);

                Page page = FacebookUtil.getPageWithID(team.getId());
                System.out.println("Fan page=" + page);
            }
        }

        List<Education> educationList = user.getEducation();
        if (educationList != null) {
            for (Education education : educationList) {
                System.out.println("Education=" + education);
            }
        }

        Connection<User> theFriends = acc.myFriends();
        List<User> friends = theFriends.getData();
        if (friends != null) {
            for (User friend : friends) {
                System.out.println(friend);
            }
        }
    }
}