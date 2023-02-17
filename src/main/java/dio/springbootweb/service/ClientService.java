package dio.springbootweb.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import dio.springbootweb.exception.ClientNullException;
import dio.springbootweb.exception.DBException;
import dio.springbootweb.model.Client;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

@Getter
@Setter
public class ClientService {
    MongoCollection<Document> conn = getConn();

    public ArrayList<Client> listAll() {
        ArrayList<Client> listClient = new ArrayList<Client>();
        for (Document client : conn.find()) {
            listClient.add(xformDocToClient(client));
        }
        return listClient;
    }

    public Client listByID(ObjectId id) {
        return xformDocToClient(conn.find(eq("_id", id)).first());
    }

    public Client add(Client client) {
        if(client.getName() == null) {
            throw new ClientNullException();
        }
        Document doc1 = new Document("name", client.getName())
                .append("phone", client.getPhone())
                .append("email", client.getEmail())
                .append("address", client.getAddress())
                .append("country", client.getCountry());
        InsertOneResult result = conn.insertOne(doc1);
        client.setId(result.getInsertedId().asObjectId().getValue().toString());
        return client;
    }

    public Client put(ObjectId id, Client client) {
        if(client.getName() == null) {
            throw new ClientNullException();
        }
        Document doc1 = new Document("name", client.getName())
                .append("phone", client.getPhone())
                .append("email", client.getEmail())
                .append("address", client.getAddress())
                .append("country", client.getCountry());
        conn.replaceOne(conn.find(eq("_id", id)).first(), doc1);
        client.setId(id.toString());
        return client;
    }

    public String delete(ObjectId id) {
        conn.deleteOne(conn.find(eq("_id", id)).first());
        return "Deleted";
    }

    public Client xformDocToClient(Document d) {
        Client c = new Client();

        c.setId(d.getObjectId("_id").toString());
        c.setName(d.getString("name"));
        c.setPhone(d.getString("phone"));
        c.setEmail(d.getString("email"));
        c.setAddress(d.getString("address"));
        c.setCountry(d.getString("country"));

        return c;
    }

    public MongoCollection<Document> getConn() {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb+srv://dbAdmin:vEYK4MowyI3ZN7oG@cluster0.nvzwm3z.mongodb.net/?retryWrites=true&w=majority");
            MongoDatabase database = mongoClient.getDatabase("API_data");
            return database.getCollection("clients");
        } catch (Exception e) {
            throw new DBException();
        }
    }
}
