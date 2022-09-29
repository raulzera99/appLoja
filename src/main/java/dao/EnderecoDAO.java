package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Endereco;

public class EnderecoDAO {
	private EntityManager em;
	
	public EnderecoDAO(EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addEndereco(Endereco endereco) {
		getEm().persist(endereco);
	}
	
	public Endereco updateEndereco (Endereco endereco) {
		return getEm().merge(endereco);
	}
	
	public Endereco searchEnderecoById(Long id){
		Endereco enderecoBuscado = new Endereco();
		enderecoBuscado = getEm().find(Endereco.class, id);
		return enderecoBuscado;
	}
	
	public void removeEnderecoById(Long id) {
		Endereco endereco = searchEnderecoById(id);
		getEm().remove(endereco);
	}
	
	public List<Endereco> listAllEnderecos (){
		List<Endereco> enderecos = new ArrayList<Endereco>();
		
		TypedQuery<Endereco> query = getEm()
				.createQuery("SELECT endereco FROM Endereco endereco", Endereco.class);
		
		enderecos = query.getResultList();
		
		return enderecos;
	}
	
}
