package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.CidadeDAO;
import models.Cidade;
import persistence.DataBaseConnection;

public class CidadeService {
	//Attributes
	@PersistenceContext(unitName = "apploja")
	private final EntityManager em;
	
	private CidadeDAO dao;
	
	private EntityTransaction tx;
	
	//Constructors
	public CidadeService() {
		em = DataBaseConnection.getConnection().getEntityManager();
		dao = new CidadeDAO(em);
	}
	
	//Methods
	public void addCidade(Cidade cidade) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			getDao().addCidade(cidade);
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
	
	public Cidade updateCidade(Cidade cidade) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			Cidade cidadeAtual = getDao().updateCidade(cidade);
			getTx().commit();
			return cidadeAtual;
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
	
	public void removeCidade(Long id) {
		tx = getEm().getTransaction();
		
		try {
			getTx().begin();
			getDao().removeCidadeById(id);
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
	
	public Cidade searchCidadeById(Long id) {
		Cidade cidade= new Cidade();
		cidade = dao.searchCidadeById(id);
		return cidade;
	}
	
	public List<Cidade> listAllCidades(){
		return dao.listAllCidades();
	}
	
	public List<Cidade> listCidadeByNome(String nome){
		return dao.listCidadeByNome(nome);
	}
	
	private CidadeDAO getDao() {
		return dao;
	}

	private EntityTransaction getTx() {
		return tx;
	}

	private EntityManager getEm() {
		return em;
	}
}
