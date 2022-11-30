package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;
import models.Endereco;

public class EnderecoDAO extends GenericDAO<Endereco, Long>{

	public EnderecoDAO(EntityManager em) {
		super(em);
	}
	

	public Page<Endereco> listaPaginada(Integer page, Integer pageSize, String text) {
		List<Endereco> lista = new ArrayList<Endereco>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<Endereco> query = getEntityManager()
				.createQuery("SELECT c FROM Endereco c "
						+ "WHERE c.bairro "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.cep "
						+ "LIKE (CONCAT('%',:text,'%')) "
						+ "OR c.numero "
						+ "LIKE (CONCAT('%',:text,'%')) "
						, Endereco.class);
		
		
		lista = query.setParameter("text", text)
				.setFirstResult(paginaAtual)
				.setMaxResults(pageSize)
				.getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}

}
