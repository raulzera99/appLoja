package services;

import config.Page;
import dao.ItemPedidoDAO;
import models.ItemPedido;

public class ItemPedidoService extends DataBaseTransactionService<ItemPedido, Long>{
	
	private ItemPedidoDAO dao;
	
	

	public ItemPedidoService() {
		dao = new ItemPedidoDAO(openEntityManager());
	}

	@Override
	public void add(ItemPedido entity) {
		try {
			beginTransaction();
			dao.add(entity);
			commitTransaction();
		}
		catch(Exception e) {
			e.printStackTrace();
			if(isActiveTransaction()) {
				rollbackTransaction();
			}
		}finally {
			closeEntityManager();
		}
	}

	@Override
	public ItemPedido update(ItemPedido entity) {
		ItemPedido itemPedido = null;
		try {
			beginTransaction();
			itemPedido = dao.update(entity);
			commitTransaction();
		}
		catch(Exception e) {
			e.printStackTrace();
			if(isActiveTransaction()) {
				rollbackTransaction();
			}
		}finally {
			closeEntityManager();
		}
		return itemPedido;
	}

	@Override
	public void remove(ItemPedido entity) {
		try {
			beginTransaction();
			dao.remove(entity);
			commitTransaction();
		}
		catch(Exception e) {
			e.printStackTrace();
			if(isActiveTransaction()) {
				rollbackTransaction();
			}
		}finally {
			closeEntityManager();
		}
	}

	@Override
	public ItemPedido findById(Long id) {
		ItemPedido itemPedido = new ItemPedido();
		itemPedido = dao.searchById(id);
		return itemPedido;
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