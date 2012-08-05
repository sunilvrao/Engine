package collabrite.relationships;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.AutoIndexer;
import org.neo4j.graphdb.index.ReadableIndex;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.util.FileUtils;
import org.neo4j.tooling.GlobalGraphOperations;

/**
 * Main class to build relationships
 *
 * @author anil
 */
public class RelationshipBuilder {
    // We need a location to store the DB files
    private String DB_PATH = "target/relationshipDB";

    private boolean initialized = false;

    private GraphDatabaseService graphDb;

    /**
     * Construct an instance of {@code RelationshipBuilder}
     */
    public RelationshipBuilder() {
    }

    /**
     * Construct an instance of {@code RelationshipBuilder}
     *
     * @param locationOfDB set the location of DB file
     */
    public RelationshipBuilder(String locationOfDB) {
        this.DB_PATH = locationOfDB;
    }

    /**
     * Important method that needs to be called before performing any relationship operations such as
     * {@link #createNode(String)}
     */
    public void initialize() {
        graphDb = new EmbeddedGraphDatabase(DB_PATH);
        registerShutdownHook(graphDb);
        setAutoIndexerProperties(new String[] {"name"}); //by default, auto index name
        initialized = true;
    }

    /**
     * Delete all data that is managed by this class.
     *
     * <b>NOTE:</b> Use with extreme care.
     */
    public void deleteAllData() {
        try {
            FileUtils.deleteRecursively(new File(DB_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add a property to a node
     *
     * @param node id of the node
     * @param propertyKey Key of the property we are trying to add
     * @param value value of the property
     * @throws RelationshipException
     */
    public void addProperty(long node, String propertyKey, Object value) throws RelationshipException {
        check();
        Transaction tx = graphDb.beginTx();
        try {
            Node theNode = graphDb.getNodeById(node);
            theNode.setProperty(propertyKey, value);
            tx.success();
        } finally {
            tx.finish();
        }
    }

    /**
     * Create a node for relationship
     *
     * @param name Name of the node
     * @return a unique id that identifies the node
     */
    public long createNode(String name) throws RelationshipException {
        check();
        Transaction tx = graphDb.beginTx();
        try {
            Node node = graphDb.createNode();
            node.setProperty("name", name);
            tx.success();
            return node.getId();
        } finally {
            tx.finish();
        }
    }

    /**
     * Create a relationship between two nodes
     *
     * @param firstNode ID of the first node
     * @param secondNode ID of the second node
     * @param relationship name of the relationship
     * @return true if the relationship was created; false -otherwise
     * @throws RelationshipException
     */
    public boolean createRelationship(long firstNode, long secondNode, String relationship) throws RelationshipException {
        check();
        Node node1 = graphDb.getNodeById(firstNode);
        Node node2 = graphDb.getNodeById(secondNode);
        if (node1 == null || node2 == null)
            throw new RelationshipException(" One of the nodes does not exist");
        RelationshipType relationshipType = RelationshipTypeCollection.get(relationship);

        Transaction tx = graphDb.beginTx();
        try {
            Relationship theRelation = node1.createRelationshipTo(node2, relationshipType);
            tx.success();
            return theRelation != null;
        } finally {
            tx.finish();
        }

    }

    /**
     * Given two nodes, find all the relationships between them
     *
     * @param firstNode id of the first node
     * @param secondNode id of the second node
     * @return an array of the relationship names
     * @throws RelationshipException
     */
    public String[] getRelationships(long firstNode, long secondNode) throws RelationshipException {
        check();
        List<String> list = new ArrayList<String>();
        Node node1 = graphDb.getNodeById(firstNode);

        Iterable<Relationship> relationships = node1.getRelationships(Direction.OUTGOING);
        if (relationships != null) {
            for (Relationship relationship : relationships) {
                Node endNode = relationship.getEndNode();
                if (endNode.getId() == secondNode) {
                    list.add(relationship.getType().name());
                }
            }
        }
        String[] results = new String[list.size()];
        list.toArray(results);
        return results;
    }
    
    /**
     * Get Related Nodes
     * @param node
     * @param relationship
     * @param direction
     * @return
     * @throws RelationshipException
     */
    public Iterable<Relationship> getRelatedNodes(Node node, String relationship, Direction direction) throws RelationshipException{
        check();
        RelationshipType relationshipType = RelationshipTypeCollection.get(relationship);
        return node.getRelationships(Direction.OUTGOING, relationshipType);
    }
    
    /**
     * Get the auto index to search
     * @return
     */
    public ReadableIndex<Node> getAutoIndex(){
        return graphDb.index()
                .getNodeAutoIndexer()
                .getAutoIndex();
    }
    
    /**
     * Set properties to auto index
     * @param properties
     */
    public void setAutoIndexerProperties(String[] properties){
        AutoIndexer<Node> nodeAutoIndexer = graphDb.index()
                .getNodeAutoIndexer();
        for(String prop: properties){
            nodeAutoIndexer.startAutoIndexingProperty( prop ); 
        }
        nodeAutoIndexer.setEnabled(true);
    }
    
    /**
     * Check whether a node has property
     * @param node
     * @param propertyName
     * @return
     * @throws RelationshipException
     */
    public boolean hasProperty(long node, String propertyName) throws RelationshipException{
        check();
        Node node1 = graphDb.getNodeById(node);
        return node1.hasProperty(propertyName);
    }

    /**
     * Get an iterable for the property keys
     *
     * @param node id of the node
     * @return
     * @throws RelationshipException
     */
    public Iterable<String> getPropertyKeys(long node) throws RelationshipException {
        check();
        Node node1 = graphDb.getNodeById(node);
        return node1.getPropertyKeys();
    }
    
    /**
     * Get all the nodes stored
     * @return
     */
    public Iterable<Node> getNodes(){
        GlobalGraphOperations go = GlobalGraphOperations.at(graphDb);
        return go.getAllNodes();
    }

    /**
     * Given a property key, get the value
     *
     * @param node
     * @param key
     * @return
     * @throws RelationshipException
     */
    public Object getProperty(long node, String key) throws RelationshipException {
        check();
        Node node1 = graphDb.getNodeById(node);
        
        if(node1.hasProperty(key) == false){
            return null;
        }

        return node1.getProperty(key);
    }

    private void check() throws RelationshipException {
        if (!initialized)
            throw new RelationshipException("Not Initialized");
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    /**
     * An internal collection that manages the neo4j relationship types
     */
    private static class RelationshipTypeCollection {
        private static Map<String, RelationshipType> relations = new HashMap<String, RelationshipType>();

        static RelationshipType get(final String key) {
            RelationshipType relationshipType = relations.get(key);
            if (relationshipType == null) {
                relationshipType = new RelationshipType() {
                    @Override
                    public String name() {
                        return key;
                    }
                };
                relations.put(key, relationshipType);
            }
            return relationshipType;
        }
    }
}