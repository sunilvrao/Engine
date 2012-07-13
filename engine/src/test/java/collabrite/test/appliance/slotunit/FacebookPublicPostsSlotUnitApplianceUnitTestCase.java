package collabrite.test.appliance.slotunit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import collabrite.appliance.plugins.SingleSlotUnitAppliance;
import collabrite.appliance.slot.out.FileDataOutput;
import collabrite.appliance.slotunit.FacebookPublicPostsSlotUnit;

/**
 * Unit test the {@link FacebookPublicPostsSlotUnit}
 * @author anil
 */
public class FacebookPublicPostsSlotUnitApplianceUnitTestCase {

    @Test
    @Ignore
    public void testFBPublic() throws Exception{
        FacebookPublicPostsSlotUnit su = new FacebookPublicPostsSlotUnit();
        su.setSearchTerm("Bosch Tools");
        
        FileDataOutput fileOut = new FileDataOutput();
        Map<String,Object> theMap = new HashMap<String,Object>();
        theMap.put(FileDataOutput.FILENAME, "/tmp/bosch.txt");
        fileOut.setOptions(theMap);
        
        su.setDataOutput(fileOut);
        

        SingleSlotUnitAppliance appliance = new SingleSlotUnitAppliance("FB", su);
        
        appliance.setUp();
        appliance.execute();
        appliance.tearDown();
    }
}