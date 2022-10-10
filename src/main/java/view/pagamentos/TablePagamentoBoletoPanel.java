package view.pagamentos;

import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TablePagamentoBoletoPanel extends JPanel {
	private static final long serialVersionUID = -4694190107545197497L;
	JTable tablePagamentoBoleto;
	JScrollPane scrollPane = new JScrollPane();
	JScrollPane scrollPaneTablePagamentoBoleto = new JScrollPane();
	JPanel panelButtons = new JPanel();
	JButton btnPrimeiro = new JButton("Primeiro");
	JButton btnAnterior = new JButton("Anterior");
	JButton btnProximo = new JButton("Próximo");
	JButton btnUltimo = new JButton("Último");
	JButton btnAdicionar = new JButton("Adicionar");
	JButton btnAlterar = new JButton("Alterar");
	JButton btnRemover = new JButton("Remover");
	JButton btnConsultar = new JButton("Consultar");

	private int linha = 0;
	private int coluna = 0;
	private int tamanhoPagina = 50;
	private int paginaAtual = 0;
	
	
	/**
	 * Create the panel.
	 */
	public TablePagamentoBoletoPanel() {
		initComponents();
		eventHandler();
	}
	
	private void eventHandler() {
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
	
	private void initComponents() {
		setBounds(new Rectangle(10, 130, 1079, 430));
		setLayout(null);
		
		scrollPane.setBounds(10, 11, 1065, 348);
		add(scrollPane);
		
		scrollPane.setViewportView(scrollPaneTablePagamentoBoleto);
		
		tablePagamentoBoleto = new JTable();
		scrollPaneTablePagamentoBoleto.setViewportView(tablePagamentoBoleto);
		
		panelButtons.setBounds(10, 370, 1065, 79);
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
	}
}
