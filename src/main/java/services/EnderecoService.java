package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import dao.EnderecoDAO;
import models.Endereco;
import persistence.DataBaseConnection;

public class EnderecoService {
	//Attributes
		@PersistenceContext(unitName = "apploja")
		private final EntityManager em;
		
		private EnderecoDAO dao;
		
		private EntityTransaction tx;
		
		//Constructors
		public EnderecoService() {
			em = DataBaseConnection.getConnection().getEntityManager();
			dao = new EnderecoDAO(em);
		}
		
		//Methods
		public void addEndereco(Endereco endereco) {
			tx = getEm().getTransaction();
			try {
				getTx().begin();
				getDao().addEndereco(endereco);;
				getTx().commit();
			}
			catch(Exception e) {
				e.printStackTrace();
				if(getTx().isActive()) {
					getTx().rollback();
				}
			}
			finally {
				getEm().close();
			}
		}
		
		public Endereco updateEndereco(Endereco endereco) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				Endereco enderecoAtual = getDao().updateEndereco(endereco);
				getTx().commit();
				return enderecoAtual;
			}
			catch(Exception e) {
				e.printStackTrace();
				if(getTx().isActive()) {
					getTx().rollback();
				}
			}
			finally {
				getEm().close();
			}
			return null;
		}
		
		public void removeEndereco(Long id) {
			tx = getEm().getTransaction();
			
			try {
				getTx().begin();
				getDao().removeEnderecoById(id);
				getTx().commit();
			}
			catch(Exception e) {
				e.printStackTrace();
				if(getTx().isActive()) {
					getTx().rollback();
				}
			}
			finally {
				getEm().close();
			}
		}
		
		public Endereco searchEnderecoById(Long id) {
			Endereco endereco = new Endereco();
			endereco = dao.searchEnderecoById(id);
			return endereco;
		}
		
		public List<Endereco> listAllEnderecos(){
			return dao.listAllEnderecos();
		}
		
		private EnderecoDAO getDao() {
			return dao;
		}

		private EntityTransaction getTx() {
			return tx;
		}

		private EntityManager getEm() {
			return em;
		}
}
