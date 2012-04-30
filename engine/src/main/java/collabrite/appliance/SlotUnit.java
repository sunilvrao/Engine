package collabrite.appliance;

import java.io.Serializable;

/**
 * <p>
 * Represents a construction unit in a {@link Slot}. A {@link Slot} contains a collection of {@link SlotUnit}
 * </p>
 *
 * @author anil
 */
public interface SlotUnit extends Serializable, Lifecycle {

    /**
     * Indicates whether the {@link Slot} has finished execution.
     *
     * @return
     */
    boolean finishedExecution();
}