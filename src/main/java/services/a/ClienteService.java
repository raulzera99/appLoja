package services.a;

import config.Page;
import dao.ClienteDAO;
import models.Cliente;

public class ClienteService extends DataBaseTransactionService<Cliente, Long>{
	
	private ClienteDAO dao;
	
	

	public ClienteService() {
		dao = new ClienteDAO(openEntityManager());
	}

	@Override
	public void add(Cliente entity) {
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
	public Cliente update(Cliente entity) {
		Cliente cliente = null;
		try {
			beginTransaction();
			cliente = dao.update(entity);
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
		return cliente;
	}

	@Override
	public void remove(Cliente entity) {
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
	public Cliente findById(Long id) {
		Cliente cliente = new Cliente();
		cliente = dao.searchById(id);
		return cliente;
	}

	@Override
	public Page<Cliente> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Cliente> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}
