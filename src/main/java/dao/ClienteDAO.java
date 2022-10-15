package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Cliente;

public class ClienteDAO extends GenericDAO<Cliente, Long>{

	public ClienteDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Cliente> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Cliente> lista = new ArrayList<Cliente>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Cliente> query = getEntityManager()
				.createQuery("SELECT c FROM Cliente c "
						+ "WHERE c.nome "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.email "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.tipo "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.cpfOuCnpj "
						+ "LIKE (CONCAT('%',:text,'%')) ", Cliente.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
