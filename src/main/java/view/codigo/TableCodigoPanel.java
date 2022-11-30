package view.codigo;

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
import dao.CodigoDAO;
import models.Codigo;
import persistence.DataBaseConnection;
import services.CodigoService;
import view.table.RenderHeaderTable;
import view.table.RenderTable;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TableCodigoPanel extends JPanel {
	private static final long serialVersionUID = -4694190107545197497L;
	JTable tableCodigo;
	JScrollPane scrollPane = new JScrollPane();
	JScrollPane scrollPaneTableCodigo = new JScrollPane();
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
	JTextField txtSearch = new JTextField();
	
	
	private TableCodigoModel model;
	private Page<Codigo> page;
	private CodigoService codigoService;
	private Codigo codigo;
	
	private int linha = 0;
	private int coluna = 0;
	private int tamanhoPagina = 50;
	private int paginaAtual = 0;
	
	private static TableCodigoPanel TABLE_PEDIDO;

	public TableCodigoPanel() {
		//setVisible(true);
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
				showCodigoFrame(Constantes.INCLUIR);
				initTable();
			}

		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showCodigoFrame(Constantes.ALTERAR);
				initTable();
			}
		});
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showCodigoFrame(Constantes.EXCLUIR);
				initTable();
			}

		});
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showCodigoFrame(Constantes.CONSULTAR);
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
		
		scrollPane.setViewportView(scrollPaneTableCodigo);
		
		tableCodigo = new JTable();
		tableCodigo.setFont(new Font("Tahoma", Font.PLAIN, 9));
		scrollPaneTableCodigo.setViewportView(tableCodigo);
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
		
		panelSearch.setBounds(10, 11, 1065, 42);
		add(panelSearch);
		panelSearch.setLayout(null);
		
		txtSearch.setBounds(160, 11, 748, 20);
		panelSearch.add(txtSearch);
		txtSearch.setColumns(10);
	}
	
	public static TableCodigoPanel getInstance() {
		if(Objects.isNull(TABLE_PEDIDO)) {
			TABLE_PEDIDO = new TableCodigoPanel();
		}
		return TABLE_PEDIDO;
	}
	
	private void listarCodigo() {
		codigoService = getCodigoService();
		page = codigoService.listaPaginada(paginaAtual, tamanhoPagina);
		
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
		listarCodigo();
		
		model = new TableCodigoModel(page.getContent());
		
		model.fireTableDataChanged();
		
		tableCodigo.setModel(model);
		
		RenderHeaderTable renderHeader = new RenderHeaderTable();
		
		tableCodigo.getTableHeader().setDefaultRenderer(renderHeader);
		
		RenderTable render = new RenderTable();
		
		for(int coluna = 0; coluna < model.getColumnCount(); coluna++) {
			tableCodigo.setDefaultRenderer(model.getColumnClass(coluna), render);
		}
		
		TableColumn coluna = tableCodigo.getColumnModel().getColumn(0);
		coluna.setMinWidth(50);
		coluna.setMaxWidth(60);
		coluna.setPreferredWidth(55);
		
		for(int col = 1; col<model.getColumnCount();col++ ) {
			coluna = tableCodigo.getColumnModel().getColumn(col);
			coluna.setMinWidth(200);
			coluna.setMaxWidth(1000);
			coluna.setPreferredWidth(900);
		}
	}
	
	private void showCodigoFrame(int opcaoCadastro) {
		CodigoView view = new CodigoView(codigo, opcaoCadastro);
		view.setLocationRelativeTo(null);
		view.setVisible(true);
	}
	
	private void getLinhaTabela() {
		codigo = getCodigo();
		
		if(tableCodigo.getSelectedRow() != -1) {
			linha = tableCodigo.getSelectedRow();
			setColuna(tableCodigo.getSelectedColumn());
			codigo = model.getCodigo(linha);
			linha = -1;
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public Codigo getCodigo() {
		return codigo;
	}
	
	

	public CodigoService getCodigoService() {
		EntityManager em = DataBaseConnection.getConnection().getEntityManager();
		return new CodigoService(em, new CodigoDAO(em));
	}

	public void setCodigoService(CodigoService codigoService) {
		this.codigoService = codigoService;
	}

	public void setCodigo(Codigo codigo) {
		this.codigo = codigo;
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
