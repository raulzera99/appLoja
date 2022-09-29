package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.ItemPedidoDAO;
import models.ItemPedido;
import persistence.DataBaseConnection;

public class ItemPedidoService {
	//Attributes
		@PersistenceContext(unitName = "apploja")
		private final EntityManager em;
		
		private ItemPedidoDAO dao;
		
		private EntityTransaction tx;
		
		//Constructors
		public ItemPedidoService() {
			em = DataBaseConnection.getConnection().getEntityManager();
			dao = new ItemPedidoDAO(em);
		}
		
		//Methods
		public void addItemPedido(ItemPedido ItemPedido) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().addItemPedido(ItemPedido);
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
		
		public ItemPedido updateItemPedido(ItemPedido ItemPedido) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				ItemPedido ItemPedidoAtual = getDao().updateItemPedido(ItemPedido);
				getTx().commit();
				return ItemPedidoAtual;
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
		
		public void removeItemPedido(Long id) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().removeItemPedidoById(id);
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
		
		public ItemPedido searchItemPedidoById(Long id) {
			ItemPedido ItemPedido = new ItemPedido();
			ItemPedido = dao.searchItemPedidoById(id);
			return ItemPedido;
		}
		
		public List<ItemPedido> listAllItemPedidos(){
			return dao.listAllItensPedidos();
		}
		
		private ItemPedidoDAO getDao() {
			return dao;
		}

		private EntityTransaction getTx() {
			return tx;
		}

		private EntityManager getEm() {
			return em;
		}
}
