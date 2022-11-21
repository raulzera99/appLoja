package view.cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.persistence.EntityManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.Constantes;
import dao.ClienteDAO;
import message.ModelResponse;
import models.Cliente;
import models.enums.TipoCliente;
import persistence.DataBaseConnection;
import services.ClienteService;
import services.errors.ErrorsData;

public class ClienteView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNome;
	JLabel lblMessageNome;
	JTextField txtEmail;
	JLabel lblEmail;
	JLabel lblMessageEmail;
	JTextField txtCpfOuCnpj;
	JLabel lblCpfOuCnpj;
	JLabel lblMessageCpfOuCnpj;	
	JComboBox<TipoCliente> cbTipoCliente ;
	JLabel lblTipoCliente;
	JLabel lblMessageTipoCliente;
	
	private Long idCliente = 0L;
	
	private ClienteService clienteService;
	private Cliente cliente = null;
	
	private ModelResponse<Cliente> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ClienteView frame = new ClienteView();
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
	public ClienteView(Cliente cliente, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Cliente");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(cliente.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(cliente.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(cliente.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idCliente == 0L) {
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
			
			txtNome.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtNome.setBorder(null);
					lblMessageNome.setVisible(false);
				}
			});
			
			txtEmail.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtEmail.setBorder(null);
					lblMessageEmail.setVisible(false);
				}
			});
			
			txtCpfOuCnpj.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtCpfOuCnpj.setBorder(null);
					lblMessageCpfOuCnpj.setVisible(false);
				}
			});
			
			cbTipoCliente.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbTipoCliente.setBorder(null);
					lblMessageTipoCliente.setVisible(false);
				}
			});
		}

		@SuppressWarnings("unchecked")
		public void add() {
			clienteService = getClienteService();
			cliente = getCliente();
			int i = 1;
			setClienteFromView();
			
			errors = (ModelResponse<ErrorsData>) clienteService.validarDadosFromView(cliente);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+cliente.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<Cliente>) clienteService.add(cliente);
					cliente = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}
		}
		
		@SuppressWarnings("unchecked")
		public void update() {
			cliente = getCliente();
			clienteService = getClienteService();
			int i = 1;
			cliente.setId(idCliente);
			setClienteFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+cliente.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Cliente>) clienteService.update(cliente);
			}

			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				cliente = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
						
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			clienteService = getClienteService();
			idCliente = cliente.getId();
			
			setClienteFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+cliente.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Cliente>) clienteService.remove(idCliente);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				cliente = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			clienteService = getClienteService();
			cliente = getCliente();

			modelResponse = (ModelResponse<Cliente>) clienteService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				cliente = modelResponse.getObject();
				getClienteFromDataBase();
			}
		}
				
		
		private void limpa() {
			
			idCliente = 0L;
			txtNome.setText("");
		}
		
		private void setClienteFromView() {
			cliente.setNome(txtNome.getText());
			cliente.setEmail(txtEmail.getText());
			cliente.setCpfOuCnpj(txtCpfOuCnpj.getText());
			cliente.setTipo(cbTipoCliente.getSelectedIndex()+1);
		}
		
		private void getClienteFromDataBase() {
			idCliente = cliente.getId();
			txtNome.setText(cliente.getNome());
			txtEmail.setText(cliente.getEmail());
			txtCpfOuCnpj.setText(cliente.getCpfOuCnpj());
			cbTipoCliente.setSelectedIndex(cliente.getTipo().getCod());
		}
		
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageNome.setVisible(true);
					lblMessageNome.setForeground(Color.red);
					lblMessageNome.setText(erro.getShowMensagemError());
					txtNome.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 2) {
					lblMessageEmail.setVisible(true);
					lblMessageEmail.setForeground(Color.red);
					lblMessageEmail.setText(erro.getShowMensagemError());
					txtEmail.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 3) {
					lblMessageTipoCliente.setVisible(true);
					lblMessageTipoCliente.setForeground(Color.red);
					lblMessageTipoCliente.setText(erro.getShowMensagemError());
					cbTipoCliente.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 4) {
					lblMessageCpfOuCnpj.setVisible(true);
					lblMessageCpfOuCnpj.setForeground(Color.red);
					lblMessageCpfOuCnpj.setText(erro.getShowMensagemError());
					txtCpfOuCnpj.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 412);
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
			
			JLabel lblNewLabel = new JLabel("Cliente");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
			lblNewLabel.setBounds(10, 30, 386, 46);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(0, 105, 555, 246);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(122, 184, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(317, 184, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblNome = new JLabel("Nome : ");
			lblNome.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNome.setBounds(10, 21, 62, 21);
			panel_1.add(lblNome);
			
			txtNome = new JTextField();
			txtNome.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNome.setColumns(10);
			txtNome.setBounds(82, 22, 451, 19);
			panel_1.add(txtNome);
			
			lblMessageNome = new JLabel("");
			lblMessageNome.setBounds(82, 42, 451, 14);
			panel_1.add(lblMessageNome);
			
			lblEmail = new JLabel("Email : ");
			lblEmail.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblEmail.setBounds(10, 53, 62, 21);
			panel_1.add(lblEmail);
			
			txtEmail = new JTextField();
			txtEmail.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtEmail.setColumns(10);
			txtEmail.setBounds(82, 54, 451, 19);
			panel_1.add(txtEmail);
			
			lblMessageEmail = new JLabel("");
			lblMessageEmail.setBounds(82, 74, 451, 14);
			panel_1.add(lblMessageEmail);
			
			lblTipoCliente = new JLabel("Tipo do cliente : ");
			lblTipoCliente.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblTipoCliente.setBounds(10, 85, 105, 21);
			panel_1.add(lblTipoCliente);
			
			lblMessageTipoCliente = new JLabel("");
			lblMessageTipoCliente.setBounds(82, 106, 451, 14);
			panel_1.add(lblMessageTipoCliente);
			
			cbTipoCliente = new JComboBox<TipoCliente>();
			cbTipoCliente.setBounds(122, 85, 411, 22);
			panel_1.add(cbTipoCliente);
			
			lblCpfOuCnpj = new JLabel("CPF/CNPJ : ");
			lblCpfOuCnpj.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblCpfOuCnpj.setBounds(10, 117, 78, 21);
			panel_1.add(lblCpfOuCnpj);
			
			txtCpfOuCnpj = new JTextField();
			txtCpfOuCnpj.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtCpfOuCnpj.setColumns(10);
			txtCpfOuCnpj.setBounds(98, 118, 435, 19);
			panel_1.add(txtCpfOuCnpj);
			
			lblMessageCpfOuCnpj = new JLabel("");
			lblMessageCpfOuCnpj.setBounds(82, 138, 451, 14);
			panel_1.add(lblMessageCpfOuCnpj);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 352, 555, 21);
			contentPane.add(panel_2);
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setLayout(null);
			
		}
		
		public ClienteService getClienteService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new ClienteService(em, new ClienteDAO(em));
		}
		
		public Cliente getCliente() {
			return new Cliente();
		}
}
