package com.collabrite.zipcode.recommendations;

import java.io.Serializable;
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
    
    /**
     * Return the {@link RelationshipBuilder}
     * @return
     */
    public RelationshipBuilder getBuilder(){
        return builder;
    }
    
    /**
     * Store the products bought by an user
     * @param nodeID
     * @param prod
     * @throws RelationshipException
     */
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
    
    /**
     * Return the set of products bought by an user
     * @param username
     * @return
     * @throws Exception
     */
    public String[] productsBought(String username) throws Exception{
        ReadableIndex<Node> autoNodeIndex = builder.getAutoIndex();
        Node person = autoNodeIndex.get( "name", username ).getSingle() ;
        return (String[]) builder.getProperty(person.getId(), BOUGHT);
    }
    
    /**
     * Given a zipcode, return the names of recommended products
     * @param zipcode
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String[] recommendations(String zipcode) throws Exception{
        List<String> products = new ArrayList<String>();
   
        Node zipcodeNode = builder.getNodeWithName(zipcode ) ;
        
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
    
    /**
     * Return a list of product frequencies
     * @param zipcode
     * @return
     * @throws Exception
     */
    public List<ProductFrequency> frequencyOfProducts(String zipcode) throws Exception{
        List<ProductFrequency> freq = new ArrayList<ProductFrequency>();

        Node zipcodeNode = builder.getNodeWithName(zipcode ) ;
        Iterable<Relationship> relations = zipcodeNode.getRelationships(Direction.INCOMING);
        
        if(relations != null){
            for(Relationship relation: relations){
                Node userNode = relation.getStartNode();
                if(userNode.hasProperty(BOUGHT)){
                    String[] boughtProducts = (String[]) userNode.getProperty(BOUGHT);
                    for(String prod: boughtProducts){
                        boolean foundProduct = false;
                        //Loop through our list
                        for(ProductFrequency prodFreq : freq){
                            if(prod.equals(prodFreq.getProductName())){
                                prodFreq.frequency++;
                                foundProduct = true;
                                continue;
                            }
                        }
                        if(foundProduct == false){
                            freq.add(new ProductFrequency(prod, 1));
                        }
                    }   
                }
            }
        }
        
        return freq;
    }
    
     
    /**
     * Class that is a map of a product vs frequency in buying
     * @author anil
     */
    public static class ProductFrequency implements Serializable{
        private static final long serialVersionUID = 1L;
        private String productName;
        private int frequency;
        public ProductFrequency(String productName, int frequency) {
            this.productName = productName;
            this.frequency = frequency;
        }
        public String getProductName() {
            return productName;
        }
        public int getFrequency() {
            return frequency;
        }
    }
}