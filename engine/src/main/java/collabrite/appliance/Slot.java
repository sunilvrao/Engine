package collabrite.appliance;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Represents a Slot in the {@link ApplianceUnit}. A {@link Slot} is a collection of {@link SlotUnit}
 * </p>
 *
 * @author anil
 */
public interface Slot extends Serializable, Lifecycle {

    /**
     * Add a {@link SlotUnit}
     *
     * @param slot
     */
    void addSlotUnit(SlotUnit slot);

    /**
     * Add a {@link List} of {@link SlotUnit}
     *
     * @param theList
     */
    void addAllSlotUnits(List<SlotUnit> theList);

    /**
     * Remove a {@link SlotUnit}
     *
     * @param slot
     * @return
     */
    boolean removeSlotUnit(SlotUnit slot);

    /**
     * Get a {@link List} of all {@link SlotUnit}
     *
     * @return
     */
    List<SlotUnit> getSlotUnits();
}