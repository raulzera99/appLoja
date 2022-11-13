
package services;

import java.util.ArrayList;
import java.util.List;

import config.Page;
import dao.EstadoDAO;
import message.Response;
import models.Estado;
import services.errors.ErrorsData;
import services.errors.ValidationRequiredField;

public class EstadoService extends DataBaseTransactionService<Estado, Long>{
	
	private EstadoDAO dao;
	
	private Response response = null;

	public EstadoService() {
		dao = new EstadoDAO(openEntityManager());
	}

	@Override
	public Response add(Estado entity) {
		try {
			beginTransaction();
			dao.add(entity);
			commitTransaction();
			response = getMessageResponse().message(entity, "Adicionado com êxito", false);
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

	@Override
	public Response update(Estado entity) {
		try {
			beginTransaction();
			dao.update(entity);
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

	@Override
	public Response remove(Estado entity) {
		try {
			beginTransaction();
			dao.remove(entity);
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

	@Override
	public Response findById(Long id) {
		Estado estado= null;
		try {
			estado = dao.searchById(id);
			response = getMessageResponse().message(estado, "Encontrado com êxito !", false);	
		} catch (Exception e) {
			e.printStackTrace();
			response = getMessageResponse().message(estado, e.getMessage(), true);	
		} finally {
			closeEntityManager();
		}
		return response;
	}

	@Override
	public Page<Estado> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Estado> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}
	
	public String[] listAllEstados() {
		List<Estado> estados = dao.listAll();
		String[] results = new String[estados.size()];
		int i = 0;
		for (Estado x : estados) {
			results[i] = x.getNome();
			i++;
		}
		return results;
	}

	@Override
	public Response validarDadosFromView(Estado objeto) {
		errorsData = new ArrayList<ErrorsData>();
		errorsData = ValidationRequiredField.validarCampoRequerido(objeto);
		
		return returnErrorOrNot();
	}
}
