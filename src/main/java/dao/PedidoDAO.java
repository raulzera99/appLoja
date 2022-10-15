package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Pedido;

public class PedidoDAO extends GenericDAO<Pedido, Long>{

	public PedidoDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Pedido> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Pedido> lista = new ArrayList<Pedido>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Pedido> query = getEntityManager()
				.createQuery("SELECT c FROM Pedido c "
						+ "WHERE c.instante "
						+ "LIKE (CONCAT('%',:text,'%')) ", Pedido.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
