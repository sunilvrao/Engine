package collabrite.appliance;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Represents an unit in the appliance. Each unit has one or more {@link Slot}.
 * </p>
 *
 * @author anil
 */
public class ApplianceUnit implements Lifecycle {
    protected List<Slot> slots = new ArrayList<Slot>();

    public ApplianceUnit(List<Slot> theSlots) {
        slots.addAll(theSlots);
    }

    /**
     * Add a {@link Slot}
     *
     * @param slot
     */
    public void addSlot(Slot slot) {
        slots.add(slot);
    }

    /**
     * Add all the {@link Slot}
     *
     * @param theSlots
     */
    public void addAllSlots(List<Slot> theSlots) {
        slots.addAll(theSlots);
    }

    /**
     * Remove a {@link Slot}
     *
     * @param slot
     * @return
     */
    public boolean removeSlot(Slot slot) {
        return slots.remove(slot);
    }

    /**
     * @see Lifecycle#setUp()
     */
    @Override
    public void setUp() {
        for (Slot slot : slots) {
            slot.setUp();
        }
    }

    /**
     * @see Lifecycle#execute()
     */
    @Override
    public void execute() {
        for (Slot slot : slots) {
            slot.execute();
        }
    }

    /**
     * @see Lifecycle#tearDown()
     */
    @Override
    public void tearDown() {
        for (Slot slot : slots) {
            slot.tearDown();
        }
    }
}