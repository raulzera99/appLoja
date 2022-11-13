package services;

import java.util.ArrayList;

import config.Page;
import dao.PagamentoComBoletoDAO;
import message.Response;
import models.PagamentoComBoleto;
import services.errors.ErrorsData;
import services.errors.ValidationRequiredField;

public class PagamentoComBoletoService extends DataBaseTransactionService<PagamentoComBoleto, Long>{
	
	private PagamentoComBoletoDAO dao;
	
	private Response response = null;

	public PagamentoComBoletoService() {
		dao = new PagamentoComBoletoDAO(openEntityManager());
	}

	@Override
	public Response add(PagamentoComBoleto entity) {
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
	public Response update(PagamentoComBoleto entity) {
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
	public Response remove(PagamentoComBoleto entity) {
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
		PagamentoComBoleto pagamentoBoleto= null;
		try {
			pagamentoBoleto = dao.searchById(id);
			response = getMessageResponse().message(pagamentoBoleto, "Encontrado com êxito !", false);	
		} catch (Exception e) {
			e.printStackTrace();
			response = getMessageResponse().message(pagamentoBoleto, e.getMessage(), true);	
		} finally {
			closeEntityManager();
		}
		return response;
	}

	@Override
	public Page<PagamentoComBoleto> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<PagamentoComBoleto> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	@Override
	public Response validarDadosFromView(PagamentoComBoleto objeto) {
		errorsData = new ArrayList<ErrorsData>();
		errorsData = ValidationRequiredField.validarCampoRequerido(objeto);
		
		return returnErrorOrNot();
	}
}