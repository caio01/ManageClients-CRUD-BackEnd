package dio.springbootweb.controller;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dio.springbootweb.model.Client;

@RestController
@RequestMapping("/clients")
public class ClientController {

	MongoClient mongoClient = MongoClients.create("mongodb+srv://dbAdmin:vEYK4MowyI3ZN7oG@cluster0.nvzwm3z.mongodb.net/?retryWrites=true&w=majority");
	
	CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
	CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
	
	MongoDatabase database = mongoClient.getDatabase("API_data");	
	MongoCollection<Client> clients = database.getCollection("clients", Client.class).withCodecRegistry(pojoCodecRegistry);
	
	@GetMapping
	public List<Client> list() {
		List<Client> clientsList = new ArrayList<>();
		System.out.println(clients.find().into(clientsList));
		return clientsList;
	}
	
		
	@GetMapping(path = "/{id}")
	public Client listByID(@PathVariable String id) {
		return clients.find(eq("name", "Ila Burch")).first();
	}
	
	/*
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Client add(@RequestBody Client client) {
		return clientRepository.save(client);
	}
	
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Client put(@RequestBody Client client, @PathVariable String id) {
		return clientRepository.findById(id)
			      .map(x -> {
			    	  x.setName(client.getName());
			        return clientRepository.save(x);
			      })
			      .orElseGet(() -> {
			    	  client.setId(id);
			        return clientRepository.save(client);
			      });
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String delete(@PathVariable String id) {
		clientRepository.deleteById(id);
		return "Deleted";
	}
	
	*/
}