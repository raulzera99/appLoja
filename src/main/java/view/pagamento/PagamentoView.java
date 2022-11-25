package view.pagamento;

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
import javax.swing.border.EmptyBorder;

import config.Constantes;
import dao.PagamentoDAO;
import dao.PedidoDAO;
import message.ModelResponse;
import models.Pagamento;
import models.Pedido;
import models.enums.EstadoPagamento;
import persistence.DataBaseConnection;
import services.PagamentoService;
import services.PedidoService;
import services.errors.ErrorsData;

public class PagamentoView extends JFrame {
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JComboBox<String> cbEstado;
	JComboBox<String> cbPedido;
	JLabel lblMessageEstado;
	JLabel lblMessagePedido;
	
	private Long idPagamento = 0L;
	
	private PagamentoService pagamentoService;
	private Pagamento pagamento = null;
	private Pedido pedido = null;
	
	private ModelResponse<Pagamento> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PagamentoView frame = new PagamentoView();
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
	public PagamentoView(Pagamento pagamento, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Pagamentos");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(pagamento.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(pagamento.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(pagamento.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idPagamento == 0l) {
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
			
			cbEstado.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbEstado.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageEstado.setVisible(false);
				}
			});
			cbPedido.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbPedido.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessagePedido.setVisible(false);
				}
			});
		}
		
		@SuppressWarnings("unchecked")
		public void add() {
			pagamentoService = getPagamentoService();
			pagamento = getPagamento();
			int i = 1;
			setPagamentoFromView();
			
			errors = (ModelResponse<ErrorsData>) pagamentoService.validarDadosFromView(pagamento);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamento.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<Pagamento>) pagamentoService.add(pagamento);
					pagamento = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}			
		}
		
		@SuppressWarnings("unchecked")
		public void update() {
			pagamento = getPagamento();
			pagamentoService = getPagamentoService();
			int i = 1;
			pagamento.setId(idPagamento);
			setPagamentoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamento.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				modelResponse = (ModelResponse<Pagamento>) pagamentoService.update(pagamento);
			}	
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamento = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			pagamentoService = getPagamentoService();
			idPagamento = pagamento.getId();
			setPagamentoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamento.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Pagamento>) pagamentoService.remove(idPagamento);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamento = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();		
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			pagamentoService = getPagamentoService();
			pagamento = getPagamento();
			modelResponse = (ModelResponse<Pagamento>) pagamentoService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamento = modelResponse.getObject();
				getPagamentoFromDataBase();
			}
			
		}
				
		
		private void limpa() {
			
			idPagamento = 0L;
			cbPedido.setSelectedIndex(-1);;
			cbEstado.setSelectedIndex(-1);;
		}
		
		@SuppressWarnings("unchecked")
		private void setPagamentoFromView() {
			pagamento.setEstado(cbEstado.getSelectedIndex()+1);
			
			ModelResponse<Pedido> mr = new ModelResponse<Pedido>();
			mr = (ModelResponse<Pedido>) getPedidoService()
					.findByName(cbPedido.getItemAt(cbPedido.getSelectedIndex()));
			pedido = mr.getObject();
			pagamento.setPedido(pedido);
		}
		
		private void getPagamentoFromDataBase() {
			idPagamento = pagamento.getId();
			cbEstado.setSelectedIndex(pagamento.getEstado() - 1);
			cbPedido.setSelectedItem(pagamento.getPedido());
		}
		
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageEstado.setVisible(true);
					lblMessageEstado.setForeground(Color.red);
					lblMessageEstado.setText(erro.getShowMensagemError());
					cbEstado.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 2) {
					lblMessagePedido.setVisible(true);
					lblMessagePedido.setForeground(Color.red);
					lblMessagePedido.setText(erro.getShowMensagemError());
					cbPedido.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 365);
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
			
			JLabel lblNewLabel = new JLabel("Pagamentos");
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
			btnSalvar.setBounds(119, 137, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(318, 137, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblNewLabel_1_1 = new JLabel("Estado do pagamento:");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 25, 151, 21);
			panel_1.add(lblNewLabel_1_1);
			
			cbEstado = new JComboBox<String>();
			cbEstado.setModel(new DefaultComboBoxModel<String>(EstadoPagamento.enumsToStringArray()));
			cbEstado.setBounds(159, 26, 374, 19);
			panel_1.add(cbEstado);
			
			lblMessageEstado = new JLabel("");
			lblMessageEstado.setBounds(169, 44, 353, 14);
			panel_1.add(lblMessageEstado);
			
			cbPedido = new JComboBox<String>();
			cbPedido.setModel(new DefaultComboBoxModel<String>(getPedidoService().stringListAllPedidos()));
			cbPedido.setBounds(159, 57, 374, 19);
			panel_1.add(cbPedido);
			
			lblMessagePedido = new JLabel("");
			lblMessagePedido.setBounds(159, 75, 374, 14);
			panel_1.add(lblMessagePedido);
			
			JLabel lblNewLabel_1_1_1 = new JLabel("Instante do pedido:");
			lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_1.setBounds(10, 55, 128, 21);
			panel_1.add(lblNewLabel_1_1_1);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 305, 555, 21);
			contentPane.add(panel_2);
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setLayout(null);
		}
		
		public PagamentoService getPagamentoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new PagamentoService(em, new PagamentoDAO(em));
		}
		
		public Pagamento getPagamento() {
			return new Pagamento();
		}
		
		public PedidoService getPedidoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new PedidoService(em, new PedidoDAO(em));
		}
		
		public Pedido getPedido() {
			return new Pedido();
		}

		public ModelResponse<Pagamento> getModelResponse() {
			return modelResponse;
		}

		public void setModelResponse(ModelResponse<Pagamento> modelResponse) {
			this.modelResponse = modelResponse;
		}
}
