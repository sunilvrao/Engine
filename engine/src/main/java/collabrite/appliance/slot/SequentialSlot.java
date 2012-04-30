package collabrite.appliance.slot;

import java.util.logging.Level;
import java.util.logging.Logger;

import collabrite.appliance.Slot;
import collabrite.appliance.SlotUnit;

/**
 * <p>
 * Represents a {@link Slot} that executes its {@link SlotUnit} in sequence.
 * </p>
 *
 * @author anil
 */
public class SequentialSlot extends AbstractSlot implements Slot {
    private static Logger log = Logger.getLogger(SequentialSlot.class.getCanonicalName());

    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * Execute the {@link SlotUnit} in sequence
     *
     * @see {@link Slot#execute()}
     */
    public void execute() {
        for (SlotUnit unit : units) {
            unit.execute();

            if (log.isLoggable(Level.FINEST)) {
                log.finest("SequentialSlot: waiting for " + unit);
            }
            while (unit.finishedExecution() == false)
                ; // Wait until the unit has finished execution

            if (log.isLoggable(Level.FINEST)) {
                log.finest("SequentialSlot: finished:" + unit);
            }
        }
    }
}