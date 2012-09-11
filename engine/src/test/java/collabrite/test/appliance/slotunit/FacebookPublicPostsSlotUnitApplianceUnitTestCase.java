package collabrite.test.appliance.slotunit;

import org.junit.Ignore;
import org.junit.Test;

import collabrite.appliance.plugins.SingleSlotUnitAppliance;
import collabrite.appliance.slot.out.DBDataOutput;
import collabrite.appliance.slot.transform.FacebookToSqlTransform;
import collabrite.appliance.slotunit.FacebookPublicPostsSlotUnit;

/**
 * Unit test the {@link FacebookPublicPostsSlotUnit}
 *
 * @author anil
 */
public class FacebookPublicPostsSlotUnitApplianceUnitTestCase {
    /*
     * private String[] terms = { "bosch tools", "bosch power", "dremel", "dremel tools", "rotozip", "rotozip tools",
     * "skil tools", "vermont american", "gilmour", "gilmour tools" };
     */
    private String[] terms = { "gilmour tools" };

    @Test
    @Ignore
    public void testFBPublic() throws Exception {
        int len = terms.length;

        for (int i = 0; i < len; i++) {
            try {
                seek(terms[i]);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            /*
             * System.out.println("Sleeping 1 min"); Thread.sleep(60000); //sleep 20 seconds
             */}

    }

    private void seek(String term) throws Exception {
        FacebookPublicPostsSlotUnit su = new FacebookPublicPostsSlotUnit();
        su.setSearchTerm(term);

        DBDataOutput db = new DBDataOutput();
        FacebookToSqlTransform transform = new FacebookToSqlTransform();
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