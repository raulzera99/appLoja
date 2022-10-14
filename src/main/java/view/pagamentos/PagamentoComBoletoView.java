package view.pagamentos;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.Constantes;
import models.PagamentoComBoleto;
import models.enums.EstadoPagamento;
import services.PagamentoBoletoService;

public class PagamentoComBoletoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	private JTextField txtEstado;
	private JTextField txtDataVencimento;
	private JTextField txtDataPagamento;
	
	private Long idPagBoleto = 0l;
	
	private PagamentoBoletoService pagamentoBoletoService;
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
			consultarPagamentoBoleto(pagamentoBoleto.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			consultarPagamentoBoleto(pagamentoBoleto.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			consultarPagamentoBoleto(pagamentoBoleto.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idPagBoleto == 0l) {
						salvarPagBoleto();
					}else {
						alterarPagBoleto();
					}
				}
			});
			
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		
		public void salvarPagBoleto() {
			pagamentoBoletoService = getPagBoletoService();
			pagamentoBoleto = getPagBoleto();
			
			setPagamentoBoletoFromView();
			pagamentoBoletoService.addPagamentoBoleto(pagamentoBoleto);
			
			limpa();
			
			
		}
		
		public void alterarPagBoleto() {
			pagamentoBoleto = getPagBoleto();
			pagamentoBoletoService = getPagBoletoService();
			
			pagamentoBoleto.setId(idPagBoleto);
			setPagamentoBoletoFromView();
			
			pagamentoBoletoService.updatePagamentoBoleto(pagamentoBoleto);
			limpa();
		}
		
		public void excluirPagBoleto() {
			pagamentoBoletoService = getPagBoletoService();
			pagamentoBoletoService.removePagamentoComBoleto(idPagBoleto);
			
			limpa();
		}
		
		public void consultarPagamentoBoleto(Long id) {
			pagamentoBoletoService = getPagBoletoService();
			pagamentoBoleto = getPagBoleto();
			
			pagamentoBoleto = pagamentoBoletoService.searchPagamentoComBoletoById(id);
			
			getPagamentoBoletoFromDataBase();
		}
				
		
		private void limpa() {
			
			idPagBoleto = 0l;
			txtDataPagamento.setText("");
			txtDataVencimento.setText("");
			txtEstado.setText("");
		}
		
		private void setPagamentoBoletoFromView() {
			pagamentoBoleto.setId(idPagBoleto);
			pagamentoBoleto.setEstado(Integer.parseInt(txtEstado.getText()));
			pagamentoBoleto.setDataPagamento(Date.valueOf(txtDataPagamento.getText()));
			pagamentoBoleto.setDataVencimento(Date.valueOf(txtDataVencimento.getText()));
			
		}
		
		private void getPagamentoBoletoFromDataBase() {
			idPagBoleto = pagamentoBoleto.getId();
			
			txtEstado.setText((EstadoPagamento.toEnum(pagamentoBoleto.getEstado()).getDescricao()) );
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
			
			txtEstado = new JTextField();
			txtEstado.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtEstado.setBounds(159, 26, 374, 19);
			panel_1.add(txtEstado);
			txtEstado.setColumns(10);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 179, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
			txtDataVencimento = new JTextField();
			txtDataVencimento.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtDataVencimento.setColumns(10);
			txtDataVencimento.setBounds(159, 57, 374, 19);
			panel_1.add(txtDataVencimento);
			
			txtDataPagamento = new JTextField();
			txtDataPagamento.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtDataPagamento.setColumns(10);
			txtDataPagamento.setBounds(159, 88, 374, 19);
			panel_1.add(txtDataPagamento);
		}
		
		public PagamentoBoletoService getPagBoletoService() {
			return new PagamentoBoletoService();
		}
		
		public PagamentoComBoleto getPagBoleto() {
			return new PagamentoComBoleto();
		}
	

}
