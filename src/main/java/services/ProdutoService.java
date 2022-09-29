package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.ProdutoDAO;
import models.Produto;
import persistence.DataBaseConnection;

public class ProdutoService {
	//Attributes
		@PersistenceContext(unitName = "apploja")
		private final EntityManager em;
		
		private ProdutoDAO dao;
		
		private EntityTransaction tx;
		
		//Constructors
		public ProdutoService() {
			em = DataBaseConnection.getConnection().getEntityManager();
			dao = new ProdutoDAO(em);
		}
		
		//Methods
		public void addProduto(Produto Produto) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().addProduto(Produto);
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
		
		public Produto updateProduto(Produto Produto) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				Produto ProdutoAtual = getDao().updateProduto(Produto);
				getTx().commit();
				return ProdutoAtual;
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
		
		public void removeProduto(Long id) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().removeProdutoById(id);
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
		
		public Produto searchProdutoById(Long id) {
			Produto Produto = new Produto();
			Produto = dao.searchProdutoById(id);
			return Produto;
		}
		
		public List<Produto> listAllProdutos(){
			return dao.listAllProdutos();
		}
		
		public List<Produto> listAllProdutoByNome(String nome){
			return dao.listProdutoByNome(nome);
		}
		
		private ProdutoDAO getDao() {
			return dao;
		}

		private EntityTransaction getTx() {
			return tx;
		}

		private EntityManager getEm() {
			return em;
		}
}
