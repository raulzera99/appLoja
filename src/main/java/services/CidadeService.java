package services;


import java.util.List;

import javax.persistence.EntityManager;

import config.Page;
import dao.CidadeDAO;
import models.Cidade;

public class CidadeService extends DataBaseTransactionService<Cidade, Long>{
	
	private CidadeDAO dao;
	

	public CidadeService(EntityManager em, CidadeDAO dao) {
		super(em, dao);
		this.dao = dao;
	}

	@Override
	public Page<Cidade> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Cidade> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	public String[] stringListAllCidades() {
		List<Cidade> cidades = dao.listAll();
		String[] results = new String[cidades.size()];
		int i = 0;
		for (Cidade x : cidades) {
			results[i] = x.getNome();
			i++;
		}
		return results;
	}
}
