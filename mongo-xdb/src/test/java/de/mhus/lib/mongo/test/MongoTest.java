/**
 * Copyright 2018 Mike Hummel
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mongodb.morphia.query.Query;

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
        manager.saveObject(null, null, elmer);

        assertNotNull(elmer.getId());

        final Employee bugs = new Employee("Bugs Bunny", 60000.0);
        manager.saveObject(null, null, bugs);

        assertNotNull(bugs.getId());

        System.out.println("Created: " + elmer);
        System.out.println("ObjectId: " + MoUtil.toObjectId(elmer.getId()));

        // get all
        {
            Query<Employee> q = manager.createQuery(Employee.class);
            int cnt = 0;
            for (Employee next : q.fetch()) {
                System.out.println("Found 1: " + next);
                cnt++;
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
            for (Employee next : q.fetch()) {
                System.out.println("Found 2: " + next);
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
            for (Employee next : q.fetch()) {
                System.out.println("Found 3: " + next);
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
        manager.saveObject(null, null, daffy);

        final Employee pepe = new Employee("Pepé Le Pew", 25000.0);
        manager.saveObject(null, null, pepe);

        //        elmer.getDirectReports().add(daffy);
        //        elmer.getDirectReports().add(pepe);

        manager.saveObject(null, null, elmer);

        // test dynamic values

        elmer.getValues().put("color", "white");
        manager.saveObject(null, null, elmer);

        // search
        // get one
        {
            Query<Employee> q = manager.createQuery(Employee.class);
            q.field("values.color").equal("white");
            int cnt = 0;
            for (Employee next : q.fetch()) {
                System.out.println("Found 4: " + next);
                cnt++;
            }
            assertEquals(1, cnt);
        }

        // test DbMetadata

        MoMetadata asterix = manager.inject(new MoMetadata("Asterix"));
        asterix.save();

        // assertNotNull(asterix.getId());

        MoMetadata obelix = manager.inject(new MoMetadata("Obelix"));
        obelix.save();

        // assertNotNull(obelix.getId());
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
