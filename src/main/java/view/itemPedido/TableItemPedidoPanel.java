package view.itemPedido;

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
import dao.ItemPedidoDAO;
import models.ItemPedido;
import persistence.DataBaseConnection;
import services.ItemPedidoService;
import view.table.RenderHeaderTable;
import view.table.RenderTable;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TableItemPedidoPanel extends JPanel {
	private static final long serialVersionUID = -4694190107545197497L;
	JTable tableItemPedido;
	JScrollPane scrollPane = new JScrollPane();
	JScrollPane scrollPaneTableItemPedido = new JScrollPane();
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
	
	
	private TableItemPedidoModel model;
	private Page<ItemPedido> page;
	private ItemPedidoService itemPedidoService;
	private ItemPedido itemPedido;
	
	private int linha = 0;
	private int coluna = 0;
	private int tamanhoPagina = 50;
	private int paginaAtual = 0;
	
	private static TableItemPedidoPanel TABLE_ESTADO;

	public TableItemPedidoPanel() {
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
				showItemPedidoFrame(Constantes.INCLUIR);
				initTable();
			}

		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showItemPedidoFrame(Constantes.ALTERAR);
				initTable();
			}
		});
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showItemPedidoFrame(Constantes.EXCLUIR);
				initTable();
			}

		});
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLinhaTabela();
				showItemPedidoFrame(Constantes.CONSULTAR);
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
		
		scrollPane.setViewportView(scrollPaneTableItemPedido);
		
		tableItemPedido = new JTable();
		tableItemPedido.setFont(new Font("Tahoma", Font.PLAIN, 9));
		scrollPaneTableItemPedido.setViewportView(tableItemPedido);
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
	
	public static TableItemPedidoPanel getInstance() {
		if(Objects.isNull(TABLE_ESTADO)) {
			TABLE_ESTADO = new TableItemPedidoPanel();
		}
		return TABLE_ESTADO;
	}
	
	private void listarItemPedido() {
		itemPedidoService = getItemPedidoService();
		if(txtSearch.equals("")) {
			page = itemPedidoService.listaPaginada(paginaAtual, tamanhoPagina);
		}
		else {
			page = itemPedidoService.listaPaginada(paginaAtual, tamanhoPagina, txtSearch.getText());
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
		listarItemPedido();
		
		model = new TableItemPedidoModel(page.getContent());
		
		model.fireTableDataChanged();
		
		tableItemPedido.setModel(model);
		
		RenderHeaderTable renderHeader = new RenderHeaderTable();
		
		tableItemPedido.getTableHeader().setDefaultRenderer(renderHeader);
		
		RenderTable render = new RenderTable();
		
		for(int coluna = 0; coluna < model.getColumnCount(); coluna++) {
			tableItemPedido.setDefaultRenderer(model.getColumnClass(coluna), render);
		}
		
		TableColumn coluna = tableItemPedido.getColumnModel().getColumn(0);
		coluna.setMinWidth(50);
		coluna.setMaxWidth(60);
		coluna.setPreferredWidth(55);
		
		for(int col = 1; col<model.getColumnCount();col++ ) {
			coluna = tableItemPedido.getColumnModel().getColumn(col);
			coluna.setMinWidth(200);
			coluna.setMaxWidth(350);
			coluna.setPreferredWidth(325);
		}
	}
	
	private void showItemPedidoFrame(int opcaoCadastro) {
		ItemPedidoView view = new ItemPedidoView(itemPedido, opcaoCadastro);
		view.setLocationRelativeTo(null);
		view.setVisible(true);
	}
	
	private void getLinhaTabela() {
		itemPedido = getItemPedido();
		
		if(tableItemPedido.getSelectedRow() != -1) {
			linha = tableItemPedido.getSelectedRow();
			setColuna(tableItemPedido.getSelectedColumn());
			itemPedido = model.getItemPedido(linha);
			linha = -1;
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public ItemPedido getItemPedido() {
		return itemPedido;
	}
	
	

	public ItemPedidoService getItemPedidoService() {
		EntityManager em = DataBaseConnection.getConnection().getEntityManager();
		return new ItemPedidoService(em, new ItemPedidoDAO(em));
	}

	public void setItemPedidoService(ItemPedidoService itemPedidoService) {
		this.itemPedidoService = itemPedidoService;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
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
