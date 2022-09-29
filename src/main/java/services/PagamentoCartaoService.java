package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.PagamentoComCartaoDAO;
import models.PagamentoComCartao;
import persistence.DataBaseConnection;

public class PagamentoCartaoService {
	//Attributtes
	@PersistenceContext(unitName = "apploja")
	private final EntityManager em;
	
	private PagamentoComCartaoDAO dao;
	
	private EntityTransaction tx;
	
	//Constructors
	public PagamentoCartaoService() {
		em = DataBaseConnection.getConnection().getEntityManager();
		dao = new PagamentoComCartaoDAO(em);
	}
	
	//Methods
	public void addPagamentoCartao(PagamentoComCartao pagamento) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			getDao().addPagamentoCartao(pagamento);
			getTx().commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			if(getTx().isActive()) {
				getTx().rollback();
			}
		}
		finally {
			getEm().close();
		}
	}
	
	public PagamentoComCartao updatePagamentoCartao(PagamentoComCartao pagamento) {
		tx = getEm().getTransaction();
		try {
			getTx().begin();
			PagamentoComCartao pagamentoAtual = getDao().updatePagamentoCartao(pagamento);
			getTx().commit();
			return pagamentoAtual;
		}
		catch(Exception e) {
			e.printStackTrace();
			if(getTx().isActive()) {
				getTx().rollback();
			}
		}
		finally {
			getEm().close();
		}
		return null;
	}
	
	public void removePagamentoCartao(Long id) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			getDao().removePagamentoCartao(id);
			getTx().commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			if(getTx().isActive()) {
				getTx().rollback();
			}
		}
		finally {
			getEm().close();
		}
	}
	
	public PagamentoComCartao searchPagamentoCartaoById(Long id) {
		PagamentoComCartao pagamento = new PagamentoComCartao();
		pagamento = dao.searchPagamentoCartaoById(id);
		return pagamento;
	}
	
	public List<PagamentoComCartao> listAllPagamentosCartao(){
		return dao.listAllPagamentosCartao();
	}
	
	private PagamentoComCartaoDAO getDao() {
		return dao;
	}

	private EntityTransaction getTx() {
		return tx;
	}

	private EntityManager getEm() {
		return em;
	}
}
