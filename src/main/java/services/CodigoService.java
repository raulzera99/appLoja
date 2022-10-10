package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.CodigoDAO;
import models.Codigo;
import persistence.DataBaseConnection;

public class CodigoService {
	//Attributes
		@PersistenceContext(unitName = "apploja")
		private final EntityManager em;
		
		private CodigoDAO dao;
		
		private EntityTransaction tx;
		
		//Constructors
		public CodigoService() {
			em = DataBaseConnection.getConnection().getEntityManager();
			dao = new CodigoDAO(em);
		}
		
		//Methods
		public void addCodigo(Codigo Codigo) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().addCodigo(Codigo);
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
		
		public Codigo updateCodigo(Codigo Codigo) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				Codigo CodigoAtual = getDao().updateCodigo(Codigo);
				getTx().commit();
				return CodigoAtual;
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
		
		public void removeCodigo(Long id) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().removeCodigoById(id);
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
		
		public Codigo searchCodigoById(Long id) {
			Codigo Codigo = new Codigo();
			Codigo = dao.searchCodigoById(id);
			return Codigo;
		}
		
		public List<Codigo> listAllCodigos(){
			return dao.listAllCodigos();
		}
		
		private CodigoDAO getDao() {
			return dao;
		}

		private EntityTransaction getTx() {
			return tx;
		}

		private EntityManager getEm() {
			return em;
		}
}
