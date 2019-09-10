package de.mhus.lib.mongo.test;

import java.net.InetSocketAddress;
import java.util.UUID;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import de.mhus.lib.errors.MException;
import de.mhus.lib.mongo.MoManager;
import de.mhus.lib.mongo.MoUtil;
import dev.morphia.query.Query;
import dev.morphia.query.internal.MorphiaCursor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MongoTest {

    private static MongoCollection<Document> collection;
    private static MongoClient client;
    private static MongoServer server;

    @BeforeAll
    public static void setUp() {
        server = new MongoServer(new MemoryBackend());

        // bind on a random local port
        InetSocketAddress serverAddress = server.bind();

        client = new MongoClient(new ServerAddress(serverAddress));
        collection = client.getDatabase("testdb").getCollection("testcollection");
    }

    @AfterAll
    public static void tearDown() {
        client.close();
        server.shutdown();
    }

    @Test
    public void testSimpleInsertQuery() throws Exception {
        assertEquals(0, collection.countDocuments());

        // creates the database and collection in memory and insert the object
        Document obj = new Document("_id", 1).append("key", "value");
        collection.insertOne(obj);

        assertEquals(1, collection.countDocuments());
        assertEquals(obj, collection.find().first());
    }
    
    @Test
    public void testMongoSchema() throws MException {
        Schema schema = new Schema();
        MoManager manager = new MoManager(client, schema);
        
        // sample from http://mongodb.github.io/morphia/1.3/getting-started/quick-tour/
        final Employee elmer = new Employee("Elmer Fudd", 50000.0);
        manager.save(elmer);
        
        assertNotNull(elmer.getId());
        
        final Employee bugs = new Employee("Bugs Bunny", 60000.0);
        manager.save(bugs);
        
        assertNotNull(bugs.getId());

        System.out.println("Created: " + elmer);
        System.out.println("ObjectId: " + MoUtil.toObjectId(elmer.getId()));
        
        // get all
        {
            Query<Employee> q = manager.createQuery(Employee.class);
            int cnt = 0;
            try (MorphiaCursor<Employee> res = q.find()) {
                while (res.hasNext()) {
                    Employee next = res.next();
                    System.out.println("Found 1: " + next);
                    cnt++;
                }
            }
            assertEquals(2, cnt);
        }
        // get one
        {
            Query<Employee> q = manager.createQuery(Employee.class);
            ObjectId id = MoUtil.toObjectId(elmer.getId());
            System.out.println("Search: " + id);
            q.field("_id").equal(id);
            int cnt = 0;
            try (MorphiaCursor<Employee> res = q.find()) {
                while (res.hasNext()) {
                    Employee next = res.next();
                    System.out.println("Found 2: " + next);
                }
                cnt++;
            }
            assertEquals(1, cnt);
        }
        // get one
        {
            Query<Employee> q = manager.createQuery(Employee.class);
            String id = "Elmer Fudd";
            System.out.println("Search: " + id);
            q.field("name").equal(id);
            int cnt = 0;
            try (MorphiaCursor<Employee> res = q.find()) {
                while (res.hasNext()) {
                    Employee next = res.next();
                    System.out.println("Found 3: " + next);
                }
                cnt++;
            }
            assertEquals(1, cnt);
        }
        
        // select by id
        Employee elmer2 = manager.getObject(Employee.class, elmer.getId());
        assertNotNull(elmer2);
        assertNotNull(elmer2.getId());
        assertEquals(elmer.getName(), elmer2.getName());
        
        
        final Employee daffy = new Employee("Daffy Duck", 40000.0);
        manager.save(daffy);

        final Employee pepe = new Employee("Pepé Le Pew", 25000.0);
        manager.save(pepe);

        elmer.getDirectReports().add(daffy);
        elmer.getDirectReports().add(pepe);

        manager.save(elmer);
        
    }
    
    @Test
    public void testUUIDConverter() {
        ObjectId oid = new ObjectId("5a58a8352c3439f468cd8fcf");
        System.out.println(oid);
        UUID uuid = MoUtil.toUUID(oid);
        System.out.println(uuid);
        ObjectId oid2 = MoUtil.toObjectId(uuid);
        System.out.println(oid2);
        assertEquals(oid.toHexString(), oid2.toHexString());
    }
    
    
}