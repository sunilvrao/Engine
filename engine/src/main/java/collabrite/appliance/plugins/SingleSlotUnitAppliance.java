package collabrite.appliance.plugins;

import scala.actors.threadpool.Arrays;
import collabrite.appliance.Appliance;
import collabrite.appliance.ApplianceUnit;
import collabrite.appliance.Slot;
import collabrite.appliance.SlotUnit;
import collabrite.appliance.slot.SequentialSlot;

/**
 * Represents an {@link Appliance} that has just one {@link SlotUnit}
 * @author anil
 */
public class SingleSlotUnitAppliance extends Appliance {
    
    @SuppressWarnings("unchecked")
    public SingleSlotUnitAppliance(String theName, SlotUnit unit){
        super(theName);
        if(unit == null){
            throw new IllegalArgumentException("unit is null");
        }
        Slot slot = new SequentialSlot();
        slot.addSlotUnit(unit);
        
        ApplianceUnit appunit = new ApplianceUnit(Arrays.asList(new Slot[]{slot}));
        
        this.units.add(appunit);
    }
}