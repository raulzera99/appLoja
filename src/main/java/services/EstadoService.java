package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.EstadoDAO;
import models.Estado;
import persistence.DataBaseConnection;

public class EstadoService {
	//Attributes
		@PersistenceContext(unitName = "apploja")
		private final EntityManager em;
		
		private EstadoDAO dao;
		
		private EntityTransaction tx;
		
		//Constructors
		public EstadoService() {
			em = DataBaseConnection.getConnection().getEntityManager();
			dao = new EstadoDAO(em);
		}
		
		//Methods
		public void addEstado(Estado estado) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().addEstado(estado);
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
		
		public Estado updateEstado(Estado estado) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				Estado estadoAtual = getDao().updateEstado(estado);
				getTx().commit();
				return estadoAtual;
			}
			catch(Exception e) {
				e.printStackTrace();
				if(getTx().isActive()) {
					getTx().rollback();
				}
			}
			return null;
		}
		
		public void removeEstado(Long id) {
			tx = getEm().getTransaction();
			try {
				getTx().begin();
				getDao().removeEstadoById(id);
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
		
		public Estado searchEstadoById(Long id) {
			Estado estado = new Estado();
			estado = dao.searchEstadoById(id);
			return estado;
		}
		
		public List<Estado> listAllEstados(){
			return dao.listAllEstados();
		}
		
		public List<Estado> listAllEstadosByNome(String nome){
			return dao.listEstadoByNome(nome);
		}
		
		private EstadoDAO getDao() {
			return dao;
		}

		private EntityTransaction getTx() {
			return tx;
		}

		private EntityManager getEm() {
			return em;
		}
}
