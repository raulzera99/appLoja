package view.pedido;

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
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import config.Constantes;
import dao.ClienteDAO;
import dao.EnderecoDAO;
import dao.PedidoDAO;
import message.ModelResponse;
import models.Cliente;
import models.Endereco;
import models.Pedido;
import persistence.DataBaseConnection;
import services.ClienteService;
import services.EnderecoService;
import services.PedidoService;
import services.errors.ErrorsData;

public class PedidoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextArea textArea;
	JLabel lblMessageInstante;
	JComboBox<String> cbCliente;
	JLabel lblMessageCliente;
	JComboBox<String> cbEndereco;
	JLabel lblMessageEndereco;
	
	private Long idPedido = 0L;
	
	private PedidoService pedidoService;
	private Pedido pedido = null;
	private Cliente cliente = null;
	private Endereco endereco = null;
	
	private ModelResponse<Pedido> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PedidoView frame = new PedidoView();
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
	public PedidoView(Pedido pedido, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Pedido");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(pedido.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(pedido.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(pedido.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idPedido == 0L) {
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
			
			textArea.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					textArea.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
					lblMessageInstante.setVisible(false);
				}
			});
			
			cbCliente.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbCliente.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageCliente.setVisible(false);
				}
			});
			
			cbEndereco.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbEndereco.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageEndereco.setVisible(false);
				}
			});
		}

		@SuppressWarnings("unchecked")
		public void add() {
			pedidoService = getPedidoService();
			pedido = getPedido();
			int i = 1;
			setPedidoFromView();
			
			errors = (ModelResponse<ErrorsData>) pedidoService.validarDadosFromView(pedido);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pedido.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<Pedido>) pedidoService.add(pedido);
					pedido = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}
		}
		
		@SuppressWarnings("unchecked")
		public void update() {
			pedido = getPedido();
			pedidoService = getPedidoService();
			int i = 1;
			pedido.setId(idPedido);
			setPedidoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pedido.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Pedido>) pedidoService.update(pedido);
			}

			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pedido = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
						
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			pedidoService = getPedidoService();
			idPedido = pedido.getId();
			
			setPedidoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pedido.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Pedido>) pedidoService.remove(idPedido);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pedido = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			pedidoService = getPedidoService();
			pedido = getPedido();

			modelResponse = (ModelResponse<Pedido>) pedidoService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pedido = modelResponse.getObject();
				getPedidoFromDataBase();
			}
		}
				
		
		private void limpa() {
			
			idPedido = 0L;
			textArea.setText("");
			cbCliente.setSelectedIndex(-1);
			cbEndereco.setSelectedIndex(-1);
		}
		
		@SuppressWarnings("unchecked")
		private void setPedidoFromView() {
			pedido.setInstante(textArea.getText());	
			
			ModelResponse<Cliente> mrCliente = new ModelResponse<Cliente>();
			mrCliente = (ModelResponse<Cliente>) getClienteService()
					.findByName(cbCliente.getItemAt(cbCliente.getSelectedIndex()));
			cliente = mrCliente.getObject();
			pedido.setCliente(cliente);
			
			ModelResponse<Endereco> mrEndereco = new ModelResponse<Endereco>();
			mrEndereco = (ModelResponse<Endereco>) getEnderecoService()
					.findByName(cbEndereco.getItemAt(cbEndereco.getSelectedIndex()));
			endereco = mrEndereco.getObject();
			pedido.setEnderecoDeEntrega(endereco);
			
			
		}
		
		private void getPedidoFromDataBase() {
			idPedido = pedido.getId();
			textArea.setText(pedido.getInstante());
			cbCliente.setSelectedItem(pedido.getCliente());
			cbEndereco.setSelectedItem(pedido.getEnderecoDeEntrega());
		}
		
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageInstante.setVisible(true);
					lblMessageInstante.setForeground(Color.red);
					lblMessageInstante.setText(erro.getShowMensagemError());
					textArea.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 2) {
					lblMessageCliente.setVisible(true);
					lblMessageCliente.setForeground(Color.red);
					lblMessageCliente.setText(erro.getShowMensagemError());
					cbCliente.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 3) {
					lblMessageEndereco.setVisible(true);
					lblMessageEndereco.setForeground(Color.red);
					lblMessageEndereco.setText(erro.getShowMensagemError());
					cbEndereco.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 424);
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
			
			JLabel lblNewLabel = new JLabel("Pedido");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
			lblNewLabel.setBounds(10, 30, 386, 46);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(0, 105, 555, 258);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(118, 210, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(313, 210, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblInstante = new JLabel("Descrição do instante:");
			lblInstante.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblInstante.setBounds(10, 21, 141, 21);
			panel_1.add(lblInstante);
			
			lblMessageInstante = new JLabel("");
			lblMessageInstante.setBounds(161, 60, 374, 14);
			panel_1.add(lblMessageInstante);
			
			textArea = new JTextArea();
			textArea.setRows(2);
			textArea.setBorder(new LineBorder(Color.DARK_GRAY));
			textArea.setBounds(161, 23, 374, 37);
			panel_1.add(textArea);
			
			JLabel lblCliente = new JLabel("Cliente:");
			lblCliente.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblCliente.setBounds(10, 77, 54, 21);
			panel_1.add(lblCliente);
			
			cbCliente = new JComboBox<String>();
			cbCliente.setModel(new DefaultComboBoxModel <String>(getClienteService().stringListAllClientes()));
			cbCliente.setBounds(81, 123, 448, 22);
			panel_1.add(cbCliente);
			
			cbEndereco = new JComboBox<String>();
			cbEndereco.setModel(new DefaultComboBoxModel <String>(getEnderecoService().stringListAllEnderecos()));
			cbEndereco.setBounds(81, 123, 448, 22);
			panel_1.add(cbEndereco);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 364, 555, 21);
			contentPane.add(panel_2);
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setLayout(null);
			
		}
		
		public PedidoService getPedidoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new PedidoService(em, new PedidoDAO(em));
		}
		
		public Pedido getPedido() {
			return new Pedido();
		}
		
		public ClienteService getClienteService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new ClienteService(em, new ClienteDAO(em));
		}

		public Cliente getCliente() {
			return new Cliente();
		}
		
		public EnderecoService getEnderecoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new EnderecoService(em, new EnderecoDAO(em));
		}
		
		public Endereco getEndereco() {
			return new Endereco();
		}
}
