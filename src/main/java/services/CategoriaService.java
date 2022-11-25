package services;

import java.util.List;

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

	public String[] stringListAllCategorias() {
		List<Categoria> categorias = dao.listAll();
		String[] results = new String[categorias.size()];
		int i = 0;
		for (Categoria x : categorias) {
			results[i] = x.getNome();
			i++;
		}
		return results;
	}
}