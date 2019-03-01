package org.greta.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tache implements Serializable{
	
	@Id
	@GeneratedValue
	private Long id;
	private String nomTache;
	
	public Tache() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tache(String nomTache) {
		super();
		this.nomTache = nomTache;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomTache() {
		return nomTache;
	}

	public void setNomTache(String nomTache) {
		this.nomTache = nomTache;
	}
	
	
	

}
