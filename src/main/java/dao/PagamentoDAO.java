package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Pagamento;

public class PagamentoDAO extends GenericDAO<Pagamento, Long>{

	public PagamentoDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Pagamento> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Pagamento> lista = new ArrayList<Pagamento>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Pagamento> query = getEntityManager()
				.createQuery("SELECT c FROM Pagamento c "
						+ "WHERE c.tipo_pagamento "
						+ "LIKE (CONCAT('%',:text,'%')) ", Pagamento.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
