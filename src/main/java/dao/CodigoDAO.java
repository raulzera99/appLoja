package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Codigo;

public class CodigoDAO extends GenericDAO<Codigo, Long>{

	public CodigoDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Codigo> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Codigo> lista = new ArrayList<Codigo>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Codigo> query = getEntityManager()
				.createQuery("SELECT c FROM Codigo c "
						+ "WHERE c.numero "
						+ "LIKE (CONCAT('%',:text,'%')) ", Codigo.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
