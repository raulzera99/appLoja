package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.CategoriaDAO;
import models.Categoria;
import persistence.DataBaseConnection;

public class CategoriaService {
	//Attributes
		@PersistenceContext(unitName = "apploja")
		private final EntityManager em;
		
		private CategoriaDAO dao;
		
		private EntityTransaction tx;
		
		//Constructors
		public CategoriaService() {
			em = DataBaseConnection.getConnection().getEntityManager();
			dao = new CategoriaDAO(em);
		}
		
		//Methods
		public void addCategoria(Categoria Categoria) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().addCategoria(Categoria);
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
		
		public Categoria updateCategoria(Categoria Categoria) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				Categoria CategoriaAtual = getDao().updateCategoria(Categoria);
				getTx().commit();
				return CategoriaAtual;
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
		
		public void removeCategoria(Long id) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().removeCategoriaById(id);
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
		
		public Categoria searchCategoriaById(Long id) {
			Categoria Categoria = new Categoria();
			Categoria = dao.searchCategoriaById(id);
			return Categoria;
		}
		
		public List<Categoria> listAllCategorias(){
			return dao.listAllCategorias();
		}
		
		private CategoriaDAO getDao() {
			return dao;
		}

		private EntityTransaction getTx() {
			return tx;
		}

		private EntityManager getEm() {
			return em;
		}
}
