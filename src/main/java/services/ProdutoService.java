package services;

import java.util.List;

import javax.persistence.EntityManager;

import config.Page;
import dao.ProdutoDAO;
import message.Response;
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
	
	public Response findByCodigo(String name) {
		openEntityManager();
		Produto entity = null;
		try {
			entity = dao.searchByCodigo(name);
			response = getMessageResponse().message(entity, "Encontrado com êxito !", false);	
		} catch (Exception e) {
			e.printStackTrace();
			response = getMessageResponse().message(entity, e.getMessage(), true);	
		} finally {
			closeEntityManager();
		}
		return response;
	}
}