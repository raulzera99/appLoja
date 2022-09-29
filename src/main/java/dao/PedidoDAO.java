package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Pedido;

public class PedidoDAO {
	private EntityManager em;
	
	public PedidoDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm(){		
		return em;
	}
	
	public void addPedido(Pedido pedido) {
		getEm().persist(pedido);
	}
	
	public Pedido updatePedido (Pedido pedido) {
		return getEm().merge(pedido);
	}
	
	public Pedido searchPedidoById(Long id) {
		Pedido pedidoBuscado = new Pedido();
		pedidoBuscado = getEm().find(Pedido.class, id);
		return pedidoBuscado;
	}
	
	public void removePedidoById(Long id) {
		Pedido pedido = searchPedidoById(id);
		getEm().remove(pedido);
	}
	
	public List<Pedido> listAllPedidos(){
		List<Pedido> pedidos = new ArrayList<Pedido>();
		TypedQuery<Pedido> query = getEm()
				.createQuery("SELECT p FROM Pedido p", Pedido.class);
		
		pedidos = query.getResultList();
		
		return pedidos;
	}
	
}
