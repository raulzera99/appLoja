package services;

import config.Page;
import dao.CategoriaDAO;
import models.Categoria;

public class CategoriaService extends DataBaseTransactionService<Categoria, Long>{
	
	private CategoriaDAO dao;
	
	

	public CategoriaService() {
		dao = new CategoriaDAO(openEntityManager());
	}

	@Override
	public void add(Categoria entity) {
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
	public Categoria update(Categoria entity) {
		Categoria categoria = null;
		try {
			beginTransaction();
			categoria = dao.update(entity);
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
		return categoria;
	}

	@Override
	public void remove(Categoria entity) {
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
	public Categoria findById(Long id) {
		Categoria categoria = new Categoria();
		categoria = dao.searchById(id);
		return categoria;
	}

	@Override
	public Page<Categoria> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Categoria> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}