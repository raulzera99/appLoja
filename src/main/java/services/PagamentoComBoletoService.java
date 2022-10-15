package services;

import config.Page;
import dao.PagamentoComBoletoDAO;
import models.PagamentoComBoleto;

public class PagamentoComBoletoService extends DataBaseTransactionService<PagamentoComBoleto, Long>{
	
	private PagamentoComBoletoDAO dao;
	
	

	public PagamentoComBoletoService() {
		dao = new PagamentoComBoletoDAO(openEntityManager());
	}

	@Override
	public void add(PagamentoComBoleto entity) {
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
	public PagamentoComBoleto update(PagamentoComBoleto entity) {
		PagamentoComBoleto pagamentoBoleto = null;
		try {
			beginTransaction();
			pagamentoBoleto = dao.update(entity);
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
		return pagamentoBoleto;
	}

	@Override
	public void remove(PagamentoComBoleto entity) {
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
	public PagamentoComBoleto findById(Long id) {
		PagamentoComBoleto pagamentoBoleto = new PagamentoComBoleto();
		pagamentoBoleto = dao.searchById(id);
		return pagamentoBoleto;
	}

	@Override
	public Page<PagamentoComBoleto> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<PagamentoComBoleto> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}