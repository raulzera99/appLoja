package services;

import java.util.ArrayList;

import config.Page;
import dao.PedidoDAO;
import message.Response;
import models.Pedido;
import services.errors.ErrorsData;
import services.errors.ValidationRequiredField;

public class PedidoService extends DataBaseTransactionService<Pedido, Long>{
	
	private PedidoDAO dao;
	
	private Response response = null;

	public PedidoService() {
		dao = new PedidoDAO(openEntityManager());
	}

	@Override
	public Response add(Pedido entity) {
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
	public Response update(Pedido entity) {
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
	public Response remove(Pedido entity) {
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
		Pedido pedido = null;
		try {
			pedido = dao.searchById(id);
			response = getMessageResponse().message(pedido, "Encontrado com êxito !", false);	
		} catch (Exception e) {
			e.printStackTrace();
			response = getMessageResponse().message(pedido, e.getMessage(), true);	
		} finally {
			closeEntityManager();
		}
		return response;
	}

	@Override
	public Page<Pedido> listaPaginada(Integer page, Integer pageSize) {
		return dao.listaPaginada(page, pageSize);
	}

	@Override
	public Page<Pedido> listaPaginada(Integer page, Integer pageSize, String text) {
		return dao.listaPaginada(page, pageSize, text);
	}

	@Override
	public Response validarDadosFromView(Pedido objeto) {
		errorsData = new ArrayList<ErrorsData>();
		errorsData = ValidationRequiredField.validarCampoRequerido(objeto);
		
		return returnErrorOrNot();
	}
}