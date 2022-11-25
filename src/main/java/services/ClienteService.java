package services;


import java.util.List;

import javax.persistence.EntityManager;

import config.Page;
import dao.ClienteDAO;
import models.Cliente;

public class ClienteService extends DataBaseTransactionService<Cliente, Long>{
	
	private ClienteDAO dao;
	
	public ClienteService(EntityManager em, ClienteDAO dao) {
		super(em, dao);
		this.dao = dao;
	}

	@Override
	public Page<Cliente> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Cliente> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	public String[] stringListAllClientes() {
		List<Cliente> clientes = dao.listAll();
		String[] results = new String[clientes.size()];
		int i = 0;
		for (Cliente x : clientes) {
			results[i] = x.getNome();
			i++;
		}
		return results;
	}

}
