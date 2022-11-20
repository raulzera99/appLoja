package services;

import javax.persistence.EntityManager;

import config.Page;
import dao.PagamentoDAO;
import models.Pagamento;

public class PagamentoService extends DataBaseTransactionService<Pagamento, Long>{
	
	private PagamentoDAO dao;
	
	public PagamentoService(EntityManager em, PagamentoDAO dao) {
		super(em, dao);
		this.dao = dao;
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