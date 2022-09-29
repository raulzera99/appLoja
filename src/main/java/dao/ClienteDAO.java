package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Cliente;

public class ClienteDAO {
	private EntityManager em;
	
	public ClienteDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addCliente(Cliente cliente) {
		getEm().persist(cliente);
	}
	
	public Cliente updateCliente(Cliente cliente) {
		return getEm().merge(cliente);
	}
	
	public Cliente searchClienteById(Long id) {
		Cliente clienteBuscado = new Cliente();
		clienteBuscado = getEm().find(Cliente.class, id);
		
		return clienteBuscado;
	}
	
	public void removeClienteById(Long id) {
		Cliente cliente = searchClienteById(id);
		getEm().remove(cliente);
	}
	
	public List<Cliente> listAllClientes (){
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		TypedQuery<Cliente> query = getEm()
				.createQuery("SELECT cliente FROM Cliente cliente", Cliente.class);
		
		clientes = query.getResultList();
		
		return clientes;
	}
	
	public List<Cliente> listClienteByNome(String nome){
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		TypedQuery<Cliente> query = getEm()
				.createQuery("SELECT cliente FROM Cliente cliente WHERE cliente.nome =:nome", Cliente.class);
				
		query.setParameter("nome", nome);
		
		clientes = query.getResultList();
		
		return clientes;
	}
}
