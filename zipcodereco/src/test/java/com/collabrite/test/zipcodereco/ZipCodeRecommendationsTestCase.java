package com.collabrite.test.zipcodereco;

import static com.collabrite.zipcode.recommendations.ZipCodeRecommendations.LIVES;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import scala.actors.threadpool.Arrays;
import collabrite.relationships.RelationshipBuilder;

import com.collabrite.zipcode.recommendations.ZipCodeRecommendations;
import com.collabrite.zipcode.recommendations.ZipCodeRecommendations.ProductFrequency;

/**
 * Unit test the {@link ZipCodeRecommendations}
 * @author anil
 */
public class ZipCodeRecommendationsTestCase {

    private ZipCodeRecommendations zipRecommendations = new ZipCodeRecommendations();
    
    @Before
    public void initialize() throws Exception{
        RelationshipBuilder builder = zipRecommendations.getBuilder();
        //set up a bunch of users in a few zipcodes
        builder.deleteAllData();
        builder.initialize();
        
        //Create zipcodes
        long node60304 = builder.createNode("60304"); 
        
        long node60004 = builder.createNode("60004"); //Arlington heights 
        
        long node60189 = builder.createNode("60189");//wheaton 
        
        long node60005 = builder.createNode("60005");//dataco 
        
        
        //Create some users
        long nodeAnil = builder.createNode("anil");
        long nodeTanveer = builder.createNode("tanveer");
        
        long nodeSunil = builder.createNode("sunil");
        long nodeVineet = builder.createNode("vineet");
        
        long nodeTom = builder.createNode("tom");
        long nodeRob = builder.createNode("rob");
        
        long nodeVince = builder.createNode("vince");
        
        zipRecommendations.storeBoughtProducts(nodeAnil, new String[] {"iphone", "ipod", "glasses"});
        zipRecommendations.storeBoughtProducts(nodeTanveer, new String[] {"glasses", "tv", "kindle", "lawnmower"});
        zipRecommendations.storeBoughtProducts(nodeSunil, new String[] {"iphone", "laptop", "vacation"});
        zipRecommendations.storeBoughtProducts(nodeVineet, new String[] {"baseball", "candy", "tshirts"});
        zipRecommendations.storeBoughtProducts(nodeTom, new String[] {"laptop"});
        zipRecommendations.storeBoughtProducts(nodeRob, new String[] {"tennis","camping"});
        zipRecommendations.storeBoughtProducts(nodeVince, new String[] {"laptop", "vacation"});
        
        builder.createRelationship(nodeAnil, node60304, LIVES);
        builder.createRelationship(nodeTanveer, node60304, LIVES);
             
        builder.createRelationship(nodeSunil, node60004, LIVES);
        builder.createRelationship(nodeVineet, node60004, LIVES);
        
        builder.createRelationship(nodeTom, node60005, LIVES);
        builder.createRelationship(nodeRob, node60005, LIVES);
        
        builder.createRelationship(nodeVince, node60189, LIVES);
    }
    
    @Test
    public void reco() throws Exception{
        System.out.println("ANIL:60304:" + Arrays.toString(zipRecommendations.productsBought("anil"))); 
        System.out.println("TANVEER:60304:" + Arrays.toString(zipRecommendations.productsBought("tanveer"))); 
        System.out.println("SUNIL:60004:" + Arrays.toString(zipRecommendations.productsBought("sunil")));
        System.out.println("VINEET:60004:" + Arrays.toString(zipRecommendations.productsBought("vineet")));
        System.out.println("TOM:60005:" + Arrays.toString(zipRecommendations.productsBought("tom")));
        System.out.println("ROB:60005:" + Arrays.toString(zipRecommendations.productsBought("rob")));
        System.out.println("VINCE:60189:" + Arrays.toString(zipRecommendations.productsBought("vince")));
        
        System.out.println("Recommendations based on zip");
        System.out.println("60304::" + Arrays.toString(zipRecommendations.recommendations("60304")));
        System.out.println("60004::" + Arrays.toString(zipRecommendations.recommendations("60004")));
        System.out.println("60005::" + Arrays.toString(zipRecommendations.recommendations("60005")));
        System.out.println("60189::" + Arrays.toString(zipRecommendations.recommendations("60189")));
        

        System.out.println("Frequency Recommendations based on zip");
        printFrequency("60304");
        printFrequency("60004");
        printFrequency("60005");
        printFrequency("60189");
    }
    
    private void printFrequency(String zip) throws Exception{
        List<ProductFrequency> productFrequencies = zipRecommendations.frequencyOfProducts(zip);
        StringBuilder sb = new StringBuilder(zip + "::");
        for(ProductFrequency freq: productFrequencies){
            sb.append("[").append(freq.getProductName()).append("/").append(""+ freq.getFrequency()).append("]");
        }
        System.out.println(sb.toString());
    }
}