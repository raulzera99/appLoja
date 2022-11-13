package services;

import java.util.ArrayList;

import config.Page;
import dao.PagamentoComCartaoDAO;
import message.Response;
import models.PagamentoComCartao;
import services.errors.ErrorsData;
import services.errors.ValidationRequiredField;

public class PagamentoComCartaoService extends DataBaseTransactionService<PagamentoComCartao, Long>{
	
	private PagamentoComCartaoDAO dao;
	
	private Response response = null;
	
	public PagamentoComCartaoService() {
		dao = new PagamentoComCartaoDAO(openEntityManager());
	}

	@Override
	public Response add(PagamentoComCartao entity) {
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
	public Response update(PagamentoComCartao entity) {
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
				response = getMessageResponse().message(entity, e.getMessage(), true);
			}
		}finally {
			closeEntityManager();
		}
		return response;
	}

	@Override
	public Response remove(PagamentoComCartao entity) {
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
				response = getMessageResponse().message(entity, e.getMessage(), true);
			}
		}finally {
			closeEntityManager();
		}
		return response;
	}

	@Override
	public Response findById(Long id) {
		PagamentoComCartao pagamentoCartao = null;
		try {
			pagamentoCartao = dao.searchById(id);
			response = getMessageResponse().message(pagamentoCartao, "Encontrado com êxito !", false);	
		} catch (Exception e) {
			e.printStackTrace();
			response = getMessageResponse().message(pagamentoCartao, e.getMessage(), true);	
		} finally {
			closeEntityManager();
		}
		return response;
	}

	@Override
	public Page<PagamentoComCartao> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<PagamentoComCartao> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	@Override
	public Response validarDadosFromView(PagamentoComCartao objeto) {
		errorsData = new ArrayList<ErrorsData>();
		errorsData = ValidationRequiredField.validarCampoRequerido(objeto);
		
		return returnErrorOrNot();
	}
}