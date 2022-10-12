package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import config.Page;
import dao.PagamentoComBoletoDAO;
import models.PagamentoComBoleto;
import persistence.DataBaseConnection;

public class PagamentoBoletoService {
	//Attributes
	@PersistenceContext(unitName = "apploja")
	private final EntityManager em;
	
	private PagamentoComBoletoDAO dao;
	
	private EntityTransaction tx;
	
	//Constructors
	public PagamentoBoletoService() {
		em = DataBaseConnection.getConnection().getEntityManager();
		dao = new PagamentoComBoletoDAO(em);
	}
	
	//Methods
	public void addPagamentoBoleto(PagamentoComBoleto pagamento) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			getDao().addPagamentoComBoleto(pagamento);
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
	
	public PagamentoComBoleto updatePagamentoBoleto(PagamentoComBoleto pagamento) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			PagamentoComBoleto pagamentoAtual = getDao().updatePagamentoComBoleto(pagamento);
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
	
	public void removePagamentoComBoleto(Long id) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			getDao().removePagamentoBoletoById(id);
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
	
	public PagamentoComBoleto searchPagamentoComBoletoById(Long id) {
		PagamentoComBoleto pagamento = new PagamentoComBoleto();
		pagamento = dao.searchPagamentoBoletoById(id);
		return pagamento;
	}
	
	public List<PagamentoComBoleto> listAllPagamentosBoleto(){
		return dao.listAllPagamentoBoleto();
	}

	private PagamentoComBoletoDAO getDao() {
		return dao;
	}

	private EntityTransaction getTx() {
		return tx;
	}

	private EntityManager getEm() {
		return em;
	}

	public Page<PagamentoComBoleto> listaPaginada(int paginaAtual, int tamanhoPagina) {
		
		return dao.listaPaginada(paginaAtual, tamanhoPagina);
	}
}
