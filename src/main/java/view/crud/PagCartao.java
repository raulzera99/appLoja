package view.crud;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import models.PagamentoComCartao;

import services.PagamentoCartaoService;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class PagCartao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4875991024217101385L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnExcluir = new JButton("Excluir");
	JButton btnAlterar = new JButton("Alterar");
	JButton btnCancelar = new JButton("Cancelar");
	private JTextField txtEstado;
	private JTextField txtParcelas;
	
	private long idPagCartao = 0l;
	private PagamentoCartaoService pagamentoCartaoService;
	
	private PagamentoComCartao pagamentoCartao;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PagCartao frame = new PagCartao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public PagCartao() {
		setBackground(new Color(0, 0, 0));
		setTitle("Pagamento Cartão");
		initComponents();
		eventhandler();
	}
	
	private void eventhandler() {
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idPagCartao == 0l) {
					salvarPagCartao();
				}else {
					alterarPagCartao();
				}
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirPagCartao();
			}
		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
	}
	
	public void salvarPagCartao() {
		pagamentoCartaoService = getPagCartaoService();
		pagamentoCartao = getPagCartao();
		
		setPagCartaoview();
		pagamentoCartaoService.addPagamentoCartao(pagamentoCartao);
		
		limpa();
		
		
	}
	
	public void alterarPagCartao() {
		pagamentoCartao = getPagCartao();
		pagamentoCartaoService = getPagCartaoService();
		
		pagamentoCartao.setId(idPagCartao);
		setPagCartaoview();
		
		pagamentoCartaoService.updatePagamentoCartao(pagamentoCartao);
		limpa();
	}
	
	public void excluirPagCartao() {
		pagamentoCartaoService = getPagCartaoService();
		pagamentoCartaoService.removePagamentoCartao(idPagCartao);
		
		limpa();
	}
			
	private void setPagCartaoview() {
		pagamentoCartao.setId(idPagCartao);
		pagamentoCartao.setEstado(Integer.parseInt(txtEstado.getText()));
		pagamentoCartao.setNumeroDeParcelas(Integer.parseInt(txtParcelas.getText()));
		
		
	}
	
	private void limpa() {
		
		idPagCartao = 0l;
		txtParcelas.setText("");
		txtEstado.setText("");
	}
	
	public PagamentoCartaoService getPagCartaoService() {
		return new PagamentoCartaoService();
	}
	
	public PagamentoComCartao getPagCartao() {
		return new PagamentoComCartao();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 798, 476);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(211, 61, 48));
		panel.setBounds(0, 0, 784, 101);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pagamento Com Cartão");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
		lblNewLabel.setBounds(10, 30, 386, 46);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(0, 103, 784, 336);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		
		btnSalvar.setForeground(new Color(255, 255, 255));
		btnSalvar.setBackground(new Color(211, 61, 48));
		btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		btnSalvar.setBounds(10, 278, 97, 29);
		panel_1.add(btnSalvar);
		
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		btnExcluir.setBackground(new Color(211, 61, 48));
		btnExcluir.setBounds(117, 278, 97, 29);
		panel_1.add(btnExcluir);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAlterar.setForeground(Color.WHITE);
		btnAlterar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		btnAlterar.setBackground(new Color(211, 61, 48));
		btnAlterar.setBounds(224, 278, 97, 29);
		panel_1.add(btnAlterar);
		
		
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		btnCancelar.setBackground(new Color(211, 61, 48));
		btnCancelar.setBounds(331, 278, 97, 29);
		panel_1.add(btnCancelar);
		
		JLabel lblestado = new JLabel("Estado :");
		lblestado.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		lblestado.setBounds(10, 25, 85, 21);
		panel_1.add(lblestado);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nº Parcelas :");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		lblNewLabel_1_1.setBounds(10, 56, 85, 21);
		panel_1.add(lblNewLabel_1_1);
		
		txtEstado = new JTextField();
		txtEstado.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		txtEstado.setBounds(99, 26, 374, 19);
		panel_1.add(txtEstado);
		txtEstado.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(211, 61, 48));
		panel_2.setBounds(0, 317, 784, 21);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		txtParcelas = new JTextField();
		txtParcelas.setFont(new Font("Segoe UI", Font.ITALIC, 15));
		txtParcelas.setColumns(10);
		txtParcelas.setBounds(99, 57, 374, 19);
		panel_1.add(txtParcelas);
	}
}
