package services.a;

import config.Page;
import dao.CodigoDAO;
import models.Codigo;

public class CodigoService extends DataBaseTransactionService<Codigo, Long>{
	
	private CodigoDAO dao;
	
	

	public CodigoService() {
		dao = new CodigoDAO(openEntityManager());
	}

	@Override
	public void add(Codigo entity) {
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
	public Codigo update(Codigo entity) {
		Codigo codigo = null;
		try {
			beginTransaction();
			codigo = dao.update(entity);
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
		return codigo;
	}

	@Override
	public void remove(Codigo entity) {
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
	public Codigo findById(Long id) {
		Codigo codigo = new Codigo();
		codigo = dao.searchById(id);
		return codigo;
	}

	@Override
	public Page<Codigo> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Codigo> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}
