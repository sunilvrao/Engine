package collabrite.appliance.slot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import collabrite.appliance.Lifecycle;
import collabrite.appliance.Slot;
import collabrite.appliance.SlotUnit;

/**
 * A base class for implementations of {@link Slot}
 * @author anil
 */
public abstract class AbstractSlot implements Slot {
    private static final long serialVersionUID = 1L;

    protected List<SlotUnit> units = new ArrayList<SlotUnit>();

    /**
     * @throws IOException 
     * @see {@link Lifecycle#setUp()}
     */
    public void setUp() throws IOException {
        for (SlotUnit unit : units) {
            unit.setUp();
        }
    }
    /**
     * @see {@link Lifecycle#execute()}
     */
    public void execute() {
        for (SlotUnit unit : units) {
            unit.execute();
        }
    }
    /**
     * @throws IOException 
     * @see {@link Lifecycle#tearDown()}
     */
    public void tearDown() throws IOException {
        for (SlotUnit unit : units) {
            unit.tearDown();
        }
    }

    @Override
    public void addSlotUnit(SlotUnit slot) {
        units.add(slot);
    }

    @Override
    public boolean removeSlotUnit(SlotUnit slot) {
        return units.remove(slot);
    }
    
    public void setSlotUnits(List<SlotUnit> theList){
        units.addAll(theList);
    }

    @Override
    public List<SlotUnit> getSlotUnits() {
        return Collections.unmodifiableList(units);
    }
}