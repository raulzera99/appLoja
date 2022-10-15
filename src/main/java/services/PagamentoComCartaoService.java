package services;

import config.Page;
import dao.PagamentoComCartaoDAO;
import models.PagamentoComCartao;

public class PagamentoComCartaoService extends DataBaseTransactionService<PagamentoComCartao, Long>{
	
	private PagamentoComCartaoDAO dao;
	
	

	public PagamentoComCartaoService() {
		dao = new PagamentoComCartaoDAO(openEntityManager());
	}

	@Override
	public void add(PagamentoComCartao entity) {
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
	public PagamentoComCartao update(PagamentoComCartao entity) {
		PagamentoComCartao pagamentoCartao = null;
		try {
			beginTransaction();
			pagamentoCartao = dao.update(entity);
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
		return pagamentoCartao;
	}

	@Override
	public void remove(PagamentoComCartao entity) {
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
	public PagamentoComCartao findById(Long id) {
		PagamentoComCartao pagamentoCartao = new PagamentoComCartao();
		pagamentoCartao = dao.searchById(id);
		return pagamentoCartao;
	}

	@Override
	public Page<PagamentoComCartao> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<PagamentoComCartao> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}