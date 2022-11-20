package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.PagamentoComCartao;

public class PagamentoComCartaoDAO extends GenericDAO<PagamentoComCartao, Long>{

	public PagamentoComCartaoDAO(EntityManager em) {
		super(em);
	}
	

	public Page<PagamentoComCartao> listaPaginada(Integer page, Integer pageSize, String text) {
		List<PagamentoComCartao> lista = new ArrayList<PagamentoComCartao>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<PagamentoComCartao> query = getEntityManager()
				.createQuery("SELECT c FROM PagamentoComCartao c "
						+ "WHERE c.estado "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.numeroDeParcelas "
						+ "LIKE (CONCAT('%',:text,'%')) ", PagamentoComCartao.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
