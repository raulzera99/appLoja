package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.ItemPedidoPKDAO;
import models.ItemPedidoPK;
import persistence.DataBaseConnection;

public class ItemPedidoPKService {
	//Attributes
		@PersistenceContext(unitName = "apploja")
		private final EntityManager em;
		
		private ItemPedidoPKDAO dao;
		
		private EntityTransaction tx;
		
		//Constructors
		public ItemPedidoPKService() {
			em = DataBaseConnection.getConnection().getEntityManager();
			dao = new ItemPedidoPKDAO(em);
		}
		
		//Methods
		public void addItemPedidoPK(ItemPedidoPK ItemPedidoPK) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().addItemPedidoPK(ItemPedidoPK);
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
		
		public ItemPedidoPK updateItemPedidoPK(ItemPedidoPK ItemPedidoPK) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				ItemPedidoPK ItemPedidoPKAtual = getDao().updateItemPedidoPK(ItemPedidoPK);
				getTx().commit();
				return ItemPedidoPKAtual;
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
		
		public void removeItemPedidoPK(Long id) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().removeItemPedidoPK(id);
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
		
		public ItemPedidoPK searchItemPedidoPKById(Long id) {
			ItemPedidoPK ItemPedidoPK = new ItemPedidoPK();
			ItemPedidoPK = dao.searchItemPedidoPKById(id);
			return ItemPedidoPK;
		}
		
		public List<ItemPedidoPK> listAllItemPedidoPKs(){
			return dao.listAllItensPedidosPK();
		}
		
		private ItemPedidoPKDAO getDao() {
			return dao;
		}

		private EntityTransaction getTx() {
			return tx;
		}

		private EntityManager getEm() {
			return em;
		}
}
