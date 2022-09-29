package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.ItemPedidoPK;

public class ItemPedidoPKDAO {
	private EntityManager em;
	
	public ItemPedidoPKDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addItemPedidoPK(ItemPedidoPK itemPedidoPK) {
		getEm().persist(itemPedidoPK);
	}
	
	public ItemPedidoPK updateItemPedidoPK(ItemPedidoPK itemPedidoPK) {
		return getEm().merge(itemPedidoPK);
	}
	
	public ItemPedidoPK searchItemPedidoPKById(Long id) {
		ItemPedidoPK itemPedidoPKBuscado = new ItemPedidoPK();
		itemPedidoPKBuscado = getEm().find(ItemPedidoPK.class, id);
		return itemPedidoPKBuscado;
	}
	
	public void removeItemPedidoPK(Long id) {
		ItemPedidoPK itemPedidoPK = searchItemPedidoPKById(id);
		getEm().remove(itemPedidoPK);
	}
	
	public List<ItemPedidoPK> listAllItensPedidosPK(){
		List<ItemPedidoPK> itensPedidosPK = new ArrayList<ItemPedidoPK>();
		
		TypedQuery<ItemPedidoPK> query = getEm()
				.createQuery("SELECT itemPedidoPK FROM ItemPedidoPK itemPedidoPK", ItemPedidoPK.class);
		
		itensPedidosPK = query.getResultList();
		
		return itensPedidosPK;
	} 
}
