package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Produto;

public class ProdutoDAO {
private EntityManager em;
	
	public ProdutoDAO (EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void addProduto(Produto produto) {
		getEm().persist(produto);
	}
	
	public Produto updateProduto(Produto produto) {
		return getEm().merge(produto);
	}
	
	public Produto searchProdutoById(Long id) {
		Produto produtoBuscado = new Produto();
		produtoBuscado = getEm().find(Produto.class, id);
		return produtoBuscado;
	}
	
	public void removeProdutoById(Long id) {
		Produto produto = searchProdutoById(id);
		getEm().remove(produto);
	}
	
	public List<Produto> listAllProdutos(){
		List<Produto> produtos = new ArrayList<Produto>();
		TypedQuery<Produto> query = getEm().createQuery("SELECT p FROM Produto p", Produto.class);
		produtos = query.getResultList();
		
		return produtos;
	}
	
	public List<Produto> listProdutoByNome(String nome){
		List<Produto> produtos = new ArrayList<Produto>();
		
		TypedQuery<Produto> query = getEm()
				.createQuery("SELECT p FROM Produto p "
						+ "WHERE Produto.nome =:nome", Produto.class);
		
		query.setParameter("nome", nome);
		
		produtos = query.getResultList();
		
		return produtos;
	}
}
