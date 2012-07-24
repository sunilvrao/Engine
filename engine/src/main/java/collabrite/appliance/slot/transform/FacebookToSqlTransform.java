package collabrite.appliance.slot.transform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import collabrite.appliance.DataTransform;

import com.restfb.types.CategorizedFacebookType;
import com.restfb.types.Comment;
import com.restfb.types.FacebookType.Metadata;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post;
import com.restfb.types.Post.Action;
import com.restfb.types.Post.Comments;
import com.restfb.types.Post.Likes;
import com.restfb.types.Post.Place;
import com.restfb.types.Post.Privacy;
import com.restfb.types.Post.Property;

/**
 * An instance of {@link DataTransform} that converts a facebook post
 * into a sql record
 * @author anil
 */
public class FacebookToSqlTransform extends AbstractSqlTransform {
    @Override
    public Object transform(Object transform) throws IOException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Post facebookPost = (Post) transform;
        try{
            conn = getConnection();
            if(exists(facebookPost, conn)) 
                return null;

            preparedStatement = getStatement(conn);   

            preparedStatement.setString(1, getActions(facebookPost));
            preparedStatement.setString(2, getApplications(facebookPost) );
            preparedStatement.setString(3, facebookPost.getAttribution() );
            preparedStatement.setString(4, facebookPost.getCaption() );
            preparedStatement.setString(5, getComments(facebookPost) );
            preparedStatement.setTimestamp(6, new Timestamp(facebookPost.getCreatedTime().getTime()));
            preparedStatement.setString(7, facebookPost.getDescription() );
            preparedStatement.setString(8, getFrom(facebookPost));
            preparedStatement.setString(9, facebookPost.getIcon());
            preparedStatement.setString(10, getLikes(facebookPost));
            preparedStatement.setString(11, ""+facebookPost.getLikesCount());
            preparedStatement.setString(12, facebookPost.getLink());
            preparedStatement.setString(13, facebookPost.getMessage());
            preparedStatement.setString(14, getMetadata(facebookPost));
            preparedStatement.setString(15, facebookPost.getName());
            preparedStatement.setString(16, facebookPost.getObjectId());
            preparedStatement.setString(17, facebookPost.getPicture());
            preparedStatement.setString(18, getPlace(facebookPost));
            preparedStatement.setString(19, getPrivacy(facebookPost));
            preparedStatement.setString(20, getProperties(facebookPost));
            preparedStatement.setString(21, facebookPost.getSource());
            preparedStatement.setString(22, getTo(facebookPost));
            preparedStatement.setTimestamp(23, new Timestamp(facebookPost.getUpdatedTime().getTime()));
            preparedStatement.setString(24, facebookPost.getId());
            

            int count = preparedStatement.executeUpdate();
            System.out.println("Count=" + count);
        }catch(Exception e){
            throw new IOException(e);
        }finally{
            safeClose(preparedStatement);
            safeClose(conn);
        }
        return null;
    }

    private boolean exists(Post post, Connection conn){
        PreparedStatement stmt = null;
        try{
            String sql = "SELECT COUNT(*) FROM fbdata where id = ?";
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, post.getId());

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
    
    private String getTo(Post post){
        List<NamedFacebookType> list = post.getTo();
        return getListNamedFacebookType(list);
    }
    
    private String getPrivacy(Post post){
        Privacy p = post.getPrivacy();
        if(p != null){
            return p.toString();
        }
        return null;
    }
    private String getPlace(Post post){
        Place place = post.getPlace();
        if(place != null)
            return place.toString();
        return null;
    }
    
    private String getProperties(Post post){
        List<Property> list = post.getProperties();
        if(list != null){
            return list.toString();
        }
        return null;
    }

    private String getActions(Post post){
        List<Action> actions = post.getActions();
        int len = actions != null ? actions.size() : 0;
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < len ; i++){
            Action action = actions.get(i);
            builder.append("(").append(action.getLink()).append(",").append(action.getName()).append(")");
        }
        return builder.toString();
    }
    
    private String getApplications(Post post){
        NamedFacebookType nf  = post.getApplication();
        return getNamedFacebookType(nf);
    }
    
    private String getNamedFacebookType(NamedFacebookType nf){
        if(nf != null){
            return "(" + nf.getId() + "," + nf.getName() + "," + nf.getType() + ")";
        }
        return null;
    }
    
    private String getComments(Post post){
        Comments comments = post.getComments();
        StringBuilder builder = new StringBuilder();
        if(comments != null){
            builder.append(comments.getCount());
            List<Comment> data = comments.getData();
            if(data != null){
                for(Comment comment: data){
                    builder.append("(").append(comment.getId()).append(",").append(comment.getMessage());
                    builder.append(",").append(comment.getType()).append(")");
                }
            }
        }
        return builder.toString();
    }
    
    private String getMetadata(Post post){
        Metadata md = post.getMetadata();
        if(md != null){
            return md.toString();
        }
        return null;
    }
    
    private String getFrom(Post post){
        CategorizedFacebookType cft = post.getFrom();
        return cft.getId() + "," + cft.getName() + "," + cft.getType();
    }
    
    private String getLikes(Post post){
        Likes likes = post.getLikes();
        if(likes != null){
            List<NamedFacebookType> list = likes.getData();
            return getListNamedFacebookType(list);   
        }
        return null;
    }
    
    private String getListNamedFacebookType(List<NamedFacebookType> list){
        StringBuilder builder = new StringBuilder();
        if(list != null){
            for(NamedFacebookType nft: list){
                builder.append(getNamedFacebookType(nft));
            }
        }
        return builder.toString();
    }


    private PreparedStatement getStatement(Connection conn) throws Exception {

        /*
             CREATE TABLE fbdata
(
  actions text, -- csv of actions such as link,comments etc
  applications text, -- The application used to create this post.
  attribution text, -- Application Attribution
  caption text,
  comments text,
  createdtime timestamp with time zone,
  descr text, -- Description
  fromuser text, -- id,user name
  icon text,
  likes text, -- Number::(id,name)(id,name)
  likescount text,
  link text,
  message text,
  metadata text,
  name text,
  objectid text,
  picture text,
  place text,
  privacy text,
  props text,
  source text,
  touser text,
  updated timestamp with time zone,
  id text
)

         */
        // Prepare a statement to insert a record
        String sql = "INSERT INTO fbdata (actions,applications,attribution,caption,comments,createdtime," +
                "descr, fromuser, icon, likes, likescount,link,message, metadata, name," +
                "objectid, picture,place,privacy, props,source,touser, updated,id)" + 
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return conn.prepareStatement(sql);   
    }
}