package collabrite.appliance.slotunit;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.pig.PigServer;

import collabrite.appliance.SlotUnit;

/**
 * A Slot Unit that is capable of running Pig Latin Scripts
 * @author anil
 */
public class ApachePigSlotUnit extends AbstractSlotUnit implements SlotUnit {

    private static final long serialVersionUID = 1L;

    public static final String PIG_SCRIPT_NAME = "script";
    public static final String PIG_MODE = "mode";

    public static final String LOCAL = "local";

    protected String pigScriptName = null;

    protected Map<String,String> params = null;

    protected List<String> paramFiles = null;

    public void setParams(Map<String,String> p){
        params = p;
    }

    public void setParamFiles(List<String> p){
        paramFiles = p;
    }


    @Override
    public void execute() {
        if(pigScriptName == null || pigScriptName.isEmpty()){
            pigScriptName = (String) options.get(PIG_SCRIPT_NAME);
        }
        if(pigScriptName == null){
            throw new IllegalStateException("Pig Script Name has not been configured");
        }
        String mode = (String) options.get(PIG_MODE);
        if(mode == null || mode.isEmpty()){
            mode = LOCAL;
        }
        PigServer pigServer = null;

        try {
            pigServer = new PigServer(mode);
            pigServer.setBatchOn();
            pigServer.debugOn();
            InputStream is = getClass().getClassLoader().getResourceAsStream(pigScriptName);
            if(params != null){
                pigServer.registerScript(is, params);
            } else if(paramFiles != null){
                pigServer.registerScript(is, paramFiles);
            } else {
                pigServer.registerScript(is);
            }
            pigServer.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(pigServer != null){
                pigServer.shutdown();
            }
        }
        this.finishedExecutionFlag = true;
    }
}