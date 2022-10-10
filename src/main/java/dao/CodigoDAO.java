package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Codigo;

public class CodigoDAO {
private EntityManager em;
	
	public CodigoDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addCodigo(Codigo codigo) {
		getEm().persist(codigo);
	}
	
	public Codigo updateCodigo(Codigo codigo) {
		return getEm().merge(codigo);
	}
	
	public Codigo searchCodigoById(Long id) {
		Codigo codigoBuscado = new Codigo();
		codigoBuscado = getEm().find(Codigo.class, id);
		return codigoBuscado;
	}
	
	public void removeCodigoById(Long id) {
		Codigo codigo = searchCodigoById(id);
		getEm().remove(codigo);
	}
	
	public List<Codigo> listAllCodigos(){
		List<Codigo> codigos = new ArrayList<Codigo>();
		
		TypedQuery<Codigo> query = getEm()
				.createQuery("SELECT codigo FROM Codigo codigo", Codigo.class);
		
		codigos = query.getResultList();
		
		return codigos;
	}
}
