package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.PagamentoDAO;
import models.Pagamento;
import persistence.DataBaseConnection;

public class PagamentoService {
	//Attributes
	@PersistenceContext(unitName = "apploja")
	private final EntityManager em;
	
	private PagamentoDAO dao;
	
	private EntityTransaction tx;
	
	//Constructors
	public PagamentoService() {
		em = DataBaseConnection.getConnection().getEntityManager();
		dao = new PagamentoDAO(em);
	}
	
	//Methods
	public void addPagamento(Pagamento pagamento) {
		tx = getEm().getTransaction();
		try {
			getTx().begin();
			getDao().addPagamento(pagamento);
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
	
	public Pagamento updatePagamento(Pagamento pagamento) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			Pagamento pagamentoAtual = getDao().updatePagamento(pagamento);
			getTx().commit();
			return pagamentoAtual;
		}
		catch (Exception e) {
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
	
	public void removePagamento(Long id) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			getDao().removePagamentoById(id);
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
	
	public Pagamento searchPagamentoById(Long id) {
		Pagamento pagamento = new Pagamento();
		pagamento = dao.searchPagamentoById(id);
		return pagamento;
	}
	
	public List<Pagamento> listAllPagamentos(){
		return dao.listAllPagamentos();
	}
	
	private PagamentoDAO getDao() {
		return dao;
	}

	private EntityTransaction getTx() {
		return tx;
	}

	private EntityManager getEm() {
		return em;
	}
}
