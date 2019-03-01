package org.greta.web;

import java.util.List;

import org.greta.dao.TacheRepository;
import org.greta.entities.Tache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TacheRestController {
	
	@Autowired
	private TacheRepository tacheRepository;
	
	@GetMapping("/taches")
	public List<Tache> ListTache(){
		return tacheRepository.findAll();
	}
	@PostMapping("/taches")
	public Tache saveTache(@RequestBody Tache t) {
		return tacheRepository.save(t);
	}
	
	
	
	
}
