package view.pagamentoComBoleto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import message.ModelResponse;
import models.PagamentoComBoleto;
import models.enums.EstadoPagamento;
import services.PagamentoComBoletoService;
import services.errors.ErrorsData;

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
	JLabel lblMessageDataVencimento;
	JLabel lblMessageDataPagamento;
	
	private Long idPagamentoComBoleto = 0l;
	
	private PagamentoComBoletoService pagamentoBoletoService;
	private PagamentoComBoleto pagamentoBoleto;
	
	private ModelResponse<PagamentoComBoleto> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	
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
					if(idPagamentoComBoleto == 0L) {
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
		
		@SuppressWarnings("unchecked")
		public void add() {
			pagamentoBoletoService = getPagamentoComBoletoService();
			pagamentoBoleto = getPagamentoComBoleto();
			int i = 1;
			setPagamentoBoletoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoBoleto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				errors = (ModelResponse<ErrorsData>) pagamentoBoletoService.validarDadosFromView(pagamentoBoleto);
			}
			
			if(errors.isError()) {
				showErrorFromServidor();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				
			}
			else {

				modelResponse = (ModelResponse<PagamentoComBoleto>) pagamentoBoletoService.add(pagamentoBoleto);
				pagamentoBoleto = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
			}
			

			pagamentoBoletoService.add(pagamentoBoleto);	
			limpa();

						
		}
		
		@SuppressWarnings("unchecked")
		public void update() {
			pagamentoBoleto = getPagamentoComBoleto();
			pagamentoBoletoService = getPagamentoComBoletoService();
			int i = 1;
			pagamentoBoleto.setId(idPagamentoComBoleto);
			setPagamentoBoletoFromView();
			
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoBoleto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<PagamentoComBoleto>) pagamentoBoletoService.update(pagamentoBoleto);
			}

			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamentoBoleto = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			pagamentoBoletoService.update(pagamentoBoleto);
			limpa();
						
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			pagamentoBoletoService = getPagamentoComBoletoService();
			setPagamentoBoletoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoBoleto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<PagamentoComBoleto>) pagamentoBoletoService.remove(pagamentoBoleto);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamentoBoleto = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			pagamentoBoletoService.remove(pagamentoBoleto);
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			pagamentoBoletoService = getPagamentoComBoletoService();
			pagamentoBoleto = getPagamentoComBoleto();
			
			modelResponse = (ModelResponse<PagamentoComBoleto>) pagamentoBoletoService.findById(pagamentoBoleto.getId());
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamentoBoleto = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Encontrado", JOptionPane.INFORMATION_MESSAGE);
			}
			
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
			pagamentoBoleto.setDataPagamento(txtDataPagamento.getText());
			pagamentoBoleto.setDataVencimento(txtDataVencimento.getText());
			
		}
		
		private void getPagamentoBoletoFromDataBase() {
			idPagamentoComBoleto = pagamentoBoleto.getId();
			
			comboBoxEstado.setSelectedIndex(pagamentoBoleto.getEstado() - 1);
			txtDataPagamento.setText(pagamentoBoleto.getDataPagamento());
			txtDataVencimento.setText(pagamentoBoleto.getDataVencimento());
		}
		
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageDataVencimento.setVisible(true);
					lblMessageDataVencimento.setForeground(Color.red);
					lblMessageDataVencimento.setText(erro.getShowMensagemError());
					lblMessageDataVencimento.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 2) {
					lblMessageDataPagamento.setVisible(true);
					lblMessageDataPagamento.setForeground(Color.red);
					lblMessageDataPagamento.setText(erro.getShowMensagemError());
				}
			}
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
			comboBoxEstado.setToolTipText("");
			comboBoxEstado.setModel(new DefaultComboBoxModel<String>(EstadoPagamento.enumsToStringArray()));
			comboBoxEstado.setBounds(159, 27, 374, 19);
			panel_1.add(comboBoxEstado);
			
			lblMessageDataVencimento = new JLabel("");
			lblMessageDataVencimento.setBounds(159, 75, 374, 14);
			panel_1.add(lblMessageDataVencimento);
			
			lblMessageDataPagamento = new JLabel("");
			lblMessageDataPagamento.setBounds(159, 100, 374, 14);
			panel_1.add(lblMessageDataPagamento);
		}
		
		public PagamentoComBoletoService getPagamentoComBoletoService() {
			return new PagamentoComBoletoService();
		}
		
		public PagamentoComBoleto getPagamentoComBoleto() {
			return new PagamentoComBoleto();
		}
		
		public ModelResponse<PagamentoComBoleto> getModelResponse() {
			return modelResponse;
		}

		public void setModelResponse(ModelResponse<PagamentoComBoleto> modelResponse) {
			this.modelResponse = modelResponse;
		}
}
