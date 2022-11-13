package services;

import java.util.ArrayList;

import config.Page;
import dao.CodigoDAO;
import message.Response;
import models.Codigo;
import services.errors.ErrorsData;
import services.errors.ValidationRequiredField;

public class CodigoService extends DataBaseTransactionService<Codigo, Long>{
	
	private CodigoDAO dao;
	
	private Response response = null;

	public CodigoService() {
		dao = new CodigoDAO(openEntityManager());
	}

	@Override
	public Response add(Codigo entity) {
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
	public Response update(Codigo entity) {
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
	public Response remove(Codigo entity) {
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
		Codigo codigo = null;
		try {
			codigo = dao.searchById(id);
			response = getMessageResponse().message(codigo, "Encontrado com êxito !", false);	
		} catch (Exception e) {
			e.printStackTrace();
			response = getMessageResponse().message(codigo, e.getMessage(), true);	
		} finally {
			closeEntityManager();
		}
		return response;
	}

	@Override
	public Page<Codigo> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Codigo> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	@Override
	public Response validarDadosFromView(Codigo objeto) {
		errorsData = new ArrayList<ErrorsData>();
		errorsData = ValidationRequiredField.validarCampoRequerido(objeto);
		
		return returnErrorOrNot();
	}
}
