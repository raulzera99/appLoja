package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
	
}
