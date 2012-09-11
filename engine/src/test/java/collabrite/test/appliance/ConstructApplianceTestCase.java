package collabrite.test.appliance;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import collabrite.appliance.Appliance;
import collabrite.appliance.ApplianceConstants;
import collabrite.appliance.ApplianceUnit;
import collabrite.appliance.Slot;
import collabrite.appliance.SlotUnit;
import collabrite.appliance.slot.SequentialSlot;
import collabrite.appliance.slot.in.DOMDataInput;
import collabrite.appliance.slot.in.FileDataInput;
import collabrite.appliance.slot.out.SystemOutDataOutput;

/**
 * Test construction of an {@link Appliance} using {@link ApplianceUnit}, {@link Slot} and {@link SlotUnit}
 *
 * @author anil
 */
public class ConstructApplianceTestCase {

    @Test
    public void testAppliance() throws Exception {
        Appliance appliance = new Appliance();

        SystemOutDataOutput sysout = new SystemOutDataOutput();

        // Let us construct 2 slots
        SequentialSlot slot1 = new SequentialSlot();
        SequentialSlot slot2 = new SequentialSlot();

        SlotUnit fileSlotUnit = new TestSlotUnit();
        FileDataInput fdi = new FileDataInput();
        fdi.addOption(ApplianceConstants.NAME, "appliance/slots/dummyFile.txt");
        fileSlotUnit.setDataInput(fdi);
        fileSlotUnit.setDataOutput(sysout);

        slot1.addSlotUnit(fileSlotUnit);

        DOMDataInput ddi = new DOMDataInput();
        ddi.addOption(ApplianceConstants.NAME, "appliance/slots/some.xml");

        SlotUnit fileSlotUnit2 = new TestDOMSlotUnit();
        fileSlotUnit2.setDataInput(ddi);
        fileSlotUnit2.setDataOutput(sysout);

        slot2.addSlotUnit(fileSlotUnit2);

        List<Slot> list = new ArrayList<Slot>();

        list.add(slot1);
        list.add(slot2);

        // Construct an appliance unit
        ApplianceUnit unit = new ApplianceUnit(list);
        appliance.addUnit(unit);

        appliance.setUp();
        appliance.execute();
        appliance.tearDown();
    }
}