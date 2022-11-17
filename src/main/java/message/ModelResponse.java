package message;

import java.util.ArrayList;
import java.util.List;

public class ModelResponse<T> extends Response {
	
	private boolean error;
	
	private String message;
	
	private T object = null;
	
	private List<T> listObject = new ArrayList<T>();
	
	public ModelResponse() {
	}

	public ModelResponse(List<T> listObject, String message, boolean error) {
		this.error = error;
		this.message = message;
		this.listObject = listObject;
	}

	public ModelResponse(String message, T object, boolean error) {
		this.error = error;
		this.message = message;
		this.object = object;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public boolean isError() {
		return error;
	}
	
	public T getObject() {
		return object;
	}
	
	public void setObject(T object) {
		this.object = object;
	}
	
	public List<T> getListObject() {
		return listObject;
	}
	
	public void setListObject(List<T> listObject) {
		this.listObject = listObject;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
