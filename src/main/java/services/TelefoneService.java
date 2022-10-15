package services;

import config.Page;
import dao.TelefoneDAO;
import models.Telefone;

public class TelefoneService extends DataBaseTransactionService<Telefone, Long>{
	
	private TelefoneDAO dao;
	
	

	public TelefoneService() {
		dao = new TelefoneDAO(openEntityManager());
	}

	@Override
	public void add(Telefone entity) {
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
	public Telefone update(Telefone entity) {
		Telefone telefone = null;
		try {
			beginTransaction();
			telefone = dao.update(entity);
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
		return telefone;
	}

	@Override
	public void remove(Telefone entity) {
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
	public Telefone findById(Long id) {
		Telefone telefone = new Telefone();
		telefone = dao.searchById(id);
		return telefone;
	}

	@Override
	public Page<Telefone> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Telefone> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}