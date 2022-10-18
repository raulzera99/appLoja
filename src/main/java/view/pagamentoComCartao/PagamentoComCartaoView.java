package view.pagamentoComCartao;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import models.PagamentoComCartao;
import models.enums.EstadoPagamento;
import services.PagamentoComCartaoService;

public class PagamentoComCartaoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JComboBox<String> comboBoxEstado;
	JTextField txtNumeroDeParcelas;
	
	private Long idPagamentoComCartao = 0l;
	
	private PagamentoComCartaoService pagamentoCartaoService;
	private PagamentoComCartao pagamentoCartao;
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PagamentoComCartaoView frame = new PagamentoComCartaoView();
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
	public PagamentoComCartaoView(PagamentoComCartao pagamentoCartao, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Pagamento com Boleto");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(pagamentoCartao.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(pagamentoCartao.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(pagamentoCartao.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idPagamentoComCartao == 0l) {
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
			pagamentoCartaoService = getPagamentoComCartaoService();
			pagamentoCartao = getPagamentoComCartao();
			
			setPagamentoCartaoFromView();
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoCartao.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				pagamentoCartaoService.add(pagamentoCartao);	
				dispose();
				idPagamentoComCartao = 0L;
				limpa();
			}			
			
		}
		
		public void update() {
			pagamentoCartao = getPagamentoComCartao();
			pagamentoCartaoService = getPagamentoComCartaoService();
			
			pagamentoCartao.setId(idPagamentoComCartao);
			setPagamentoCartaoFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoCartao.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				pagamentoCartaoService.update(pagamentoCartao);
				dispose();
				idPagamentoComCartao = 0L;
				limpa();
			}
			
		}
		
		public void remove() {
			pagamentoCartaoService = getPagamentoComCartaoService();
			PagamentoComCartao pagamento = new PagamentoComCartao();
			pagamento = pagamentoCartaoService.findById(idPagamentoComCartao);
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamento.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				pagamentoCartaoService.remove(pagamento);
				dispose();
				idPagamentoComCartao = 0L;
				limpa();
			}
		}
		
		public void findById(Long id) {
			pagamentoCartaoService = getPagamentoComCartaoService();
			pagamentoCartao = getPagamentoComCartao();
			
			pagamentoCartao = pagamentoCartaoService.findById(id);
			
			getPagamentoCartaoFromDataBase();
		}
				
		
		private void limpa() {
			
			idPagamentoComCartao = 0l;
			txtNumeroDeParcelas.setText("");
			comboBoxEstado.setSelectedIndex(-1);
		}
		
		private void setPagamentoCartaoFromView() {
			pagamentoCartao.setId(idPagamentoComCartao);
			pagamentoCartao.setEstado(comboBoxEstado.getSelectedIndex()+1);
			pagamentoCartao.setNumeroDeParcelas(Integer.parseInt(txtNumeroDeParcelas.getText()));
			
		}
		
		private void getPagamentoCartaoFromDataBase() {
			idPagamentoComCartao = pagamentoCartao.getId();
			comboBoxEstado.setSelectedIndex(pagamentoCartao.getEstado() - 1);
			txtNumeroDeParcelas.setText(String.valueOf(pagamentoCartao.getNumeroDeParcelas()));
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
			
			JLabel lblNewLabel = new JLabel("Pagamento Com Cartão");
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
			
			JLabel lblNewLabel_1_1 = new JLabel("Número de parcelas :");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 56, 151, 21);
			panel_1.add(lblNewLabel_1_1);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 179, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
			txtNumeroDeParcelas = new JTextField();
			txtNumeroDeParcelas.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNumeroDeParcelas.setColumns(10);
			txtNumeroDeParcelas.setBounds(159, 57, 374, 19);
			panel_1.add(txtNumeroDeParcelas);
			
			comboBoxEstado = new JComboBox<String>();
			comboBoxEstado.setModel(new DefaultComboBoxModel<String>(EstadoPagamento.enumsToStringArray()));
			comboBoxEstado.setBounds(159, 27, 374, 19);
			panel_1.add(comboBoxEstado);
			
		}
		
		public PagamentoComCartaoService getPagamentoComCartaoService() {
			return new PagamentoComCartaoService();
		}
		
		public PagamentoComCartao getPagamentoComCartao() {
			return new PagamentoComCartao();
		}
	

}
