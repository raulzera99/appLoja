package services;

import config.Page;
import dao.CidadeDAO;
import models.Cidade;

public class CidadeService extends DataBaseTransactionService<Cidade, Long>{
	
	private CidadeDAO dao;
	
	

	public CidadeService() {
		dao = new CidadeDAO(openEntityManager());
	}

	@Override
	public void add(Cidade entity) {
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
	public Cidade update(Cidade entity) {
		Cidade cidade = null;
		try {
			beginTransaction();
			cidade = dao.update(entity);
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
		return cidade;
	}

	@Override
	public void remove(Cidade entity) {
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
	public Cidade findById(Long id) {
		Cidade cidade = new Cidade();
		cidade = dao.searchById(id);
		return cidade;
	}

	@Override
	public Page<Cidade> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Cidade> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
	
}
