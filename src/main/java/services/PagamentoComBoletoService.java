package services;

import javax.persistence.EntityManager;

import config.Page;
import dao.PagamentoComBoletoDAO;
import models.PagamentoComBoleto;

public class PagamentoComBoletoService extends DataBaseTransactionService<PagamentoComBoleto, Long>{
	
	private PagamentoComBoletoDAO dao;
	

	public PagamentoComBoletoService(EntityManager em, PagamentoComBoletoDAO dao) {
		super(em, dao);
		this.dao = dao;
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