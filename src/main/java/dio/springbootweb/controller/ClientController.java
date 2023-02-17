package dio.springbootweb.controller;

import java.util.ArrayList;

import dio.springbootweb.service.ClientService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dio.springbootweb.model.Client;

@CrossOrigin
@RestController
@RequestMapping("/clients")
public class ClientController {
	ClientService service = new ClientService();

	@GetMapping
	public ArrayList<Client> list() {
		return service.listAll();
	}

	@GetMapping(path = "/{id}")
	public Client listByID(@PathVariable ObjectId id) {
		return service.listByID(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Client add(@RequestBody Client client) {
		return service.add(client);
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Client put(@RequestBody Client client, @PathVariable ObjectId id) {
		return service.put(id, client);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String delete(@PathVariable ObjectId id) {
		return service.delete(id);
	}
}