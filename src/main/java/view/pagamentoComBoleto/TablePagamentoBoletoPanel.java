package view.pagamentoComBoleto;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import config.Constantes;
import config.Page;
import models.PagamentoComBoleto;
import services.PagamentoComBoletoService;
import view.table.RenderHeaderTable;
import view.table.RenderTable;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TablePagamentoBoletoPanel extends JPanel {
	private static final long serialVersionUID = -4694190107545197497L;
	JTable tablePagamentoBoleto;
	JScrollPane scrollPane;
	JScrollPane scrollPaneTablePagamentoBoleto;
	JPanel panelButtons;
	JPanel panelSearch;
	JButton btnPrimeiro;
	JButton btnAnterior;
	JButton btnProximo;
	JButton btnUltimo;
	JButton btnAdicionar;
	JButton btnAlterar;
	JButton btnRemover;
	JButton btnConsultar;
	JTextField txtSearch;
	
	
	private TablePagamentoBoletoModel model;
	private Page<PagamentoComBoleto> page;
	private PagamentoComBoletoService pagamentoBoletoService;
	private PagamentoComBoleto pagamentoBoleto;
	
	private int linha = 0;
	private int coluna = 0;
	private int tamanhoPagina = 50;
	private int paginaAtual = 0;
	
	private static TablePagamentoBoletoPanel TABLE_PAGAMENTO_BOLETO;

	public TablePagamentoBoletoPanel() {
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
				showPagamentoBoletoFrame(Constantes.INCLUIR);
				initTable();
			}

		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showPagamentoBoletoFrame(Constantes.ALTERAR);
				initTable();
			}
		});
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showPagamentoBoletoFrame(Constantes.EXCLUIR);
				initTable();
			}

		});
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showPagamentoBoletoFrame(Constantes.CONSULTAR);
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

		scrollPane = new JScrollPane();
		setBounds(new Rectangle(10, 130, 1079, 468));
		setLayout(null);
		scrollPane.setBounds(10, 64, 1065, 478);
		add(scrollPane);
		
		scrollPaneTablePagamentoBoleto = new JScrollPane();
		scrollPane.setViewportView(scrollPaneTablePagamentoBoleto);
		
		tablePagamentoBoleto = new JTable();
		tablePagamentoBoleto.setFont(new Font("Tahoma", Font.PLAIN, 9));
		scrollPaneTablePagamentoBoleto.setViewportView(tablePagamentoBoleto);
		

		panelButtons = new JPanel();
		panelButtons.setBounds(11, 550, 1065, 79);
		add(panelButtons);
		panelButtons.setLayout(null);
		

		
		btnPrimeiro = new JButton("Primeiro");
		btnPrimeiro.setBounds(10, 11, 89, 23);
		panelButtons.add(btnPrimeiro);
		
		btnAnterior = new JButton("Anterior");
		btnAnterior.setBounds(109, 11, 89, 23);
		panelButtons.add(btnAnterior);
		

		btnProximo = new JButton("Próximo");
		btnProximo.setBounds(208, 11, 89, 23);
		panelButtons.add(btnProximo);
		

		btnUltimo = new JButton("Último");
		btnUltimo.setBounds(307, 11, 89, 23);
		panelButtons.add(btnUltimo);
		

		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(10, 45, 89, 23);
		panelButtons.add(btnAdicionar);

		btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(119, 45, 89, 23);
		panelButtons.add(btnAlterar);

		btnRemover = new JButton("Remover");		
		btnRemover.setBounds(233, 45, 89, 23);
		panelButtons.add(btnRemover);

		btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(346, 45, 89, 23);
		panelButtons.add(btnConsultar);
		
		panelSearch = new JPanel();
		panelSearch.setBounds(10, 11, 1065, 42);
		add(panelSearch);
		panelSearch.setLayout(null);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(160, 11, 748, 20);
		panelSearch.add(txtSearch);
		txtSearch.setColumns(10);
	}
	
	public static TablePagamentoBoletoPanel getInstance() {
		if(Objects.isNull(TABLE_PAGAMENTO_BOLETO)) {
			TABLE_PAGAMENTO_BOLETO = new TablePagamentoBoletoPanel();
		}
		return TABLE_PAGAMENTO_BOLETO;
	}
	
	private void listarPagamentoBoleto() {
		pagamentoBoletoService = getPagamentoBoletoService();
		if(txtSearch.equals("")) {
			page = pagamentoBoletoService.listaPaginada(paginaAtual, tamanhoPagina);
		}
		else {
			page = pagamentoBoletoService.listaPaginada(paginaAtual, tamanhoPagina, txtSearch.getText());
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
		listarPagamentoBoleto();
		
		model = new TablePagamentoBoletoModel(page.getContent());
		
		model.fireTableDataChanged();
		
		tablePagamentoBoleto.setModel(model);
		
		RenderHeaderTable renderHeader = new RenderHeaderTable();
		
		tablePagamentoBoleto.getTableHeader().setDefaultRenderer(renderHeader);
		
		RenderTable render = new RenderTable();
		
		for(int coluna = 0; coluna < model.getColumnCount(); coluna++) {
			tablePagamentoBoleto.setDefaultRenderer(model.getColumnClass(coluna), render);
		}
		
		TableColumn coluna = tablePagamentoBoleto.getColumnModel().getColumn(0);
		coluna.setMinWidth(50);
		coluna.setMaxWidth(60);
		coluna.setPreferredWidth(55);
		
		for(int col = 1; col<model.getColumnCount();col++ ) {
			coluna = tablePagamentoBoleto.getColumnModel().getColumn(col);
			coluna.setMinWidth(200);
			coluna.setMaxWidth(350);
			coluna.setPreferredWidth(325);
		}
	}
	
	private void showPagamentoBoletoFrame(int opcaoCadastro) {
		PagamentoComBoletoView view = new PagamentoComBoletoView(pagamentoBoleto, opcaoCadastro);
		view.setLocationRelativeTo(null);
		view.setVisible(true);
	}
	
	private void getLinhaTabela() {
		pagamentoBoleto = getPagamentoBoleto();
		
		if(tablePagamentoBoleto.getSelectedRow() != -1) {
			linha = tablePagamentoBoleto.getSelectedRow();
			setColuna(tablePagamentoBoleto.getSelectedColumn());
			pagamentoBoleto = model.getPagamentoBoleto(linha);
			linha = -1;
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public PagamentoComBoleto getPagamentoBoleto() {
		return pagamentoBoleto;
	}
	
	

	public PagamentoComBoletoService getPagamentoBoletoService() {
		return new PagamentoComBoletoService();
	}

	public void setPagamentoBoletoService(PagamentoComBoletoService pagamentoBoletoService) {
		this.pagamentoBoletoService = pagamentoBoletoService;
	}

	public void setPagamentoBoleto(PagamentoComBoleto pagamentoBoleto) {
		this.pagamentoBoleto = pagamentoBoleto;
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
