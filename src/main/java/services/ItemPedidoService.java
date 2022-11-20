package services;

import javax.persistence.EntityManager;

import config.Page;
import dao.ItemPedidoDAO;
import models.ItemPedido;

public class ItemPedidoService extends DataBaseTransactionService<ItemPedido, Long>{
	
	private ItemPedidoDAO dao;
	
	public ItemPedidoService(EntityManager em, ItemPedidoDAO dao) {
		super(em, dao);
		this.dao = dao;
	}
	
	@Override
	public Page<ItemPedido> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<ItemPedido> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}