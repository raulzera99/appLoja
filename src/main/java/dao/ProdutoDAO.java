package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Produto;

public class ProdutoDAO extends GenericDAO<Produto, Long>{

	public ProdutoDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Produto> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Produto> lista = new ArrayList<Produto>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Produto> query = getEntityManager()
				.createQuery("SELECT c FROM Produto c "
						+ "WHERE c.nome "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.preco "
						+ "LIKE (CONCAT('%',:text,'%')) ", Produto.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}

