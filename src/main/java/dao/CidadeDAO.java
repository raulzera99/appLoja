package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Cidade;

public class CidadeDAO extends GenericDAO<Cidade, Long>{

	public CidadeDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Cidade> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Cidade> lista = new ArrayList<Cidade>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Cidade> query = getEntityManager()
				.createQuery("SELECT c "
						+ "FROM Cidade c "
						+ "WHERE c.nome "
						+ "LIKE (CONCAT('%',:text,'%')) ", Cidade.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
