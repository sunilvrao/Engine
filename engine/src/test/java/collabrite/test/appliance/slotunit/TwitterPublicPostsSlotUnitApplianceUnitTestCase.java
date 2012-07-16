package collabrite.test.appliance.slotunit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import collabrite.appliance.plugins.SingleSlotUnitAppliance;
import collabrite.appliance.slot.out.DBDataOutput;
import collabrite.appliance.slot.transform.TweetToSqlTransform;
import collabrite.appliance.slotunit.AbstractSlotUnit;
import collabrite.appliance.slotunit.TwitterPublicPostsSlotUnit;

/**
 * Unit test the {@link TwitterPublicPostsSlotUnit}
 * @author anil
 */
public class TwitterPublicPostsSlotUnitApplianceUnitTestCase {

    private String[] terms = { "bosch tools", "bosch power", "dremel", "dremel tools",
      "rotozip", "rotozip tools", "skil tools", "vermont american", "gilmour",
      "gilmour tools"
    };
    
    @Test
    @Ignore
    public void testTwitterPublic() throws Exception{
        
        int len = terms.length;
        
        for(int i = 0 ; i < len; i++){
            try{
                seek(terms[i]);   
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("Sleeping 20 secs");
            Thread.sleep(20000); //sleep 20 seconds
        }
    }
    
    private void seek(String term) throws Exception{
        Map<String,Object> options = new HashMap<String, Object>();
        options.put(AbstractSlotUnit.DELAY, "30000");
        
        TwitterPublicPostsSlotUnit su = new TwitterPublicPostsSlotUnit();
        su.setSearchTerm(term);
        su.setMaxPage(50);
        su.setOptions(options);
        su.setDelay(12000); //12 secs
        
        DBDataOutput db = new DBDataOutput();
        
        //Set the twitter data transform
        TweetToSqlTransform transform = new TweetToSqlTransform();
        transform.setDatabaseDriverName("org.postgresql.Driver");
        
        transform.setJdbcURL("jdbc:postgresql://localhost/collabrite");

        transform.setUsername("collabrite");
        transform.setPassword("collabrite");
        
        db.setDataTransform(transform);
         
        su.setDataOutput(db);
        

        SingleSlotUnitAppliance appliance = new SingleSlotUnitAppliance("FB", su);
        
        appliance.setUp();
        appliance.execute();
        appliance.tearDown();
    }
}