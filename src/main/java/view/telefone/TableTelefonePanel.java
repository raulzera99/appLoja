package view.telefone;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import config.Constantes;
import config.Page;
import dao.TelefoneDAO;
import models.PrintJasperReports;
import models.Telefone;
import persistence.DataBaseConnection;
import services.JasperReportsService;
import services.TelefoneService;
import view.table.RenderHeaderTable;
import view.table.RenderTable;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TableTelefonePanel extends JPanel {
	private static final long serialVersionUID = -4694190107545197497L;
	JTable tableTelefone;
	JScrollPane scrollPane = new JScrollPane();
	JScrollPane scrollPaneTableTelefone = new JScrollPane();
	JPanel panelButtons = new JPanel();
	JPanel panelSearch = new JPanel();
	JButton btnPrimeiro = new JButton("Primeiro");
	JButton btnAnterior = new JButton("Anterior");
	JButton btnProximo = new JButton("Próximo");
	JButton btnUltimo = new JButton("Último");
	JButton btnAdicionar = new JButton("Adicionar");
	JButton btnAlterar = new JButton("Alterar");
	JButton btnRemover = new JButton("Remover");
	JButton btnConsultar = new JButton("Consultar");
	JButton btnRelatorio = new JButton("Relatório");
	JTextField txtSearch = new JTextField();
	
	
	private TableTelefoneModel model;
	private Page<Telefone> page;
	private TelefoneService telefoneService;
	private Telefone telefone;
	
	private int linha = 0;
	private int coluna = 0;
	private int tamanhoPagina = 50;
	private int paginaAtual = 0;
	
	private static TableTelefonePanel TABLE_TELEFONE;

	public TableTelefonePanel() {
		initComponents();
		eventHandler();
		initTable();
	}
	
	private void eventHandler() {
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = 1;
				initTable();
			}
		});
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(paginaAtual > 1) {
					paginaAtual = paginaAtual - 1;
					initTable();
				}
			}
		});
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(paginaAtual < page.getTotalPage()) {
					paginaAtual = paginaAtual + 1;
					initTable();
				}
			}
		});
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = page.getTotalPage();	
				initTable();
			}
		});
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTelefoneFrame(Constantes.INCLUIR);
				initTable();
			}

		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showTelefoneFrame(Constantes.ALTERAR);
				initTable();
			}
		});
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showTelefoneFrame(Constantes.EXCLUIR);
				initTable();
			}

		});
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showTelefoneFrame(Constantes.CONSULTAR);
			}
		});
		
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintJasperReports relatorio = new PrintJasperReports();
				JasperReportsService service = new JasperReportsService();
				
				relatorio.setFile("relatorio_telefone");
				service.gerarRelatorioPorSql(relatorio);
			}
		});
		
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				initTable();
			}
		});
	}
	
	private void initComponents() {
		setBounds(new Rectangle(10, 130, 1079, 468));
		setLayout(null);
		scrollPane.setBounds(10, 64, 1065, 478);
		add(scrollPane);
		
		scrollPane.setViewportView(scrollPaneTableTelefone);
		
		tableTelefone = new JTable();
		tableTelefone.setFont(new Font("Tahoma", Font.PLAIN, 9));
		scrollPaneTableTelefone.setViewportView(tableTelefone);
		panelButtons.setBounds(11, 550, 1065, 79);
		add(panelButtons);
		panelButtons.setLayout(null);
		
		btnPrimeiro.setBounds(10, 11, 89, 23);
		panelButtons.add(btnPrimeiro);
		
		btnAnterior.setBounds(109, 11, 89, 23);
		panelButtons.add(btnAnterior);
		
		btnProximo.setBounds(208, 11, 89, 23);
		panelButtons.add(btnProximo);
		
		btnUltimo.setBounds(307, 11, 89, 23);
		panelButtons.add(btnUltimo);
		
		btnAdicionar.setBounds(10, 45, 89, 23);
		panelButtons.add(btnAdicionar);
		
		btnAlterar.setBounds(119, 45, 89, 23);
		panelButtons.add(btnAlterar);
		
		btnRemover.setBounds(233, 45, 89, 23);
		panelButtons.add(btnRemover);
		
		btnConsultar.setBounds(346, 45, 89, 23);
		panelButtons.add(btnConsultar);
		
		btnRelatorio.setBounds(460, 45, 89, 23);
		panelButtons.add(btnRelatorio);
		
		panelSearch.setBounds(10, 11, 1065, 42);
		add(panelSearch);
		panelSearch.setLayout(null);
		
		txtSearch.setBounds(160, 11, 748, 20);
		panelSearch.add(txtSearch);
		txtSearch.setColumns(10);
	}
	
	public static TableTelefonePanel getInstance() {
		if(Objects.isNull(TABLE_TELEFONE)) {
			TABLE_TELEFONE = new TableTelefonePanel();
		}
		return TABLE_TELEFONE;
	}
	
	private void listarTelefone() {
		telefoneService = getTelefoneService();
		page = telefoneService.listaPaginada(paginaAtual, tamanhoPagina);
		
		if(paginaAtual == 1) {
			btnPrimeiro.setEnabled(false);
			btnAnterior.setEnabled(false);
		}
		else {
			btnPrimeiro.setEnabled(true);
			btnAnterior.setEnabled(true);
		}
		
		if(paginaAtual == page.getTotalPage()) {
			btnProximo.setEnabled(false);
			btnUltimo.setEnabled(false);
		}
		else {
			btnProximo.setEnabled(true);
			btnUltimo.setEnabled(true);
		}
		
		if(paginaAtual > page.getTotalPage()) {
			paginaAtual = page.getTotalPage();
		}
		
		paginaAtual = page.getPage();
		tamanhoPagina = page.getPageSize();
	}
	
	private void initTable() {
		listarTelefone();
		
		model = new TableTelefoneModel(page.getContent());
		
		model.fireTableDataChanged();
		
		tableTelefone.setModel(model);
		
		RenderHeaderTable renderHeader = new RenderHeaderTable();
		
		tableTelefone.getTableHeader().setDefaultRenderer(renderHeader);
		
		RenderTable render = new RenderTable();
		
		for(int coluna = 0; coluna < model.getColumnCount(); coluna++) {
			tableTelefone.setDefaultRenderer(model.getColumnClass(coluna), render);
		}
		
		TableColumn coluna = tableTelefone.getColumnModel().getColumn(0);
		coluna.setMinWidth(50);
		coluna.setMaxWidth(60);
		coluna.setPreferredWidth(55);
		
		for(int col = 1; col<model.getColumnCount();col++ ) {
			coluna = tableTelefone.getColumnModel().getColumn(col);
			coluna.setMinWidth(200);
			coluna.setMaxWidth(600);
			coluna.setPreferredWidth(500);
		}
	}
	
	private void showTelefoneFrame(int opcaoCadastro) {
		TelefoneView view = new TelefoneView(telefone, opcaoCadastro);
		view.setLocationRelativeTo(null);
		view.setVisible(true);
	}
	
	private void getLinhaTabela() {
		telefone = getTelefone();
		
		if(tableTelefone.getSelectedRow() != -1) {
			linha = tableTelefone.getSelectedRow();
			setColuna(tableTelefone.getSelectedColumn());
			telefone = model.getTelefone(linha);
			linha = -1;
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public Telefone getTelefone() {
		return telefone;
	}
	
	

	public TelefoneService getTelefoneService() {
		EntityManager em = DataBaseConnection.getConnection().getEntityManager();
		return new TelefoneService(em, new TelefoneDAO(em));
	}

	public void setTelefoneService(TelefoneService telefoneService) {
		this.telefoneService = telefoneService;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
}
