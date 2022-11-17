
package services;

import java.util.List;

import javax.persistence.EntityManager;

import config.Page;
import dao.EstadoDAO;
import models.Estado;

public class EstadoService extends DataBaseTransactionService<Estado, Long>{
	
	private EstadoDAO dao;
	
	public EstadoService(EntityManager em, EstadoDAO dao) {
		super(em, dao);
		this.dao = dao; 
	}

	@Override
	public Page<Estado> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Estado> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
	
	public String[] listAllEstados() {
		List<Estado> estados = dao.listAll();
		String[] results = new String[estados.size()];
		int i = 0;
		for (Estado x : estados) {
			results[i] = x.getNome();
			i++;
		}
		return results;
	}


}
