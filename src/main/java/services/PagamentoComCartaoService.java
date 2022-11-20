package services;

import javax.persistence.EntityManager;

import config.Page;
import dao.PagamentoComCartaoDAO;
import models.PagamentoComCartao;

public class PagamentoComCartaoService extends DataBaseTransactionService<PagamentoComCartao, Long>{
	
	private PagamentoComCartaoDAO dao;
	
	public PagamentoComCartaoService(EntityManager em, PagamentoComCartaoDAO dao) {
		super(em, dao);
		this.dao = dao;
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