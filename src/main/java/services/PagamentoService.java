package services;

import config.Page;
import dao.PagamentoDAO;
import models.Pagamento;

public class PagamentoService extends DataBaseTransactionService<Pagamento, Long>{
	
	private PagamentoDAO dao;
	
	

	public PagamentoService() {
		dao = new PagamentoDAO(openEntityManager());
	}

	@Override
	public void add(Pagamento entity) {
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
	public Pagamento update(Pagamento entity) {
		Pagamento pagamento = null;
		try {
			beginTransaction();
			pagamento = dao.update(entity);
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
		return pagamento;
	}

	@Override
	public void remove(Pagamento entity) {
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
	public Pagamento findById(Long id) {
		Pagamento pagamento = new Pagamento();
		pagamento = dao.searchById(id);
		return pagamento;
	}

	@Override
	public Page<Pagamento> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Pagamento> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}