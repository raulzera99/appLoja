package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.ItemPedido;

public class ItemPedidoDAO {
	private EntityManager em;
	
	public ItemPedidoDAO (EntityManager em){
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addItemPedido(ItemPedido itemPedido) {
		getEm().persist(itemPedido);
	}
	
	public ItemPedido updateItemPedido(ItemPedido itemPedido) {
		return getEm().merge(itemPedido);
	}
	
	public ItemPedido searchItemPedidoById(Long id) {
		ItemPedido itemPedidoBuscado = new ItemPedido();
		itemPedidoBuscado = getEm().find(ItemPedido.class, id);	
		return itemPedidoBuscado;
	}
	
	public void removeItemPedidoById(Long id) {
		ItemPedido itemPedido = searchItemPedidoById(id);
		getEm().remove(itemPedido);
	}
	
	public List<ItemPedido> listAllItensPedidos(){
		List<ItemPedido> itensPedidos = new ArrayList<ItemPedido>();
		
		TypedQuery<ItemPedido> query = getEm()
				.createQuery("SELECT itemPedido "
						+ "FROM ItemPedido itemPedido", ItemPedido.class);
		
		itensPedidos = query.getResultList();
		
		return itensPedidos;
	}
}
