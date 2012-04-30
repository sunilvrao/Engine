package collabrite.test.relationships;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import collabrite.relationships.RelationshipBuilder;

/**
 * Unit test the {@code RelationshipBuilder}
 *
 * @author anil
 */
public class RelationshipBuilderUnitTestCase {
    private RelationshipBuilder builder = null;

    String person1 = "anil";
    String person2 = "sunil";
    String person3 = "rakesh";
    String friend = "friend";
    String works = "works";
    String lives = "lives";

    @Before
    public void setup() throws Exception {
        builder = new RelationshipBuilder();
        builder.deleteAllData();
        builder.initialize();
    }

    @Test
    public void testRelationships() throws Exception {
        long node1 = builder.createNode(person1);
        long node2 = builder.createNode(person2);
        long node3 = builder.createNode(person3);

        boolean success = builder.createRelationship(node1, node2, friend);
        assertTrue(success);

        success = builder.createRelationship(node2, node3, friend);
        assertTrue(success);

        success = builder.createRelationship(node1, node3, friend);
        assertTrue(success);

        String[] relationships = builder.getRelationships(node1, node2);
        assertEquals(1, relationships.length);
        assertEquals(friend, relationships[0]);

        relationships = builder.getRelationships(node2, node3);
        assertEquals(1, relationships.length);
        assertEquals(friend, relationships[0]);

        relationships = builder.getRelationships(node1, node3);
        assertEquals(1, relationships.length);
        assertEquals(friend, relationships[0]);

        builder.addProperty(node1, lives, "Chicago");
        builder.addProperty(node2, lives, "Chicago");
        builder.addProperty(node3, lives, "Chicago");

        builder.addProperty(node1, works, "OSS");
        builder.addProperty(node2, works, "Analytics");
        builder.addProperty(node3, works, "Univ");

        Iterable<String> iter = builder.getPropertyKeys(node1);
        assertTrue(iter.iterator().hasNext());
        iter = builder.getPropertyKeys(node2);
        assertTrue(iter.iterator().hasNext());
        iter = builder.getPropertyKeys(node3);
        assertTrue(iter.iterator().hasNext());

        assertEquals("Chicago", builder.getProperty(node1, lives));
        assertEquals("Chicago", builder.getProperty(node2, lives));
        assertEquals("Chicago", builder.getProperty(node3, lives));

        assertEquals("OSS", builder.getProperty(node1, works));
        assertEquals("Analytics", builder.getProperty(node2, works));
        assertEquals("Univ", builder.getProperty(node3, works));
    }
}