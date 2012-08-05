package com.collabrite.zipcode.recommendations;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.ReadableIndex;

import scala.actors.threadpool.Arrays;
import collabrite.relationships.RelationshipBuilder;
import collabrite.relationships.RelationshipException;

/**
 * A class that establishes relationships between a zip code and its users
 * @author anil
 */
public class ZipCodeRecommendations {
    public static final String BOUGHT = "bought";
    public static final String LIVES = "lives";
    
    private RelationshipBuilder builder = new RelationshipBuilder();
    
    public RelationshipBuilder getBuilder(){
        return builder;
    }/*
    
    public void initialize() throws Exception{
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
        
        storeBoughtProducts(nodeAnil, new String[] {"iphone", "ipod", "glasses"});
        storeBoughtProducts(nodeTanveer, new String[] {"tv", "kindle", "lawnmower"});
        storeBoughtProducts(nodeSunil, new String[] {"iphone", "laptop", "vacation"});
        storeBoughtProducts(nodeTom, new String[] {"laptop"});
        storeBoughtProducts(nodeRob, new String[] {"tennis","camping"});
        storeBoughtProducts(nodeVince, new String[] {"laptop", "vacation"});
        
        builder.createRelationship(nodeAnil, node60304, LIVES);
        builder.createRelationship(nodeTanveer, node60304, LIVES);
        
        
        builder.createRelationship(nodeSunil, node60004, LIVES);
        builder.createRelationship(nodeVineet, node60004, LIVES);
        
        builder.createRelationship(nodeTom, node60005, LIVES);
        builder.createRelationship(nodeRob, node60005, LIVES);
        
        builder.createRelationship(nodeVince, node60189, LIVES);
    }*/
    
    @SuppressWarnings("unchecked")
    public void storeBoughtProducts(long nodeID, String[] prod) throws RelationshipException{
      //Add some properties for our users
        List<String> prodList = new ArrayList<String>();
        
        if(builder.hasProperty(nodeID, BOUGHT)){
            String[] savedProducts = (String[]) builder.getProperty(nodeID, BOUGHT);
           prodList.addAll(Arrays.asList(savedProducts));    
        }
        prodList.addAll(Arrays.asList(prod));
        
        int len = prodList.size();
        String[] storeProducts = new String[len];
        prodList.toArray(storeProducts);
        builder.addProperty(nodeID, BOUGHT, storeProducts);
    }
    
    public String[] productsBought(String username) throws Exception{
        ReadableIndex<Node> autoNodeIndex = builder.getAutoIndex();
        Node person = autoNodeIndex.get( "name", username ).getSingle() ;
        return (String[]) builder.getProperty(person.getId(), BOUGHT);
    }
    
    @SuppressWarnings("unchecked")
    public String[] recommendations(String zipcode) throws Exception{
        List<String> products = new ArrayList<String>();
        
        ReadableIndex<Node> autoNodeIndex = builder.getAutoIndex();
        Node zipcodeNode = autoNodeIndex.get( "name", zipcode ).getSingle() ;
        
        Iterable<Relationship> relations = zipcodeNode.getRelationships(Direction.INCOMING);
        
        if(relations != null){
            for(Relationship relation: relations){
                Node userNode = relation.getStartNode();
                if(userNode.hasProperty(BOUGHT)){
                    String[] boughtProducts = (String[]) userNode.getProperty(BOUGHT);
                    products.addAll(Arrays.asList(boughtProducts));   
                }
            }
        }
        int length = products.size();
        String[] result = new String[length];
        products.toArray(result);
        return result;
    }
}