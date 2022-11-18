package dio.springbootweb.controller;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import dio.springbootweb.model.Client;

@RestController
@RequestMapping("/clients")
public class ClientController {
	MongoClient mongoClient = MongoClients.create("mongodb+srv://dbAdmin:vEYK4MowyI3ZN7oG@cluster0.nvzwm3z.mongodb.net/?retryWrites=true&w=majority");
	
	MongoDatabase database = mongoClient.getDatabase("API_data");	
	MongoCollection<Document> clients = database.getCollection("clients");	
	
	@GetMapping
	public ArrayList<Client> list() {
		ArrayList<Client> listClient = new ArrayList<Client>();
		for (Document client : clients.find()) {			
			listClient.add(xformDocToClient(client));
		}
		return listClient;
	}
	
	@GetMapping(path = "/{id}")
	public Client listByID(@PathVariable ObjectId id) {
		return xformDocToClient(clients.find(eq("_id", id)).first());
	}
	
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Client add(@RequestBody Client client) {
		Document doc1 = new Document("name", client.getName())
							.append("phone", client.getPhone())
							.append("email", client.getEmail())
							.append("address", client.getAddress())
							.append("country", client.getCountry());
		InsertOneResult result = clients.insertOne(doc1);
		client.setId(result.getInsertedId().asObjectId().getValue().toString());
		return client;
	}
	
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Client put(@RequestBody Client client, @PathVariable ObjectId id) {
		Document doc1 = new Document("name", client.getName())
				.append("phone", client.getPhone())
				.append("email", client.getEmail())
				.append("address", client.getAddress())
				.append("country", client.getCountry());
		clients.replaceOne(clients.find(eq("_id", id)).first(), doc1);
		client.setId(id.toString());
		return client;
	}
	
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String delete(@PathVariable ObjectId id) {
		clients.deleteOne(clients.find(eq("_id", id)).first());
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
}