package services;

import java.util.List;

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

	public String[] stringListAllCodigos() {
		List<Codigo> codigos = dao.listAll();
		String[] results = new String[codigos.size()];
		int i = 0;
		for (Codigo x : codigos) {
			results[i] = x.getNumero();
			i++;
		}
		return results;
	}

}
