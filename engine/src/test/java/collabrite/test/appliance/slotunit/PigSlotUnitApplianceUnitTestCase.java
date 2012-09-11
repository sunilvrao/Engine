package collabrite.test.appliance.slotunit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import collabrite.appliance.plugins.SingleSlotUnitAppliance;
import collabrite.appliance.slotunit.ApachePigSlotUnit;

/**
 * Unit test a {@link SingleSlotUnitAppliance} with a {@link ApachePigSlotUnit}
 *
 * @author anil
 */
public class PigSlotUnitApplianceUnitTestCase {

    @Test
    public void testPig() throws Exception {
        ApachePigSlotUnit slotUnit = new ApachePigSlotUnit();
        slotUnit.addOption(ApachePigSlotUnit.PIG_SCRIPT_NAME, "pig/CASLog.pig");

        Map<String, String> params = new HashMap<String, String>();

        params.put("inputfile", "src/test/resources/pig/CASLoginLog.txt");
        params.put("outputfile", "target/result");

        slotUnit.setParams(params);

        SingleSlotUnitAppliance appliance = new SingleSlotUnitAppliance("PIG", slotUnit);

        appliance.setUp();
        appliance.execute();
        appliance.tearDown();
    }
}