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

import view.pagamentos.PagamentoComBoletoView;
import view.pagamentos.PagamentoComCartaoView;
import view.pagamentos.TablePagamentoBoletoPanel;

public class MainView extends JFrame {
	private static final long serialVersionUID = 2435012607684752695L;
	JPanel contentPane;
	TablePagamentoBoletoPanel tablePagamentoBoletoPanel = new TablePagamentoBoletoPanel();
	JMenuItem mntm_pagamentoCartao = new JMenuItem("Cartão");
	JMenuItem mntm_pagamentoBoleto = new JMenuItem("Boleto");
	

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
		mntm_pagamentoCartao.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							PagamentoComCartaoView frame = new PagamentoComCartaoView();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mntm_pagamentoBoleto.addActionListener(new ActionListener() {
			
			
		});
	}

	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1115, 647);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(86, 86, 86));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JPanel topBar = new JPanel();
		topBar.setBackground(new Color(245, 41, 5));
		topBar.setBounds(0, 0, 1101, 119);
		contentPane.add(topBar);
		topBar.setLayout(null);
		
		JMenuBar menu = new JMenuBar();
		menu.setBackground(new Color(245, 41, 5));
		menu.setBounds(0, 0, 101, 22);
		topBar.add(menu);
		
		JMenu mn_Menu = new JMenu("Menu");
		mn_Menu.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
		mn_Menu.setForeground(new Color(255, 255, 255));
		mn_Menu.setBackground(new Color(245, 41, 5));
		menu.add(mn_Menu);
		
		JMenu mn_pagamento = new JMenu("Pagamento");
		mn_pagamento.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_pagamento.setForeground(new Color(0, 0, 0));
		mn_pagamento.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_pagamento);
		
		
		
		mntm_pagamentoBoleto.setForeground(new Color(255, 255, 255));
		mntm_pagamentoBoleto.setBackground(new Color(245, 41, 5));
		mn_pagamento.add(mntm_pagamentoBoleto);
		
		
		
		mntm_pagamentoCartao.setForeground(new Color(255, 255, 255));
		mntm_pagamentoCartao.setBackground(new Color(245, 41, 5));
		mn_pagamento.add(mntm_pagamentoCartao);
		
		JMenuItem mn_Estado = new JMenuItem("Estado");
		mn_Menu.add(mn_Estado);
		mn_Estado.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Estado.setBackground(new Color(245, 41, 5));
		
		JMenuItem mn_Cidade = new JMenuItem("Cidade");
		mn_Cidade.setBackground(new Color(245, 41, 5));
		mn_Cidade.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Menu.add(mn_Cidade);
		
		JMenuItem mn_Endereco = new JMenuItem("Endereço");
		mn_Endereco.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Endereco.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Endereco);
		
		JMenuItem mn_Cliente = new JMenuItem("Cliente");
		mn_Cliente.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Cliente.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Cliente);
		
		JMenuItem mn_Telefone = new JMenuItem("Telefone");
		mn_Telefone.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Telefone.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Telefone);
		
		JMenuItem mn_Pedido = new JMenuItem("Pedido");
		mn_Pedido.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Pedido.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Pedido);
		
		JMenuItem mn_Produto = new JMenuItem("Produto");
		mn_Produto.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Produto.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Produto);
		
		JMenuItem mn_Codigo = new JMenuItem("Código");
		mn_Codigo.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		mn_Codigo.setBackground(new Color(245, 41, 5));
		mn_Menu.add(mn_Codigo);
		
		JLabel lblNewLabel = new JLabel("Controle de Loja");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 50));
		lblNewLabel.setBounds(153, 30, 464, 79);
		topBar.add(lblNewLabel);
		
		JPanel botBar = new JPanel();
		botBar.setBackground(new Color(245, 41, 5));
		botBar.setBounds(10, 571, 1079, 26);
		contentPane.add(botBar);
		botBar.setLayout(null);
		
		JPanel panelTables = new JPanel();
		panelTables.setBounds(10, 130, 1079, 430);
		contentPane.add(panelTables);
		panelTables.setVisible(rootPaneCheckingEnabled);
		panelTables.add(tablePagamentoBoletoPanel);
	}
}
