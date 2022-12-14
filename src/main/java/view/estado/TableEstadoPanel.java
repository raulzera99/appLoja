package view.estado;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import config.Constantes;
import config.Page;
import dao.EstadoDAO;
import models.Estado;
import models.PrintJasperReports;
import persistence.DataBaseConnection;
import services.EstadoService;
import services.JasperReportsService;
import view.table.RenderHeaderTable;
import view.table.RenderTable;

public class TableEstadoPanel extends JPanel {
	private static final long serialVersionUID = -4694190107545197497L;
	JTable tableEstado;
	JScrollPane scrollPane = new JScrollPane();
	JScrollPane scrollPaneTableEstado = new JScrollPane();
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
	JButton btnRelatorioPorLista = new JButton("Relatório por lista");
	JTextField txtSearch = new JTextField();

	
	private TableEstadoModel model;
	private Page<Estado> page;
	private EstadoService estadoService;
	private Estado estado;
	
	private int linha = 0;
	private int coluna = 0;
	private int tamanhoPagina = 50;
	private int paginaAtual = 0;
	
	private static TableEstadoPanel TABLE_ESTADO;

	public TableEstadoPanel() {
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
				showEstadoFrame(Constantes.INCLUIR);
				initTable();
			}

		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showEstadoFrame(Constantes.ALTERAR);
				initTable();
			}
		});
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showEstadoFrame(Constantes.EXCLUIR);
				initTable();
			}

		});
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showEstadoFrame(Constantes.CONSULTAR);
				initTable();
			}
		});
		
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				initTable();
			}
		});
		
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintJasperReports relatorio = new PrintJasperReports();
				JasperReportsService service = new JasperReportsService();
				
				relatorio.setFile("relatorio_estado");
				service.gerarRelatorioPorSql(relatorio);
			}
		});
		
		btnRelatorioPorLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintJasperReports relatorio = new PrintJasperReports();
				JasperReportsService service = new JasperReportsService();
				
				estadoService = getEstadoService();
				
				List<Estado> listaCliente = estadoService.getListAll();
				
				relatorio.setFile("relatorio_estado_list");
				relatorio.setCollection(listaCliente);
				
				service.gerarRelatorioPorLista(relatorio);
				
			}
		});
	}
	
	private void initComponents() {
		setBounds(new Rectangle(10, 130, 1079, 468));
		setLayout(null);
		scrollPane.setBounds(10, 64, 1065, 478);
		add(scrollPane);
		
		scrollPane.setViewportView(scrollPaneTableEstado);
		
		tableEstado = new JTable();
		tableEstado.setFont(new Font("Tahoma", Font.PLAIN, 9));
		scrollPaneTableEstado.setViewportView(tableEstado);
		panelButtons.setBounds(11, 550, 1065, 79);
		add(panelButtons);
		panelButtons.setLayout(null);
		
		
		btnPrimeiro.setBounds(300, 11, 89, 23);
		panelButtons.add(btnPrimeiro);
		
		
		btnAnterior.setBounds(399, 11, 89, 23);
		panelButtons.add(btnAnterior);
		
		
		btnProximo.setBounds(498, 11, 89, 23);
		panelButtons.add(btnProximo);
		
		
		btnUltimo.setBounds(597, 11, 89, 23);
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
		
		btnRelatorioPorLista.setBounds(573, 0, 89, 23);
		//panelButtons.add(btnRelatorioPorLista);
		
		
		panelSearch.setBounds(10, 11, 1065, 42);
		add(panelSearch);
		panelSearch.setLayout(null);
		
		txtSearch.setBounds(160, 11, 748, 20);
		panelSearch.add(txtSearch);
		txtSearch.setColumns(10);
	}
	
	public static TableEstadoPanel getInstance() {
		if(Objects.isNull(TABLE_ESTADO)) {
			TABLE_ESTADO = new TableEstadoPanel();
		}
		return TABLE_ESTADO;
	}
	
	private void listarEstado() {
		estadoService = getEstadoService();
		if(txtSearch.equals("")) {
			page = estadoService.listaPaginada(paginaAtual, tamanhoPagina);
		}
		else {
			page = estadoService.listaPaginada(paginaAtual, tamanhoPagina, txtSearch.getText());
		}
		
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
		listarEstado();
		
		model = new TableEstadoModel(page.getContent());
		
		model.fireTableDataChanged();
		
		tableEstado.setModel(model);
		
		RenderHeaderTable renderHeader = new RenderHeaderTable();
		
		tableEstado.getTableHeader().setDefaultRenderer(renderHeader);
		
		RenderTable render = new RenderTable();
		
		for(int coluna = 0; coluna < model.getColumnCount(); coluna++) {
			tableEstado.setDefaultRenderer(model.getColumnClass(coluna), render);
		}
		
		TableColumn coluna = tableEstado.getColumnModel().getColumn(0);
		coluna.setMinWidth(50);
		coluna.setMaxWidth(60);
		coluna.setPreferredWidth(55);
		
		
		coluna = tableEstado.getColumnModel().getColumn(1);
		coluna.setMinWidth(900);
		coluna.setMaxWidth(1000);
		coluna.setPreferredWidth(950);
		
	}
	
	private void showEstadoFrame(int opcaoCadastro) {
		EstadoView view = new EstadoView(estado, opcaoCadastro);
		view.setLocationRelativeTo(null);
		view.setVisible(true);
	}
	
	private void getLinhaTabela() {
		estado = getEstado();
		
		if(tableEstado.getSelectedRow() != -1) {
			linha = tableEstado.getSelectedRow();
			setColuna(tableEstado.getSelectedColumn());
			estado = model.getEstado(linha);
			linha = -1;
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public Estado getEstado() {
		return estado;
	}
	
	

	public EstadoService getEstadoService() {
		EntityManager em = DataBaseConnection.getConnection().getEntityManager();
		return new EstadoService(em, new EstadoDAO(em));
	}

	public void setEstadoService(EstadoService estadoService) {
		this.estadoService = estadoService;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
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
