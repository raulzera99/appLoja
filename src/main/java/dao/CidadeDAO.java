package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Cidade;

public class CidadeDAO {
	private EntityManager em;
	
	public CidadeDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addCidade(Cidade cidade) {
		getEm().persist(cidade);
	}
	
	public Cidade updateCidade(Cidade cidade) {
		return getEm().merge(cidade);
	}
	
	public Cidade searchCidadeById(Long id) {
		Cidade cidadeBuscada = new Cidade();
		cidadeBuscada = getEm().find(Cidade.class, id);
		return cidadeBuscada;
	}
	
	public void removeCidadeById(Long id) {
		Cidade cidade = searchCidadeById(id);
		getEm().remove(cidade);
	}
	
	public List<Cidade> listAllCidades(){
		List<Cidade> cidades = new ArrayList<Cidade>();
		
		TypedQuery<Cidade> query = getEm()
				.createQuery("SELECT cidade FROM Cidade cidade", Cidade.class);
		
		cidades = query.getResultList();
		
		return cidades;
	}
	
	public List<Cidade> listCidadeByNome(String nome){
		List<Cidade> cidades = new ArrayList<Cidade>();
		
		TypedQuery<Cidade> query = getEm()
				.createQuery("SELECT cidade FROM Cidade cidade WHERE cidade.nome =:nome", Cidade.class);
		
		query.setParameter("nome", nome);
		
		cidades = query.getResultList();
		
		return cidades;
	}
}
