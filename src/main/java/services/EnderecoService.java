package services;


import javax.persistence.EntityManager;

import config.Page;
import dao.EnderecoDAO;
import models.Endereco;

public class EnderecoService extends DataBaseTransactionService<Endereco, Long>{
	
	private EnderecoDAO dao;
	
	public EnderecoService(EntityManager em, EnderecoDAO dao) {
		super(em, dao);
		this.dao = dao;
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
