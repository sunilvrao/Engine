package collabrite.appliance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Represents the Appliance. An appliance is just a collection of {@link ApplianceUnit}
 * </p>
 *
 * @author anil
 */
public class Appliance implements Lifecycle {
    protected String name = "default";

    protected List<ApplianceUnit> units = new ArrayList<ApplianceUnit>();

    public Appliance() {
    }

    public Appliance(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addUnit(ApplianceUnit unit) {
        units.add(unit);
    }

    public void setUnits(List<ApplianceUnit> theUnits) {
        units.addAll(theUnits);
    }

    public boolean removeUnit(ApplianceUnit unit) {
        return units.remove(unit);
    }

    public List<ApplianceUnit> getUnits() {
        return Collections.unmodifiableList(units);
    }

    /**
     * @throws IOException 
     * @see Lifecycle#setUp()
     */
    @Override
    public void setUp() throws IOException {
        for (ApplianceUnit unit : units) {
            unit.setUp();
        }
    }

    /**
     * @see Lifecycle#execute()
     */
    @Override
    public void execute() {
        for (ApplianceUnit unit : units) {
            unit.execute();
        }
    }

    /**
     * @throws IOException 
     * @see Lifecycle#tearDown()
     */
    @Override
    public void tearDown() throws IOException {
        for (ApplianceUnit unit : units) {
            unit.tearDown();
        }
    }
}