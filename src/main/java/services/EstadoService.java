
package services;

import java.util.List;

import config.Page;
import dao.EstadoDAO;
import models.Estado;

public class EstadoService extends DataBaseTransactionService<Estado, Long>{
	
	private EstadoDAO dao;
	
	

	public EstadoService() {
		dao = new EstadoDAO(openEntityManager());
	}

	@Override
	public void add(Estado entity) {
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
	public Estado update(Estado entity) {
		Estado estado = null;
		try {
			beginTransaction();
			estado = dao.update(entity);
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
		return estado;
	}

	@Override
	public void remove(Estado entity) {
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
	public Estado findById(Long id) {
		Estado estado = new Estado();
		estado = dao.searchById(id);
		return estado;
	}

	@Override
	public Page<Estado> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Estado> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
	
	public String[] listAllEstados() {
		List<Estado> estados = dao.listAll();
		String[] results = new String[estados.size()];
		int i = 0;
		for (Estado x : estados) {
			results[i] = x.getNome();
			i++;
		}
		return results;
	}
}
