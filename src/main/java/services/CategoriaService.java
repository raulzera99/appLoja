package services;

import javax.persistence.EntityManager;

import config.Page;
import dao.CategoriaDAO;
import models.Categoria;

public class CategoriaService extends DataBaseTransactionService<Categoria, Long>{
	
	private CategoriaDAO dao;
	
	public CategoriaService(EntityManager em, CategoriaDAO dao) {
		super(em, dao);
		this.dao = dao;
	}

	@Override
	public Page<Categoria> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Categoria> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
}