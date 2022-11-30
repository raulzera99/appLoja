package services;
import java.io.InputStream;

import javax.swing.JFrame;

import config.Constantes;
import models.PrintJasperReports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

public class JasperReportsService extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ReportConnection conexao;
	
	public JasperReportsService() {
		conexao = new ReportConnection();
	}

	public void gerarRelatorioPorSql(PrintJasperReports relatorio) {
		
		try {
			InputStream arquivoFisico = getClass().getClassLoader()
					.getResourceAsStream(Constantes.reportDirectory+relatorio.getFile()+Constantes.reportSufixe);
			
			JasperPrint print = JasperFillManager
					.fillReport(arquivoFisico, 
								relatorio.getParams(),
								conexao.getConnection());
			
			
			viewRelatorio(print);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	public void gerarRelatorioPorLista(PrintJasperReports relatorio) {
		try {
			InputStream arquivoFisico = getClass().getClassLoader()
					.getResourceAsStream(Constantes.reportDirectory+relatorio.getFile()+Constantes.reportSufixe);
			
			JasperPrint print = JasperFillManager
					.fillReport(arquivoFisico, 
								relatorio.getParams(),
								new JRBeanCollectionDataSource(relatorio.getCollection()));
			
			
			viewRelatorio(print);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	private void viewRelatorio(JasperPrint print ) {
		JRViewer viewer = new JRViewer(print);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setTitle("Visualização de relatório");
		getContentPane().add(viewer);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

}
