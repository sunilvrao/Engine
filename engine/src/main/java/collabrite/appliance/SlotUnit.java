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
     * Set the {@link DataInput}
     *
     * @param dataInput
     */
    void setDataInput(DataInput<?> dataInput);

    /**
     * Get the {@link DataInput}
     *
     * @return
     */
    DataInput<?> getDataInput();

    /**
     * Set the {@link DataOutput}
     *
     * @param dataOutput
     */
    void setDataOutput(DataOutput dataOutput);

    /**
     * Get the {@link DataOutput}
     *
     * @return
     */
    DataOutput getDataOutput();

    /**
     * Indicates whether the {@link Slot} has finished execution.
     *
     * @return
     */
    boolean finishedExecution();
}