package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

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
	
	
	public Produto searchByCodigo(String name) {
		Produto entity = null;
		
		TypedQuery<Produto> query = getEntityManager().createQuery("SELECT u FROM Produto u where u.codigo=:name", getClasse());
		query.setParameter("name", name);
		entity = query.getResultList().get(0);
		
		return entity; 
	}

}

