package services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import config.Page;
import dao.GenericDAO;
import message.MessageResponse;
import message.ModelResponse;
import message.Response;
import persistence.DataBaseConnection;
import services.errors.ErrorsData;
import services.errors.ValidationRequiredField;

public abstract class DataBaseTransactionService<T, ID extends Serializable> {
	
	@PersistenceContext(unitName = "apploja")
	private EntityManager em;
	
	protected List<ErrorsData> errorsData;
	
	protected GenericDAO<T, ID> dao;
	
	protected Response response;
	
	protected MessageResponse<ErrorsData> errorData;
	protected MessageResponse<T> messageResponse;
	
	protected ModelResponse<T> modelResponse;
	
	public DataBaseTransactionService() {};
	
	public DataBaseTransactionService(EntityManager em, GenericDAO<T, ID> dao ) {
		this.em = em;
		this.dao = dao;
	};
	
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
		return getEm().getTransaction().isActive();
	}
	
	public void rollbackTransaction() {
		getEm().getTransaction().rollback();
	}
	
	public void closeEntityManager() {
		em.close();
	}
	
	public Response add(T entity) {
		openEntityManager();
		try {
			beginTransaction();
			getDao().add(entity);
			commitTransaction();
			this.response = getMessageResponse().message(entity, "Adicionado com êxito", false);
		}
		catch(Exception e) {
			e.printStackTrace();
			if(isActiveTransaction()) {
				rollbackTransaction();
			}
			this.response = getMessageResponse().message(entity, e.getMessage(), true);
		}finally {
			closeEntityManager();
		}
		return this.response;
	}
	
	public Response update(T entity) {
		openEntityManager();
		try {
			beginTransaction();
			getDao().update(entity);
			commitTransaction();
			response = getMessageResponse().message(entity, "Alterado com êxito", false);
		}
		catch(Exception e) {
			e.printStackTrace();
			if(isActiveTransaction()) {
				rollbackTransaction();
			}
			response = getMessageResponse().message(entity, e.getMessage(), true);
		}finally {
			closeEntityManager();
		}
		return response;
	}

	public Response remove(ID id) {
		T entity = getDao().searchById(id);
		openEntityManager();
		try {
			beginTransaction();
			getDao().removeById(id);
			commitTransaction();
			
			response = getMessageResponse().message(entity, "Removido com êxito", false);
		}
		catch(Exception e) {
			e.printStackTrace();
			if(isActiveTransaction()) {
				rollbackTransaction();
			}
			response = getMessageResponse().message(entity, e.getMessage(), true);
		}finally {
			closeEntityManager();
		}
		
		return response;
	}

	public Response findById(ID id) {
		openEntityManager();
		T entity = null;
		try {
			entity = getDao().searchById(id);
			response = getMessageResponse().message(entity, "Encontrado com êxito !", false);	
		} catch (Exception e) {
			e.printStackTrace();
			response = getMessageResponse().message(entity, e.getMessage(), true);	
		} finally {
			closeEntityManager();
		}
		return response;
	}
	
	public Page<T> listaPaginada(Integer page, Integer pageSize){
		return getDao().listaPaginada(page, pageSize);
	}
	
	public abstract Page<T> listaPaginada(Integer page, Integer pageSize, String text);

	public Response validarDadosFromView(T objeto) {
		errorsData = new ArrayList<ErrorsData>();
		errorsData = ValidationRequiredField.validarCampoRequerido(objeto);
		
		return returnErrorOrNot();
	}
	
	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public MessageResponse<T> getMessageResponse() {
		return new MessageResponse<T>();
	}

	public void setMessageResponse(MessageResponse<T> messageResponse) {
		this.messageResponse = messageResponse;
	}

	public ModelResponse<T> getModelResponse() {
		return new ModelResponse<T>();
	}

	public void setModelResponse(ModelResponse<T> modelResponse) {
		this.modelResponse = modelResponse;
	}
	
	public GenericDAO<T, ID> getDao() {
		return dao;
	}

	public void setDao(GenericDAO<T, ID> dao) {
		this.dao = dao;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Response returnErrorOrNot() {
		if(errorsData.size() > 0 ) {
			response = getErrorData().message(errorsData, "",true);
		}
		else {
			response = getErrorData().message(errorsData, "",false);
		}
		return response;
	}

	public MessageResponse<ErrorsData> getErrorData() {
		return new MessageResponse<ErrorsData>();
	}

	public void setErrorData(MessageResponse<ErrorsData> errorData) {
		this.errorData = errorData;
	}
	
	
	
}
