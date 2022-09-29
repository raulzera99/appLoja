package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.PagamentoComCartao;

public class PagamentoComCartaoDAO {
private EntityManager em;
	
	public PagamentoComCartaoDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addPagamentoCartao(PagamentoComCartao pagamento) {
		getEm().persist(pagamento);
	}
	
	public PagamentoComCartao updatePagamentoCartao (PagamentoComCartao pagamento) {
		return getEm().merge(pagamento);
	}
	
	public PagamentoComCartao searchPagamentoCartaoById(Long id) {
		PagamentoComCartao pagamentoBuscado = new PagamentoComCartao();
		pagamentoBuscado = getEm().find(PagamentoComCartao.class, id);
		return pagamentoBuscado;
	}
	
	public void removePagamentoCartao (Long id) {
		PagamentoComCartao pagamento = searchPagamentoCartaoById(id);
		getEm().remove(pagamento);
	}
	
	public List<PagamentoComCartao> listAllPagamentosCartao(){
		List<PagamentoComCartao> pagamentos = new ArrayList<PagamentoComCartao>();
		TypedQuery<PagamentoComCartao> query = getEm()
				.createQuery("SELECT p FROM PagamentoComCartao p", PagamentoComCartao.class);
		pagamentos = query.getResultList();
		
		return pagamentos;
	}
}
