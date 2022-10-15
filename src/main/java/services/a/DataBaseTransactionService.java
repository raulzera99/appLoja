package services.a;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import config.Page;
import persistence.DataBaseConnection;

public abstract class DataBaseTransactionService<T, ID> {
	
	@PersistenceContext(unitName = "apploja")
	private EntityManager em;
	
	private EntityTransaction transaction;
	
	public EntityManager openEntityManager() {
		if(Objects.isNull(em)) {
			em = DataBaseConnection.getConnection().getEntityManager();
		}
		return em;
	}
	
	public void beginTransaction() {
		em.getTransaction().begin();
	}
	
	public void commitTransaction() {
		em.getTransaction().commit();
	}
	
	public boolean isActiveTransaction() {
		return em.getTransaction().isActive();
	}
	
	public void rollbackTransaction() {
		em.getTransaction().rollback();
	}
	
	public void closeEntityManager() {
		em.close();
	}
	
	public abstract void add(T entity);
	
	public abstract T update(T entity);
	
	public abstract void remove(T entity);
	
	public abstract T findById(ID id);
	
	public abstract Page<T> listaPaginada(Integer page, Integer pageSize);
	
	public abstract Page<T> listaPaginada(Integer page, Integer pageSize, String text);

	public EntityTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(EntityTransaction transaction) {
		this.transaction = transaction;
	}
	
	
	
}
