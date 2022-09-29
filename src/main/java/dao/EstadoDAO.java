package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Estado;

public class EstadoDAO {
	private EntityManager em;
	
	public EstadoDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addEstado(Estado estado) {
		getEm().persist(estado);
	}
	
	public Estado updateEstado(Estado estado) {
		return getEm().merge(estado);
	}
	
	public Estado searchEstadoById(Long id) {
		Estado estadoBuscado = new Estado();
		estadoBuscado = getEm().find(Estado.class, id);
		return estadoBuscado;
	}
	
	public void removeEstadoById(Long id) {
		Estado estado = searchEstadoById(id);
		getEm().remove(estado);
	}
	
	public List<Estado> listAllEstados(){
		List<Estado> estados = new ArrayList<Estado>();
		
		TypedQuery<Estado> query = getEm()
				.createQuery("SELECT estado FROM Estado estado", Estado.class);
		
		estados = query.getResultList();
		
		return estados;
	}
	
	public List<Estado> listEstadoByNome(String nome){
		List<Estado> estados = new ArrayList<Estado>();
		
		TypedQuery<Estado> query = getEm()
				.createQuery("SELECT etado FROM Estado estado "
						+ "WHERE estado.nome =:nome", Estado.class);
		
		query.setParameter("nome", nome);
		
		estados = query.getResultList();
		
		return estados;
		
	}
	
	
}
