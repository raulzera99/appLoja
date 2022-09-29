package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.ClienteDAO;
import models.Cliente;
import persistence.DataBaseConnection;

public class ClienteService {
	// Attributes
	@PersistenceContext(unitName = "apploja")
	private final EntityManager em;

	private ClienteDAO dao;

	private EntityTransaction tx;

	// Constructors
	public ClienteService() {
		em = DataBaseConnection.getConnection().getEntityManager();
		dao = new ClienteDAO(em);
	}

	// Methods
	public void addCliente(Cliente Cliente) {
		tx = getEm().getTransaction();

		try {
			getTx().begin();
			getDao().addCliente(Cliente);
			getTx().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (getTx().isActive()) {
				getTx().rollback();
			}
		} finally {
			getEm().close();
		}
	}

	public Cliente updateCliente(Cliente Cliente) {
		tx = getEm().getTransaction();

		try {
			getTx().begin();
			Cliente ClienteAtual = getDao().updateCliente(Cliente);
			getTx().commit();
			return ClienteAtual;
		} catch (Exception e) {
			e.printStackTrace();
			if (getTx().isActive()) {
				getTx().rollback();
			}
		} finally {
			getEm().close();
		}
		return null;
	}

	public void removeCliente(Long id) {
		tx = getEm().getTransaction();

		try {
			getTx().begin();
			getDao().removeClienteById(id);
			getTx().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (getTx().isActive()) {
				getTx().rollback();
			}
		} finally {
			getEm().close();
		}
	}

	public Cliente searchClienteById(Long id) {
		Cliente Cliente = new Cliente();
		Cliente = dao.searchClienteById(id);
		return Cliente;
	}

	public List<Cliente> listAllClientes() {
		return dao.listAllClientes();
	}

	private ClienteDAO getDao() {
		return dao;
	}

	private EntityTransaction getTx() {
		return tx;
	}

	private EntityManager getEm() {
		return em;
	}
}
