package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.categoria.TableCategoriaPanel;
import view.cidade.TableCidadePanel;
import view.cliente.TableClientePanel;
import view.codigo.TableCodigoPanel;
import view.endereco.TableEnderecoPanel;
import view.estado.TableEstadoPanel;
import view.itemPedido.TableItemPedidoPanel;
import view.pagamentoComBoleto.TablePagamentoBoletoPanel;
import view.pagamentoComCartao.TablePagamentoCartaoPanel;
import view.pedido.TablePedidoPanel;
import view.produto.TableProdutoPanel;
import view.serial.CommunicationView;
import view.telefone.TableTelefonePanel;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class MainView extends JFrame {
	private static final long serialVersionUID = 2435012607684752695L;
	
	JPanel contentPane;
	TableCategoriaPanel tableCategoriaPanel = TableCategoriaPanel.getInstance();
	TableCidadePanel tableCidadePanel = TableCidadePanel.getInstance();
	TableClientePanel tableClientePanel = TableClientePanel.getInstance();
	TableCodigoPanel tableCodigoPanel = TableCodigoPanel.getInstance();
	TableEnderecoPanel tableEnderecoPanel = TableEnderecoPanel.getInstance();
	TableEstadoPanel tableEstadoPanel = TableEstadoPanel.getInstance();
	TablePagamentoBoletoPanel tablePagamentoBoletoPanel = TablePagamentoBoletoPanel.getInstance();
	TablePagamentoCartaoPanel tablePagamentoCartaoPanel = TablePagamentoCartaoPanel.getInstance();
	TablePedidoPanel tablePedidoPanel = TablePedidoPanel.getInstance();
	TableItemPedidoPanel tableItemPedidoPanel = TableItemPedidoPanel.getInstance();
	TableProdutoPanel tableProdutoPanel = TableProdutoPanel.getInstance();
	TableTelefonePanel tableTelefonePanel = TableTelefonePanel.getInstance();
	
	JLabel lblTitle;
	JPanel topBar;
	JPanel botBar;
	JMenuBar menu;
	JMenu mn_Menu;
	JMenuItem mn_Categoria;
	JMenuItem mn_Cidade;
	JMenuItem mn_Cliente;
	JMenuItem mn_Codigo;
	JMenuItem mn_Endereco;	
	JMenuItem mn_Estado;
	JMenu mn_pagamento;
	JMenuItem mntm_pagamentoCartao;
	JMenuItem mntm_pagamentoBoleto;
	JMenuItem mn_Pedido;
	JMenuItem mn_Produto;
	JMenuItem mn_Telefone;
	JMenuItem mn_itensPedido;
	JMenuItem mntmSerial;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private MainView() {
		setTitle("Loja");
		initComponents();
		eventHandler();
	}
	
	private void eventHandler() {
		mn_Categoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tableCategoriaPanel.setVisible(true);
			}
		});
		
		mn_Cidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tableCidadePanel.setVisible(true);
			}
		});
		
		mn_Cliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tableClientePanel.setVisible(true);
			}
		});
		
		mn_Codigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tableCodigoPanel.setVisible(true);
			}
		});
		
		mn_Endereco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tableEnderecoPanel.setVisible(true);
			}
		});
		
		mn_Estado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tableEstadoPanel.setVisible(true);
			}
		});
		
		mntm_pagamentoBoleto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tablePagamentoBoletoPanel.setVisible(true);
			}
		});
		
		mntm_pagamentoCartao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tablePagamentoCartaoPanel.setVisible(true);
			}
		});
		
		mn_Pedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tablePedidoPanel.setVisible(true);
			}
		});
		
		mn_Produto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tableProdutoPanel.setVisible(true);
			}
		});
		
		mn_Telefone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tableTelefonePanel.setVisible(true);
			}
		});
		
		mn_itensPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisiblePanels(false);
				tableItemPedidoPanel.setVisible(true);
			}
		});
		
		mntmSerial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommunicationView view = new CommunicationView();
				view.setLocationRelativeTo(null);
				view.setVisible(true);
			}
		});
		
	}
	
	private void setVisiblePanels(boolean b) {
		tableCategoriaPanel.setVisible(b);
		tableCidadePanel.setVisible(b);
		tableClientePanel.setVisible(b);
		tableCodigoPanel.setVisible(b);
		tableEnderecoPanel.setVisible(b);
		tableEstadoPanel.setVisible(b);
		tablePagamentoBoletoPanel.setVisible(b);
		tablePagamentoCartaoPanel.setVisible(b);
		tablePedidoPanel.setVisible(b);
		tableProdutoPanel.setVisible(b);
		tableTelefonePanel.setVisible(b);
		tableItemPedidoPanel.setVisible(b);
	}

	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1115, 850);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(86, 86, 86));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisiblePanels(false);
		
		getContentPane().add(tableCategoriaPanel);
		tableCategoriaPanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tableCidadePanel);
		tableCidadePanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tableClientePanel);
		tableClientePanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tableCodigoPanel);
		tableCodigoPanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tableEnderecoPanel);
		tableEnderecoPanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tableEstadoPanel);
		tableEstadoPanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tablePagamentoBoletoPanel);
		tablePagamentoBoletoPanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tablePagamentoCartaoPanel);
		tablePagamentoCartaoPanel.setBounds(10, 130, 1079, 645);

		getContentPane().add(tablePedidoPanel);
		tablePedidoPanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tableProdutoPanel);
		tableProdutoPanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tableTelefonePanel);
		tableTelefonePanel.setBounds(10, 130, 1079, 645);
		
		getContentPane().add(tableItemPedidoPanel);
		tableItemPedidoPanel.setBounds(10, 130, 1079, 645);
		
		topBar = new JPanel();
		topBar.setBackground(new Color(245, 41, 5));
		topBar.setBounds(0, 0, 1101, 119);
		contentPane.add(topBar);
		topBar.setLayout(null);
		
		menu = new JMenuBar();
		menu.setBackground(new Color(245, 41, 5));
		menu.setBounds(0, 0, 1101, 22);
		topBar.add(menu);
		
		mn_Menu = new JMenu("Menu");
		mn_Menu.setBorder(new LineBorder(Color.LIGHT_GRAY));
		mn_Menu.setFocusable(false);
		mn_Menu.setPreferredSize(new Dimension(100, 26));
		mn_Menu.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
		mn_Menu.setForeground(Color.BLACK);
		mn_Menu.setBackground(new Color(245, 41, 5));
		menu.add(mn_Menu);
		
		mn_Pedido = new JMenuItem("Pedido");
		mn_Pedido.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Pedido.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Pedido);
		
		mn_itensPedido = new JMenuItem("Itens do pedido");
		mn_itensPedido.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_itensPedido.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_itensPedido);
		
		mn_pagamento = new JMenu("Pagamentos");
		mn_pagamento.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_pagamento.setForeground(new Color(0, 0, 0));
		mn_pagamento.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_pagamento);
		
		mntm_pagamentoBoleto = new JMenuItem("Boleto");
		mntm_pagamentoBoleto.setForeground(new Color(255, 255, 255));
		mntm_pagamentoBoleto.setBackground(new Color(245, 41, 5));
		mn_pagamento.add(mntm_pagamentoBoleto);
		
		mntm_pagamentoCartao = new JMenuItem("Cartão");
		mntm_pagamentoCartao.setForeground(new Color(255, 255, 255));
		mntm_pagamentoCartao.setBackground(new Color(245, 41, 5));
		mn_pagamento.add(mntm_pagamentoCartao);
		
		mn_Produto = new JMenuItem("Produto");
		mn_Produto.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Produto.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Produto);
		
		mn_Cliente = new JMenuItem("Cliente");
		mn_Cliente.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Cliente.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Cliente);
		
		mn_Endereco = new JMenuItem("Endereço");
		mn_Endereco.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Endereco.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Endereco);
		
		mn_Telefone = new JMenuItem("Telefone");
		mn_Telefone.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Telefone.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Telefone);
		
		mn_Categoria = new JMenuItem("Categoria");
		mn_Categoria.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Categoria.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Categoria);
		
		mn_Cidade = new JMenuItem("Cidade");
		mn_Cidade.setBackground(new Color(245, 41, 5));
		mn_Cidade.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Menu.add(mn_Cidade);
		
		mn_Estado = new JMenuItem("Estado");
		mn_Estado.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Estado.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Estado);
		
		mn_Codigo = new JMenuItem("Código");
		mn_Codigo.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Codigo.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Codigo);
		
		mntmSerial = new JMenuItem("Serial");
		
		mntmSerial.setMaximumSize(new Dimension(100, 32767));
		mntmSerial.setHorizontalTextPosition(SwingConstants.CENTER);
		mntmSerial.setBorder(new LineBorder(Color.LIGHT_GRAY));
		mntmSerial.setHorizontalAlignment(SwingConstants.CENTER);
		mntmSerial.setForeground(Color.BLACK);
		mntmSerial.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
		mntmSerial.setBackground(new Color(245, 41, 5));
		mntmSerial.setPreferredSize(new Dimension(100, 26));
		menu.add(mntmSerial);
		
		lblTitle = new JLabel("Controle de Loja");
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setFont(new Font("Segoe UI", Font.ITALIC, 50));
		lblTitle.setBounds(153, 30, 464, 79);
		topBar.add(lblTitle);
		
		botBar = new JPanel();
		botBar.setBackground(new Color(245, 41, 5));
		botBar.setBounds(0, 785, 1101, 26);
		contentPane.add(botBar);
		botBar.setLayout(null);
		
	}
}
