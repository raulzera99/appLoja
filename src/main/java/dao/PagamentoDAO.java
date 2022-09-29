package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Pagamento;

public class PagamentoDAO {
	private EntityManager em;
	
	public PagamentoDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addPagamento(Pagamento pagamento) {
		getEm().persist(pagamento);
	}
	
	public Pagamento updatePagamento(Pagamento pagamento) {
		return getEm().merge(pagamento);
	}
	
	public Pagamento searchPagamentoById(Long id) {
		Pagamento pagamentoBuscado = new Pagamento();
		pagamentoBuscado = getEm().find(Pagamento.class, id);
		return pagamentoBuscado;
	}
	
	public void removePagamentoById(Long id) {
		Pagamento pagamento = searchPagamentoById(id);
		getEm().remove(pagamento);
	}
	
	public List<Pagamento> listAllPagamentos (){
		List<Pagamento> pagamentos = new ArrayList<Pagamento>();
		TypedQuery<Pagamento> query = getEm()
				.createQuery("SELECT pagamento FROM Pagamento pagamento", Pagamento.class);
		pagamentos = query.getResultList();
		
		return pagamentos;
	}
}
