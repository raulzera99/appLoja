package view.pagamentoComBoleto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.persistence.EntityManager;
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
import dao.PagamentoComBoletoDAO;
import dao.PedidoDAO;
import message.ModelResponse;
import models.PagamentoComBoleto;
import models.Pedido;
import models.enums.EstadoPagamento;
import persistence.DataBaseConnection;
import services.PagamentoComBoletoService;
import services.PedidoService;
import services.errors.ErrorsData;

public class PagamentoComBoletoView extends JFrame {

	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtDataVencimento;
	JTextField txtDataPagamento;
	JComboBox<String> cbPedido;
	JComboBox<String> comboBoxEstado;
	JLabel lblMessageDataVencimento;
	JLabel lblMessageDataPagamento;
	JLabel lblMessageEstado;
	JLabel lblMessagePedido;
	
	private Long idPagamentoComBoleto = 0L;
	
	private PagamentoComBoletoService pagamentoBoletoService;
	private PagamentoComBoleto pagamentoBoleto = null;
	private Pedido pedido = null;
	
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
			btnCancelar.setBounds(225, 152, 114, 37);
			btnCancelar.setText("Sair");
		}
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idPagamentoComBoleto == 0L) {
						add();
					}else if(btnSalvar.getText() == "Alterar"){
						update();
					}
					else if(btnSalvar.getText() == "Excluir") {
						remove();
					}
				}
			});
			
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
			comboBoxEstado.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					comboBoxEstado.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageEstado.setVisible(false);
				}
			});
			
			cbPedido.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbPedido.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
					lblMessagePedido.setVisible(false);
				}
			});
			
			txtDataPagamento.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtDataPagamento.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageDataPagamento.setVisible(false);
				}
			});
			
			txtDataVencimento.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtDataVencimento.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageDataVencimento.setVisible(false);
				}
			});
		}
		
		@SuppressWarnings("unchecked")
		public void add() {
			pagamentoBoletoService = getPagamentoComBoletoService();
			pagamentoBoleto = getPagamentoComBoleto();
			int i = 1;
			setPagamentoBoletoFromView();
			
			errors = (ModelResponse<ErrorsData>) pagamentoBoletoService.validarDadosFromView(pagamentoBoleto);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoBoleto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<PagamentoComBoleto>) pagamentoBoletoService.add(pagamentoBoleto);
					pagamentoBoleto = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}
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
			
			limpa();
						
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			pagamentoBoletoService = getPagamentoComBoletoService();
			idPagamentoComBoleto = pagamentoBoleto.getId();
			setPagamentoBoletoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoBoleto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<PagamentoComBoleto>) pagamentoBoletoService.remove(idPagamentoComBoleto);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamentoBoleto = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			pagamentoBoletoService = getPagamentoComBoletoService();
			pagamentoBoleto = getPagamentoComBoleto();

			modelResponse = (ModelResponse<PagamentoComBoleto>) pagamentoBoletoService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamentoBoleto = modelResponse.getObject();
				getPagamentoBoletoFromDataBase();
			}
		}
				
		private void limpa() {
			
			idPagamentoComBoleto = 0L;
			txtDataPagamento.setText("");
			txtDataVencimento.setText("");
			comboBoxEstado.setSelectedIndex(-1);
		}
		
		@SuppressWarnings("unchecked")
		private void setPagamentoBoletoFromView() {
			pagamentoBoleto.setEstado(comboBoxEstado.getSelectedIndex()+1);
			pagamentoBoleto.setDataPagamento(txtDataPagamento.getText());
			pagamentoBoleto.setDataVencimento(txtDataVencimento.getText());
			ModelResponse<Pedido> mrPedido = new ModelResponse<Pedido>();
			mrPedido = (ModelResponse<Pedido>) getPedidoService()
					.findByName(cbPedido.getItemAt(cbPedido.getSelectedIndex()));
			pedido = mrPedido.getObject();
			
			pagamentoBoleto.setPedido(pedido);
		}
		
		private void getPagamentoBoletoFromDataBase() {
			idPagamentoComBoleto = pagamentoBoleto.getId();
			comboBoxEstado.setSelectedIndex(pagamentoBoleto.getEstado() - 1);
			txtDataPagamento.setText(pagamentoBoleto.getDataPagamento());
			txtDataVencimento.setText(pagamentoBoleto.getDataVencimento());
			cbPedido.setSelectedItem(pagamentoBoleto.getPedido());
		}
		
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessagePedido.setVisible(true);
					lblMessagePedido.setForeground(Color.red);
					lblMessagePedido.setText(erro.getShowMensagemError());
					cbPedido.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 2) {
					lblMessageEstado.setVisible(true);
					lblMessageEstado.setForeground(Color.red);
					lblMessageEstado.setText(erro.getShowMensagemError());
					comboBoxEstado.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 3) {
					lblMessageDataVencimento.setVisible(true);
					lblMessageDataVencimento.setForeground(Color.red);
					lblMessageDataVencimento.setText(erro.getShowMensagemError());
					txtDataVencimento.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 4) {
					lblMessageDataPagamento.setVisible(true);
					lblMessageDataPagamento.setForeground(Color.red);
					lblMessageDataPagamento.setText(erro.getShowMensagemError());
					txtDataPagamento.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 364);
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
			btnSalvar.setBounds(120, 152, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(320, 152, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblestado = new JLabel("Estado :");
			lblestado.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblestado.setBounds(10, 45, 85, 21);
			panel_1.add(lblestado);
			
			JLabel lblNewLabel_1_1 = new JLabel("Data de Vencimento :");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 76, 151, 21);
			panel_1.add(lblNewLabel_1_1);
			
			JLabel lblNewLabel_1_2 = new JLabel("Data de Pagamento :");
			lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_2.setBounds(10, 107, 139, 21);
			panel_1.add(lblNewLabel_1_2);
			
			txtDataVencimento = new JTextField();
			txtDataVencimento.setToolTipText("AAAA-MM-DD");
			txtDataVencimento.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtDataVencimento.setColumns(10);
			txtDataVencimento.setBounds(159, 77, 374, 19);
			panel_1.add(txtDataVencimento);
			
			txtDataPagamento = new JTextField();
			txtDataPagamento.setToolTipText("AAAA-MM-DD");
			txtDataPagamento.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtDataPagamento.setColumns(10);
			txtDataPagamento.setBounds(159, 108, 374, 19);
			panel_1.add(txtDataPagamento);
			
			comboBoxEstado = new JComboBox<String>();
			comboBoxEstado.setToolTipText("");
			comboBoxEstado.setModel(new DefaultComboBoxModel<String>(EstadoPagamento.enumsToStringArray()));
			comboBoxEstado.setBounds(159, 47, 374, 19);
			panel_1.add(comboBoxEstado);
			
			cbPedido = new JComboBox<String>();
			cbPedido.setModel(new DefaultComboBoxModel<String>(getPedidoService().stringListAllPedidos()));
			cbPedido.setBounds(159, 15, 374, 19);
			panel_1.add(cbPedido);
			
			lblMessagePedido = new JLabel("");
			lblMessagePedido.setBounds(159, 36, 374, 14);
			panel_1.add(lblMessagePedido);
			
			JLabel lblNewLabel_1_1_1 = new JLabel("Descrição do pedido:");
			lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_1.setBounds(10, 11, 139, 21);
			panel_1.add(lblNewLabel_1_1_1);
			
			lblMessageDataVencimento = new JLabel("");
			lblMessageDataVencimento.setBounds(159, 95, 374, 14);
			panel_1.add(lblMessageDataVencimento);
			
			lblMessageDataPagamento = new JLabel("");
			lblMessageDataPagamento.setBounds(159, 126, 374, 14);
			panel_1.add(lblMessageDataPagamento);
			
			lblMessageEstado = new JLabel("");
			lblMessageEstado.setBounds(159, 64, 374, 14);
			panel_1.add(lblMessageEstado);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 304, 555, 21);
			contentPane.add(panel_2);
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setLayout(null);
		}
		
		public PagamentoComBoletoService getPagamentoComBoletoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new PagamentoComBoletoService(em, new PagamentoComBoletoDAO(em));
		}
		
		public PagamentoComBoleto getPagamentoComBoleto() {
			return new PagamentoComBoleto();
		}
		
		public PedidoService getPedidoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new PedidoService(em, new PedidoDAO(em));
		}
		
		public Pedido getPedido() {
			return new Pedido();
		}
		
		public ModelResponse<PagamentoComBoleto> getModelResponse() {
			return modelResponse;
		}

		public void setModelResponse(ModelResponse<PagamentoComBoleto> modelResponse) {
			this.modelResponse = modelResponse;
		}
}
