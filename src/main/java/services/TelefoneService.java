package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.TelefoneDAO;
import models.Telefone;
import persistence.DataBaseConnection;

public class TelefoneService {
	//Attributes
		@PersistenceContext(unitName = "apploja")
		private final EntityManager em;
		
		private TelefoneDAO dao;
		
		private EntityTransaction tx;
		
		//Constructors
		public TelefoneService() {
			em = DataBaseConnection.getConnection().getEntityManager();
			dao = new TelefoneDAO(em);
		}
		
		//Methods
		public void addTelefone(Telefone telefone) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().addTelefone(telefone);
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
		
		public Telefone updateTelefone(Telefone telefone) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				Telefone telefoneAtual = getDao().updateTelefone(telefone);
				getTx().commit();
				return telefoneAtual;
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
		
		public void removeTelefone(Long id) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().removeTelefone(id);
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
		
		public Telefone searchTelefoneById(Long id) {
			Telefone telefone = new Telefone();
			telefone = dao.searchTelefoneById(id);
			return telefone;
		}
		
		public List<Telefone> listAllTelefones(){
			return dao.listAllTelefones();
		}
		
		private TelefoneDAO getDao() {
			return dao;
		}

		private EntityTransaction getTx() {
			return tx;
		}

		private EntityManager getEm() {
			return em;
		}
}
