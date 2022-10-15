package services;

import config.Page;
import dao.ProdutoDAO;
import models.Produto;

public class ProdutoService extends DataBaseTransactionService<Produto, Long>{
	
	private ProdutoDAO dao;
	
	

	public ProdutoService() {
		dao = new ProdutoDAO(openEntityManager());
	}

	@Override
	public void add(Produto entity) {
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
	public Produto update(Produto entity) {
		Produto produto = null;
		try {
			beginTransaction();
			produto = dao.update(entity);
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
		return produto;
	}

	@Override
	public void remove(Produto entity) {
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
	public Produto findById(Long id) {
		Produto produto = new Produto();
		produto = dao.searchById(id);
		return produto;
	}

	@Override
	public Page<Produto> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Produto> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}