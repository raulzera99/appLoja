package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Telefone;

public class TelefoneDAO {
	private EntityManager em;
	
	public TelefoneDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addTelefone(Telefone telefone) {
		getEm().persist(telefone);
	}
	
	public Telefone updateTelefone(Telefone telefone) {
		return getEm().merge(telefone);
	}
	
	public Telefone searchTelefoneById(Long id) {
		Telefone telefoneBuscado = new Telefone();
		telefoneBuscado = getEm().find(Telefone.class, id);
		return telefoneBuscado;
	}
	
	public void removeTelefone(Long id) {
		Telefone telefone = searchTelefoneById(id);
		getEm().remove(telefone);
	}
	
	public List<Telefone> listAllTelefones(){
		List<Telefone> telefones = new ArrayList<Telefone>();
		TypedQuery<Telefone> query = getEm()
				.createQuery("SELECT t FROM Telefone t", Telefone.class);
		
		telefones = query.getResultList();
		
		return telefones;
	}
}
