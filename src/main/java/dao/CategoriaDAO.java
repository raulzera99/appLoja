package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Categoria;

public class CategoriaDAO extends GenericDAO<Categoria, Long>{

	public CategoriaDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Categoria> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Categoria> lista = new ArrayList<Categoria>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Categoria> query = getEntityManager()
				.createQuery("SELECT c FROM Categoria c "
						+ "WHERE c.nome "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.id "
						+ "LIKE (CONCAT('%',:text,'%')) ", Categoria.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
