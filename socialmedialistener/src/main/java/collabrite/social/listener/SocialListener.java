package collabrite.social.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import collabrite.appliance.plugins.SingleSlotUnitAppliance;
import collabrite.appliance.slot.out.DBDataOutput;
import collabrite.appliance.slot.transform.FacebookToSqlTransform;
import collabrite.appliance.slotunit.FacebookPublicPostsSlotUnit;

/**
 * Main class for pulling in public posts
 * from Facebook and Twitter and populate
 * the DB
 * @author anil
 */
public class SocialListener { 
    private String jdbcurl,driver,dbuser,dbpass;
    private List<String> ignoreList = new ArrayList<String>();
    
    private int delayBetweenBatches = 10; //10secs
    
    private int messageLimit = 100;
            
    public String getJdbcurl() {
        return jdbcurl;
    }

    public void setJdbcurl(String jdbcurl) {
        this.jdbcurl = jdbcurl;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDbuser() {
        return dbuser;
    }
    

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }

    public String getDbpass() {
        return dbpass;
    }

    public void setDbpass(String dbpass) {
        this.dbpass = dbpass;
    }
    
    public void setIgnoreList(List<String> ignoreList) {
        this.ignoreList.addAll(ignoreList);
    }

    public static void main(String[] args) throws Exception {
       if(args.length < 2){
           System.out.println("Usage needs facebook/twitter plus termsfile");
           System.exit(0);
       }
       String type = args[0]; //facebook or twitter
       String termsFile = args[1]; //file containing comma separated search terms
       String ignoreFile = args[2];
       
       //Get the db.properties file
       InputStream is = SocialListener.class.getClassLoader().getResourceAsStream("db.properties");
       Properties props = new Properties();
       props.load(is);
       
       SocialListener listener = new SocialListener();
       listener.setJdbcurl(props.getProperty("jdbc.url"));
       listener.setDriver(props.getProperty("jdbc.driver"));
       listener.setDbuser(props.getProperty("db.user"));
       listener.setDbpass(props.getProperty("db.pass"));
       listener.validateDBSettings();
       
       listener.generateIgnoreList(ignoreFile);
       
       if(type.equalsIgnoreCase("facebook")){
           listener.seekFacebook(termsFile);
       }else {
           throw new RuntimeException("Unknown type");
       }
    }
    
    private void generateIgnoreList(String ignoreFile) throws Exception{ 
            List<String> tokens = new ArrayList<String>();
            //Z means: "The end of the input but for the final terminator, if any"
            String output = new Scanner(new File(ignoreFile)).useDelimiter("\\Z").next();
            StringTokenizer st = new StringTokenizer(output,",");
            while(st.hasMoreTokens()){
               tokens.add(st.nextToken().trim()); 
            }
            this.setIgnoreList(tokens);
    }
    
    private void validateDBSettings() throws Exception{
        System.out.println(driver+ " " + jdbcurl + " " + dbuser);
        Class.forName(driver);
        Connection con = DriverManager.getConnection(jdbcurl, dbuser, dbpass);
        if(con == null)
            throw new RuntimeException("Unable to get connection");
        try{
            con.close();
        }catch(Exception e){}
    }
    
    public void seekFacebook(String fileName){
        List<String> tokens= new ArrayList<String>();
        try { 
            //Z means: "The end of the input but for the final terminator, if any"
            String output = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
            StringTokenizer st = new StringTokenizer(output,",");
            while(st.hasMoreTokens()){
               tokens.add(st.nextToken().trim()); 
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        for(String term: tokens){
            try {
                Thread.sleep(delayBetweenBatches * 1000L);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                FacebookPublicPostsSlotUnit su = new FacebookPublicPostsSlotUnit();
                su.setSearchTerm(term);
                su.setIgnoreList(ignoreList);
                su.setMessageLengthFilter(this.messageLimit);
                
                DBDataOutput db = new DBDataOutput();
                FacebookToSqlTransform transform = new FacebookToSqlTransform();
                transform.setDatabaseDriverName(driver);
                
                transform.setJdbcURL(jdbcurl);

                transform.setUsername(dbuser);
                transform.setPassword(dbpass);
                
                db.setDataTransform(transform);
                
                su.setDataOutput(db);

                SingleSlotUnitAppliance appliance = new SingleSlotUnitAppliance("FB", su);
                
                appliance.setUp();
                appliance.execute();
                appliance.tearDown();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}