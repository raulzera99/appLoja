package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.PedidoDAO;
import models.Pedido;
import persistence.DataBaseConnection;

public class PedidoService {
	//Attributes
		@PersistenceContext(unitName = "apploja")
		private final EntityManager em;
		
		private PedidoDAO dao;
		
		private EntityTransaction tx;
		
		//Constructors
		public PedidoService() {
			em = DataBaseConnection.getConnection().getEntityManager();
			dao = new PedidoDAO(em);
		}
		
		//Methods
		public void addPedido(Pedido Pedido) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().addPedido(Pedido);
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
		
		public Pedido updatePedido(Pedido Pedido) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				Pedido PedidoAtual = getDao().updatePedido(Pedido);
				getTx().commit();
				return PedidoAtual;
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
		
		public void removePedido(Long id) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().removePedidoById(id);
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
		
		public Pedido searchPedidoById(Long id) {
			Pedido Pedido = new Pedido();
			Pedido = dao.searchPedidoById(id);
			return Pedido;
		}
		
		public List<Pedido> listAllPedidos(){
			return dao.listAllPedidos();
		}
		
		private PedidoDAO getDao() {
			return dao;
		}

		private EntityTransaction getTx() {
			return tx;
		}

		private EntityManager getEm() {
			return em;
		}
}
