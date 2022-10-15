package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.ItemPedido;

public class ItemPedidoDAO extends GenericDAO<ItemPedido, Long>{

	public ItemPedidoDAO(EntityManager em) {
		super(em);
	}
	

	public Page<ItemPedido> listaPaginada(Integer page, Integer pageSize, String text) {
		List<ItemPedido> lista = new ArrayList<ItemPedido>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<ItemPedido> query = getEntityManager()
				.createQuery("SELECT c FROM ItemPedido c "
						+ "WHERE c.desconto "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.preco "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.quantidade "
						+ "LIKE (CONCAT('%',:text,'%')) ", ItemPedido.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
