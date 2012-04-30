package collabrite.appliance.slot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import collabrite.appliance.Slot;
import collabrite.appliance.SlotUnit;

/**
 * An instance of {@link Slot} that executes all its {@link SlotUnit} in parallel.
 *
 * @author anil
 */
public class ParallelSlot extends AbstractSlot implements Slot {
    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(ParallelSlot.class.getCanonicalName());

    /**
     * Holds threads to execute the {@link SlotUnit}
     */
    protected ExecutorService executorService = null;

    public void setUp() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(units.size());
        }
    }

    public void tearDown() {
        if (log.isLoggable(Level.FINEST)) {
            log.finest("ParallelSlot:tearDown");
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    /**
     * Run the {@link SlotUnit} in parallel
     */
    public void execute() {
        if (log.isLoggable(Level.INFO)) {
            log.info("Executing Parallel Slot");
        }
        for (SlotUnit unit : units) {
            executorService.execute(new SlotThread(unit));
        }
    }

    public class SlotThread extends Thread {
        private SlotUnit aUnit;

        public SlotThread(SlotUnit aUnit) {
            this.aUnit = aUnit;
        }

        public void run() {
            aUnit.execute();
        }
    }
}