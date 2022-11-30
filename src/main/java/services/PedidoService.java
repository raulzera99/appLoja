package services;

import java.util.List;

import javax.persistence.EntityManager;

import config.Page;
import dao.PedidoDAO;
import models.Pedido;

public class PedidoService extends DataBaseTransactionService<Pedido, Long>{
	
	private PedidoDAO dao;
	
	public PedidoService(EntityManager em, PedidoDAO dao) {
		super(em,dao);
		this.dao = dao;
	}

	@Override
	public Page<Pedido> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Pedido> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	public String[] stringListAllPedidos() {
		List<Pedido> pedidos = dao.listAll();
		String[] results = new String[pedidos.size()];
		int i = 0;
		for (Pedido x : pedidos) {
			results[i] = x.getDescricao();
			i++;
		}
		return results;
	}
}