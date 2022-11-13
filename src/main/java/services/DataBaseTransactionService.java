package services;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import config.Page;
import message.MessageResponse;
import message.ModelResponse;
import message.Response;
import persistence.DataBaseConnection;
import services.errors.ErrorsData;

public abstract class DataBaseTransactionService<T, ID> {
	
	@PersistenceContext(unitName = "apploja")
	private EntityManager em;
	
	protected List<ErrorsData> errorsData;
	
	protected Response response;
	
	protected MessageResponse<ErrorsData> errorData;
	protected MessageResponse<T> messageResponse;
	
	protected ModelResponse<T> modelResponse;
	
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
	
	public abstract Response add(T entity);
	
	public abstract Response update(T entity);
	
	public abstract Response remove(T entity);
	
	public abstract Response findById(ID id);
	
	public abstract Page<T> listaPaginada(Integer page, Integer pageSize);
	
	public abstract Page<T> listaPaginada(Integer page, Integer pageSize, String text);

	public abstract Response validarDadosFromView(T objeto );
	
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
