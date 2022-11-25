package services;

import java.util.List;

import javax.persistence.EntityManager;

import config.Page;
import dao.ProdutoDAO;
import models.Produto;

public class ProdutoService extends DataBaseTransactionService<Produto, Long>{
	
	private ProdutoDAO dao;
	
	public ProdutoService(EntityManager em, ProdutoDAO dao) {
		super(em, dao);
		this.dao = dao;
	}

	@Override
	public Page<Produto> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Produto> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	public String[] stringListAllProdutos() {
		List<Produto> produtos = dao.listAll();
		String[] results = new String[produtos.size()];
		int i = 0;
		for (Produto x : produtos) {
			results[i] = x.getNome();
			i++;
		}
		return results;
	}
}