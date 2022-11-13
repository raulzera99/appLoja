package services;

import java.util.ArrayList;

import config.Page;
import dao.TelefoneDAO;
import message.Response;
import models.Telefone;
import services.errors.ErrorsData;
import services.errors.ValidationRequiredField;

public class TelefoneService extends DataBaseTransactionService<Telefone, Long>{
	
	private TelefoneDAO dao;
	
	private Response response = null;

	public TelefoneService() {
		dao = new TelefoneDAO(openEntityManager());
	}

	@Override
	public Response add(Telefone entity) {
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
	public Response update(Telefone entity) {
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
	public Response remove(Telefone entity) {
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
		Telefone telefone = null;
		try {
			telefone = dao.searchById(id);
			response = getMessageResponse().message(telefone, "Encontrado com êxito !", false);	
		} catch (Exception e) {
			e.printStackTrace();
			response = getMessageResponse().message(telefone, e.getMessage(), true);	
		} finally {
			closeEntityManager();
		}
		return response;
	}

	@Override
	public Page<Telefone> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Telefone> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	@Override
	public Response validarDadosFromView(Telefone objeto) {
		errorsData = new ArrayList<ErrorsData>();
		errorsData = ValidationRequiredField.validarCampoRequerido(objeto);
		
		return returnErrorOrNot();
	}
	
}