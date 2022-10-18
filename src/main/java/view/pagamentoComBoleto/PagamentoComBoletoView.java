package view.pagamentoComBoleto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.Constantes;
import models.PagamentoComBoleto;
import models.enums.EstadoPagamento;
import services.PagamentoComBoletoService;

public class PagamentoComBoletoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtDataVencimento;
	JTextField txtDataPagamento;
	JComboBox<String> comboBoxEstado;
	
	private Long idPagamentoComBoleto = 0l;
	
	private PagamentoComBoletoService pagamentoBoletoService;
	private PagamentoComBoleto pagamentoBoleto;
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PagamentoComBoletoView frame = new PagamentoComBoletoView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	
	/**
	 * Create the frame.
	 */
	public PagamentoComBoletoView(PagamentoComBoleto pagamentoBoleto, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Pagamento com Boleto");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(pagamentoBoleto.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(pagamentoBoleto.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(pagamentoBoleto.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idPagamentoComBoleto == 0l) {
						add();
					}else {
						update();
					}
				}
			});
			
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		
		public void add() {
			pagamentoBoletoService = getPagamentoComBoletoService();
			pagamentoBoleto = getPagamentoComBoleto();
			
			setPagamentoBoletoFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoBoleto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				pagamentoBoletoService.add(pagamentoBoleto);	
				dispose();
				idPagamentoComBoleto = 0L;
				limpa();
			}			
		}
		
		public void update() {
			pagamentoBoleto = getPagamentoComBoleto();
			pagamentoBoletoService = getPagamentoComBoletoService();
			
			pagamentoBoleto.setId(idPagamentoComBoleto);
			setPagamentoBoletoFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoBoleto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				pagamentoBoletoService.update(pagamentoBoleto);
				dispose();
				idPagamentoComBoleto = 0L;
				limpa();
			}			
		}
		
		public void remove() {
			pagamentoBoletoService = getPagamentoComBoletoService();
			PagamentoComBoleto pagamento = new PagamentoComBoleto();
			pagamento = pagamentoBoletoService.findById(idPagamentoComBoleto);
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamento.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				pagamentoBoletoService.remove(pagamento);
				dispose();
				idPagamentoComBoleto = 0L;
				limpa();
			}			
		}
		
		public void findById(Long id) {
			pagamentoBoletoService = getPagamentoComBoletoService();
			pagamentoBoleto = getPagamentoComBoleto();
			
			pagamentoBoleto = pagamentoBoletoService.findById(id);
			
			getPagamentoBoletoFromDataBase();
		}
				
		
		private void limpa() {
			
			idPagamentoComBoleto = 0l;
			txtDataPagamento.setText("");
			txtDataVencimento.setText("");
			comboBoxEstado.setSelectedIndex(-1);
		}
		
		private void setPagamentoBoletoFromView() {
			pagamentoBoleto.setId(idPagamentoComBoleto);
			pagamentoBoleto.setEstado(comboBoxEstado.getSelectedIndex()+1);
			pagamentoBoleto.setDataPagamento(Date.valueOf(txtDataPagamento.getText()));
			pagamentoBoleto.setDataVencimento(Date.valueOf(txtDataVencimento.getText()));
			
		}
		
		private void getPagamentoBoletoFromDataBase() {
			idPagamentoComBoleto = pagamentoBoleto.getId();
			
			comboBoxEstado.setSelectedIndex(pagamentoBoleto.getEstado() - 1);
			txtDataPagamento.setText(String.valueOf(pagamentoBoleto.getDataPagamento()));
			txtDataVencimento.setText(String.valueOf(pagamentoBoleto.getDataVencimento()));
			}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 344);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(0, 0, 0));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JPanel panel = new JPanel();
			panel.setBackground(new Color(211, 61, 48));
			panel.setBounds(0, 0, 555, 105);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Pagamento Com Boleto");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
			lblNewLabel.setBounds(10, 30, 386, 46);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(0, 105, 555, 200);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(120, 131, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(320, 131, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblestado = new JLabel("Estado :");
			lblestado.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblestado.setBounds(10, 25, 85, 21);
			panel_1.add(lblestado);
			
			JLabel lblNewLabel_1_1 = new JLabel("Data de Vencimento :");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 56, 151, 21);
			panel_1.add(lblNewLabel_1_1);
			
			JLabel lblNewLabel_1_2 = new JLabel("Data de Pagamento :");
			lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_2.setBounds(10, 87, 139, 21);
			panel_1.add(lblNewLabel_1_2);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 179, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
			txtDataVencimento = new JTextField();
			txtDataVencimento.setToolTipText("AAAA-MM-DD");
			txtDataVencimento.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtDataVencimento.setColumns(10);
			txtDataVencimento.setBounds(159, 57, 374, 19);
			panel_1.add(txtDataVencimento);
			
			txtDataPagamento = new JTextField();
			txtDataPagamento.setToolTipText("AAAA-MM-DD");
			txtDataPagamento.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtDataPagamento.setColumns(10);
			txtDataPagamento.setBounds(159, 88, 374, 19);
			panel_1.add(txtDataPagamento);
			
			comboBoxEstado = new JComboBox<String>();
			comboBoxEstado.setModel(new DefaultComboBoxModel<String>(EstadoPagamento.enumsToStringArray()));
			comboBoxEstado.setBounds(159, 27, 374, 19);
			panel_1.add(comboBoxEstado);
		}
		
		public PagamentoComBoletoService getPagamentoComBoletoService() {
			return new PagamentoComBoletoService();
		}
		
		public PagamentoComBoleto getPagamentoComBoleto() {
			return new PagamentoComBoleto();
		}
}
