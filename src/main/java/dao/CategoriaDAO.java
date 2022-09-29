package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Categoria;

public class CategoriaDAO {
	private EntityManager em;
	
	public CategoriaDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addCategoria(Categoria categoria) {
		getEm().persist(categoria);
	}
	
	public Categoria updateCategoria(Categoria categoria) {
		return getEm().merge(categoria);
	}
	
	public Categoria searchCategoriaById(Long id) {
		Categoria categoriaBuscada = new Categoria();
		categoriaBuscada = getEm().find(Categoria.class, id);
		return categoriaBuscada;
	}
	
	public void removeCategoriaById(Long id) {
		Categoria categoria = searchCategoriaById(id);
		getEm().remove(categoria);
	}
	
	public List<Categoria> listAllCategorias (){
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		TypedQuery<Categoria> query = getEm()
				.createQuery("SELECT categoria FROM Categoria categoria", Categoria.class);
		
		categorias = query.getResultList();
		
		return categorias;
	}
	
	public List<Categoria> listCategoriaByNome (String nome){
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		TypedQuery<Categoria> query = getEm()
				.createQuery("SELECT categoria FROM Categoria categoria "
						+ "WHERE categoria.nome =:nome", Categoria.class);
		
		query.setParameter("nome", nome);
		
		categorias = query.getResultList();
		
		return categorias;
	}
	
	
	
}
