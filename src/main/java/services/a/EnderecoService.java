package services.a;

import config.Page;
import dao.EnderecoDAO;
import models.Endereco;

public class EnderecoService extends DataBaseTransactionService<Endereco, Long>{
	
	private EnderecoDAO dao;
	
	

	public EnderecoService() {
		dao = new EnderecoDAO(openEntityManager());
	}

	@Override
	public void add(Endereco entity) {
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
	public Endereco update(Endereco entity) {
		Endereco endereco = null;
		try {
			beginTransaction();
			endereco = dao.update(entity);
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
		return endereco;
	}

	@Override
	public void remove(Endereco entity) {
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
	public Endereco findById(Long id) {
		Endereco endereco = new Endereco();
		endereco = dao.searchById(id);
		return endereco;
	}

	@Override
	public Page<Endereco> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Endereco> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}
