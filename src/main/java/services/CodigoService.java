package services;

import javax.persistence.EntityManager;

import config.Page;
import dao.CodigoDAO;
import models.Codigo;

public class CodigoService extends DataBaseTransactionService<Codigo, Long>{
	
	private CodigoDAO dao;
	
	public CodigoService(EntityManager em, CodigoDAO dao) {
		super(em, dao);
		this.dao = dao;
	}

	@Override
	public Page<Codigo> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Codigo> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

}
