package services;

import javax.persistence.EntityManager;

import config.Page;
import dao.TelefoneDAO;
import models.Telefone;

public class TelefoneService extends DataBaseTransactionService<Telefone, Long>{
	
	private TelefoneDAO dao;
	
	public TelefoneService(EntityManager em, TelefoneDAO dao) {
		super(em,dao);
		this.dao = dao;
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