package collabrite.data.source.facebook;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Post;

public class FacebookPublicSearching {
    protected FacebookClient publicOnlyFacebookClient = new DefaultFacebookClient();
    
    public Connection<Post> getConnectionForPublicPosts(String query){
        return publicOnlyFacebookClient.fetchConnection("search", Post.class,
                Parameter.with("q", query), 
                Parameter.with("limit", 100),
                Parameter.with("type", "post"));
    }
     
/*
    public void getPublicPosts(String query, FacebookSearchType type){ 
        
        Connection<Post> publicSearch =
                publicOnlyFacebookClient.fetchConnection("search", Post.class,
                        Parameter.with("q", query), 
                        Parameter.with("type", type.name()));
        
        List<Post> data = publicSearch.getData();
        for(Post post: data){
            System.out.println(post);
        }
    }*/
}