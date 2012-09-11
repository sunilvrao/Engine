package collabrite.test.appliance;

import java.io.IOException;

import org.w3c.dom.Document;

import collabrite.appliance.SlotUnit;
import collabrite.appliance.util.DocumentUtil;

/**
 * Test {@link SlotUnit}
 *
 * @author anil
 */
public class TestDOMSlotUnit extends TestSlotUnit implements SlotUnit {
    private static final long serialVersionUID = 1L;

    @Override
    public void execute() {
        try {
            Document dom = (Document) input.open();
            output.store(DocumentUtil.asString(dom).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finished = true;
    }
}