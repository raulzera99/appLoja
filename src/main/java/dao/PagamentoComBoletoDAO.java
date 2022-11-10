package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.PagamentoComBoleto;

public class PagamentoComBoletoDAO extends GenericDAO<PagamentoComBoleto, Long>{

	public PagamentoComBoletoDAO(EntityManager em) {
		super(em);
	}
	

	public Page<PagamentoComBoleto> listaPaginada(Integer page, Integer pageSize, String text) {
		List<PagamentoComBoleto> lista = new ArrayList<PagamentoComBoleto>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<PagamentoComBoleto> query = getEntityManager()
				.createQuery("SELECT c FROM PagamentoComBoleto c "
						+ "WHERE c.estado "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.dataVencimento "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.dataPagamento "
						+ "LIKE (CONCAT('%',:text,'%')) ", PagamentoComBoleto.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
