package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Estado;

public class EstadoDAO extends GenericDAO<Estado, Long>{

	public EstadoDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Estado> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Estado> lista = new ArrayList<Estado>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Estado> query = getEntityManager()
				.createQuery("SELECT c FROM Estado c "
						+ "WHERE c.nome "
						+ "LIKE (CONCAT('%',:text,'%')) ", Estado.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
