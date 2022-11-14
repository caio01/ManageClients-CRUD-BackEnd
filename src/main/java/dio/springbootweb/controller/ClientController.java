package dio.springbootweb.controller;

import dio.springbootweb.model.Client;
import java.util.List;
import java.util.Optional;

import dio.springbootweb.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@GetMapping
	public List<Client> list() {
		return clientRepository.findAll();
	}
	
	@GetMapping(path = "/{id}")
	public Optional<Client> listByID(@PathVariable Long id) {
		return clientRepository.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Client add(@RequestBody Client client) {
		return clientRepository.save(client);
	}
	
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Client put(@RequestBody Client client, @PathVariable Long id) {
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
	public String delete(@PathVariable Long id) {
		clientRepository.deleteById(id);
		return "Deleted";
	}
	
}