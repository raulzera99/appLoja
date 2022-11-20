package services;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import config.Constantes;

public class ReportConnection {
	@PersistenceContext(unitName = Constantes.persistenceUnitName)
	private EntityManager em;
	
	private Connection connection = null;
	
	public Connection getConnection() {
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
