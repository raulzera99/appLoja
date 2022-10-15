package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Telefone;

public class TelefoneDAO extends GenericDAO<Telefone, Long>{

	public TelefoneDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Telefone> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Telefone> lista = new ArrayList<Telefone>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Telefone> query = getEntityManager()
				.createQuery("SELECT c FROM Telefone c "
						+ "WHERE c.numero "
						+ "LIKE (CONCAT('%',:text,'%')) ", Telefone.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
