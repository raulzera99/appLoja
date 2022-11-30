package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import config.Constantes;
import persistence.DataBaseConnection;

public class ReportConnection {
	@PersistenceContext(unitName = Constantes.persistenceUnitName)
	private EntityManager em;
	
	private Connection connection = null;
	
	public EntityManager openEntityManager() {
		if(Objects.isNull(em)) {
			em = DataBaseConnection.getConnection().getEntityManager();
		}
		return em;
	}
	
	public Connection getConnection() {
		openEntityManager();
		Session session = em.unwrap(Session.class);
		Conexao conexao = new Conexao();
		session.doWork(conexao);
		connection = conexao.getConnection();
		
		return connection;
	}
	
	private static class Conexao implements Work{
		private Connection connection;
		
		@Override
		public void execute(Connection connection) throws SQLException {
			this.connection = connection;
		}
		
		public Connection getConnection() {
			return connection;
		}
	}
}
