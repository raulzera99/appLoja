package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.PagamentoComBoleto;

public class PagamentoComBoletoDAO {
	private EntityManager em;
	
	public PagamentoComBoletoDAO(EntityManager em){
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addPagamentoComBoleto(PagamentoComBoleto pagamentoComBoleto) {
		getEm().persist(pagamentoComBoleto);
	}
	
	public PagamentoComBoleto updatePagamentoComBoleto (PagamentoComBoleto pagamentoComBoleto) {
		return getEm().merge(pagamentoComBoleto);
	}
	
	public PagamentoComBoleto searchPagamentoBoletoById(Long id) {
		PagamentoComBoleto pagamentoBuscado = new PagamentoComBoleto();
		pagamentoBuscado = getEm().find(PagamentoComBoleto.class,id);
		return pagamentoBuscado;
	}
	
	public void removePagamentoBoletoById(Long id) {
		PagamentoComBoleto pagamentoBoleto = searchPagamentoBoletoById(id);
		getEm().remove(pagamentoBoleto);
	}
	
	public List<PagamentoComBoleto> listAllPagamentoBoleto(){
		List<PagamentoComBoleto> pagamentosBoleto = new ArrayList<PagamentoComBoleto>();
		TypedQuery<PagamentoComBoleto> query = getEm().createQuery("SELECT pagamentoBoleto FROM PagamentoComBoleto pagamentoBoleto", PagamentoComBoleto.class);
		
		pagamentosBoleto = query.getResultList();
		
		return pagamentosBoleto;
	}

	public Page<PagamentoComBoleto> listaPaginada(int paginaAtual, Integer tamanhoPagina) {
		List<PagamentoComBoleto> listaPagamentoBoleto = new ArrayList<PagamentoComBoleto>();
		Page<PagamentoComBoleto> page = new Page<PagamentoComBoleto>();
		
		Long total = count();
		
		Integer pagina = ((paginaAtual - 1)*tamanhoPagina);
		
		if(pagina < 0) {
			pagina = 0;
		}
		
		Double totalPaginas = Math.ceil(total.doubleValue() / tamanhoPagina.doubleValue());
		
		TypedQuery<PagamentoComBoleto> query = getEm()
				.createQuery("SELECT p FROM PagamentoComBoleto p", PagamentoComBoleto.class);
		
		listaPagamentoBoleto = query.setFirstResult(pagina)
				.setMaxResults(tamanhoPagina)
				.getResultList();
		
		page.setContent(listaPagamentoBoleto);
		page.setPage(paginaAtual);
		page.setPageSize(tamanhoPagina);
		page.setTotalRecords(total.intValue());
		page.setTotalPage(totalPaginas.intValue());
		
		return page;
	}

	private Long count() {
		TypedQuery<Long> query = getEm()
				.createQuery("SELECT COUNT(p) FROM PagamentoComBoleto p", Long.class);
		Long total = query.getSingleResult();
		return total;
	}
	
}
