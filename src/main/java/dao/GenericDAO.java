package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.Page;

public class GenericDAO<T, ID extends Serializable>  {
	private Class<T> classe;
	private EntityManager entityManager;
	private Page<T> page;
	
	@SuppressWarnings("unchecked")
	public GenericDAO(EntityManager em) {
		this.entityManager = em;
		this.setClasse( (Class<T>)((ParameterizedType)this.getClass()
				.getGenericSuperclass())
				.getActualTypeArguments()[0]);
	}
	
	public void add(T entity) {
		getEntityManager().persist(entity);
	}
	
	public T update(T entity) {
		return getEntityManager().merge(entity);
	}
	
	public void remove(T entity) {
		getEntityManager().remove(entity);
	}
	
	public T searchById(ID id) {
		return (T)getEntityManager().find(getClasse(), id);
	}
	
	public void removeById(ID id) {
        T entity = searchById(id);
        getEntityManager().remove(entity);
    }
	
	
	public Page<T> listaPaginada(Integer page, Integer pageSize){
		List<T> lista = new ArrayList<T>();
		Long total = count();
		Integer paginaAtual = ((page-1)*pageSize);
		if(paginaAtual<0) {
			paginaAtual = 0;
		}
		Double totalPaginas = Math.ceil(total.doubleValue() / pageSize.doubleValue());
		TypedQuery<T> query = getEntityManager()
				.createQuery("SELECT o FROM "+getClasse().getSimpleName()+" o", getClasse());
		
		
		lista = query.setFirstResult(paginaAtual).setMaxResults(pageSize).getResultList();
		
		
		
		return getPaginas(lista, page, pageSize, totalPaginas.intValue(), total.intValue());
	}
	
	protected Long count() {
		TypedQuery<Long> query = getEntityManager()
				.createQuery("SELECT COUNT(o) FROM " + getClasse().getSimpleName() + " o", Long.class);
		Long total = query.getSingleResult();
		return total > 0L ? total:0L;
	}

	protected Page<T> getPaginas(List<T> lista, Integer page, Integer pageSize, Integer totalPaginas, Integer total ){
		Page<T> pagina = new Page<T>();
		pagina.setContent(lista);
		pagina.setPage(page);
		pagina.setPageSize(pageSize);
		pagina.setTotalPage(totalPaginas);
		pagina.setTotalRecords(total);
		return pagina;
	}
	
	public List<T> listAll(){
		TypedQuery<T> query = getEntityManager()
				.createQuery("SELECT o FROM "+getClasse().getSimpleName()+" o", getClasse());
		return query.getResultList();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Class<T> getClasse() {
		return classe;
	}

	public void setClasse(Class<T> classe) {
		this.classe = classe;
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}
	
}

