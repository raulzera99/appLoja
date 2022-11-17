package services;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import config.Page;
import dao.EnderecoDAO;
import message.Response;
import models.Endereco;
import services.errors.ErrorsData;
import services.errors.ValidationRequiredField;

public class EnderecoService extends DataBaseTransactionService<Endereco, Long>{
	
	private EnderecoDAO dao;
	
	private Response response = null;

	public EnderecoService(EntityManager em, EnderecoDAO dao) {
		super(em, dao);
		this.dao = dao;
	}

	@Override
	public Response add(Endereco entity) {
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
	public Response update(Endereco entity) {
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
	public Response remove(Endereco entity) {
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
		Endereco endereco = null;
		try {
			endereco = dao.searchById(id);
			response = getMessageResponse().message(endereco, "Encontrado com êxito !", false);	
		} catch (Exception e) {
			e.printStackTrace();
			response = getMessageResponse().message(endereco, e.getMessage(), true);	
		} finally {
			closeEntityManager();
		}
		return response;
	}

	@Override
	public Page<Endereco> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Endereco> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	@Override
	public Response validarDadosFromView(Endereco objeto) {
		errorsData = new ArrayList<ErrorsData>();
		errorsData = ValidationRequiredField.validarCampoRequerido(objeto);
		
		return returnErrorOrNot();
	}
}
