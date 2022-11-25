package view.endereco;

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
import dao.CidadeDAO;
import dao.ClienteDAO;
import dao.EnderecoDAO;
import message.ModelResponse;
import models.Cidade;
import models.Cliente;
import models.Endereco;
import persistence.DataBaseConnection;
import services.CidadeService;
import services.ClienteService;
import services.EnderecoService;
import services.errors.ErrorsData;

public class EnderecoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNumero;
	JLabel lblMessageNum;
	private JTextField txtBairro;
	JLabel lblMessageBairro;
	private JTextField txtCEP;
	JLabel lblMessageCEP;
	JComboBox<String> cbCidade;
	JLabel lblMessageCidade;
	JComboBox<String> cbCliente;
	JLabel lblMessageCliente;
	private Long idEndereco = 0L;
	
	private EnderecoService enderecoService;
	private Endereco endereco = null;
	private Cliente cliente = null;
	private Cidade cidade = null;
	
	private ModelResponse<Endereco> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					EnderecoView frame = new EnderecoView();
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
	public EnderecoView(Endereco endereco, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Endereço");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(endereco.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(endereco.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(endereco.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idEndereco == 0l) {
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
			
			txtNumero.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtNumero.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageNum.setVisible(false);
				}
			});
			
			txtBairro.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtBairro.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageBairro.setVisible(false);
				}
			});
			
			txtCEP.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtCEP.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageCEP.setVisible(false);
				}
			});
		}
		
		@SuppressWarnings("unchecked")
		public void add() {
			enderecoService = getEnderecoService();
			endereco = getEndereco();
			int i = 1;

			setEnderecoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+endereco.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<Endereco>) enderecoService.add(endereco);
					endereco = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}			
		}

		@SuppressWarnings("unchecked")
		public void update() {
			endereco = getEndereco();
			enderecoService = getEnderecoService();
			int i = 1;
			endereco.setId(idEndereco);
			setEnderecoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+endereco.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				modelResponse = (ModelResponse<Endereco>) enderecoService.update(endereco);
			}		
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				endereco = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			enderecoService = getEnderecoService();
			idEndereco = endereco.getId();
			setEnderecoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+endereco.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Endereco>) enderecoService.remove(idEndereco);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				endereco = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			enderecoService = getEnderecoService();
			endereco = getEndereco();
			
			modelResponse = (ModelResponse<Endereco>) enderecoService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				endereco = modelResponse.getObject();
				getEnderecoFromDataBase();
			}
		}
				
		
		private void limpa() {
			
			idEndereco = 0l;
			txtNumero.setText("");
			cbCidade.setSelectedIndex(-1);
			cbCliente.setSelectedIndex(-1);
		}
		
		@SuppressWarnings("unchecked")
		private void setEnderecoFromView() {
			endereco.setNumero(txtNumero.getText());		
			endereco.setBairro(txtBairro.getText());
			endereco.setCep(txtCEP.getText());
			ModelResponse<Cidade> mrCidade = new ModelResponse<Cidade>();
			mrCidade = (ModelResponse<Cidade>) getCidadeService()
					.findByName(cbCidade.getItemAt(cbCidade.getSelectedIndex()));
			cidade = mrCidade.getObject();
			endereco.setCidade(cidade);
			
			ModelResponse<Cliente> mrCliente = new ModelResponse<Cliente>();
			mrCliente = (ModelResponse<Cliente>) getClienteService()
					.findByName(cbCliente.getItemAt(cbCliente.getSelectedIndex()));
			cliente = mrCliente.getObject();
			endereco.setCliente(cliente);
		}
		
		private void getEnderecoFromDataBase() {
			idEndereco = endereco.getId();
			txtNumero.setText(String.valueOf(endereco.getNumero()));
			txtBairro.setText(String.valueOf(endereco.getBairro()));
			txtCEP.setText(String.valueOf(endereco.getCep()));
			cbCidade.setSelectedItem(endereco.getCidade());
			cbCliente.setSelectedItem(endereco.getCliente());
		}

		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageNum.setVisible(true);
					lblMessageNum.setForeground(Color.red);
					lblMessageNum.setText(erro.getShowMensagemError());
					txtNumero.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 2) {
					lblMessageBairro.setVisible(true);
					lblMessageBairro.setForeground(Color.red);
					lblMessageBairro.setText(erro.getShowMensagemError());
					txtBairro.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 3) {
					lblMessageCEP.setVisible(true);
					lblMessageCEP.setForeground(Color.red);
					lblMessageCEP.setText(erro.getShowMensagemError());
					txtCEP.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 4) {
					lblMessageCidade.setVisible(true);
					lblMessageCidade.setForeground(Color.red);
					lblMessageCidade.setText(erro.getShowMensagemError());
					cbCidade.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 5) {
					lblMessageCliente.setVisible(true);
					lblMessageCliente.setForeground(Color.red);
					lblMessageCliente.setText(erro.getShowMensagemError());
					cbCliente.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 420);
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
			
			JLabel lblNewLabel = new JLabel("Endereço");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
			lblNewLabel.setBounds(10, 30, 386, 46);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(0, 105, 555, 254);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(120, 206, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(315, 206, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblNewLabel_1_1 = new JLabel("Número: ");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 25, 61, 21);
			panel_1.add(lblNewLabel_1_1);
			
			txtNumero = new JTextField();
			txtNumero.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNumero.setColumns(10);
			txtNumero.setBounds(81, 26, 448, 19);
			panel_1.add(txtNumero);
			
			JLabel lblNewLabel_1_1_1 = new JLabel("Bairro: ");
			lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_1.setBounds(10, 57, 61, 21);
			panel_1.add(lblNewLabel_1_1_1);
			
			txtBairro = new JTextField();
			txtBairro.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtBairro.setColumns(10);
			txtBairro.setBounds(81, 58, 448, 19);
			panel_1.add(txtBairro);
			
			JLabel lblNewLabel_1_1_2 = new JLabel("CEP: ");
			lblNewLabel_1_1_2.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_2.setBounds(10, 89, 64, 21);
			panel_1.add(lblNewLabel_1_1_2);
			
			txtCEP = new JTextField();
			txtCEP.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtCEP.setColumns(10);
			txtCEP.setBounds(81, 90, 448, 19);
			panel_1.add(txtCEP);
			
			lblMessageNum = new JLabel("");
			lblMessageNum.setBounds(81, 45, 448, 14);
			panel_1.add(lblMessageNum);
			
			lblMessageBairro = new JLabel("");
			lblMessageBairro.setBounds(81, 76, 448, 14);
			panel_1.add(lblMessageBairro);
			
			lblMessageCEP = new JLabel("");
			lblMessageCEP.setBounds(81, 110, 448, 14);
			panel_1.add(lblMessageCEP);
			
			JLabel lblNewLabel_1_1_2_1 = new JLabel("Cidade:");
			lblNewLabel_1_1_2_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_2_1.setBounds(10, 121, 61, 21);
			panel_1.add(lblNewLabel_1_1_2_1);
			
			lblMessageCidade = new JLabel("");
			lblMessageCidade.setBounds(81, 145, 448, 14);
			panel_1.add(lblMessageCidade);
			
			cbCidade = new JComboBox<String>();
			cbCidade.setModel(new DefaultComboBoxModel <String>(getCidadeService().stringListAllCidades()));
			cbCidade.setBounds(81, 123, 448, 22);
			panel_1.add(cbCidade);
			
			JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Cliente:");
			lblNewLabel_1_1_2_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_2_1_1.setBounds(10, 157, 61, 21);
			panel_1.add(lblNewLabel_1_1_2_1_1);
			
			cbCliente = new JComboBox<String>();
			cbCliente.setModel(new DefaultComboBoxModel <String>(getClienteService().stringListAllClientes()));
			cbCliente.setBounds(81, 159, 448, 22);
			panel_1.add(cbCliente);
			
			lblMessageCliente = new JLabel("");
			lblMessageCliente.setBounds(81, 181, 448, 14);
			panel_1.add(lblMessageCliente);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 360, 555, 21);
			contentPane.add(panel_2);
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setLayout(null);
		}
		
		public EnderecoService getEnderecoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new EnderecoService(em, new EnderecoDAO(em));
		}
		
		public Endereco getEndereco() {
			return new Endereco();
		}

		public CidadeService getCidadeService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new CidadeService(em, new CidadeDAO(em));
		}

		public Cidade getCidade() {
			return new Cidade();
		}

		public ClienteService getClienteService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new ClienteService(em, new ClienteDAO(em));
		}

		public Cliente getCliente() {
			return new Cliente();
		}
}
