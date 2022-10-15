package services;

import config.Page;
import dao.PedidoDAO;
import models.Pedido;

public class PedidoService extends DataBaseTransactionService<Pedido, Long>{
	
	private PedidoDAO dao;
	
	

	public PedidoService() {
		dao = new PedidoDAO(openEntityManager());
	}

	@Override
	public void add(Pedido entity) {
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
	public Pedido update(Pedido entity) {
		Pedido pedido = null;
		try {
			beginTransaction();
			pedido = dao.update(entity);
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
		return pedido;
	}

	@Override
	public void remove(Pedido entity) {
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
	public Pedido findById(Long id) {
		Pedido pedido = new Pedido();
		pedido = dao.searchById(id);
		return pedido;
	}

	@Override
	public Page<Pedido> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Pedido> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}