package view.telefone;

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
import dao.ClienteDAO;
import dao.TelefoneDAO;
import message.ModelResponse;
import models.Cliente;
import models.Telefone;
import persistence.DataBaseConnection;
import services.ClienteService;
import services.TelefoneService;
import services.errors.ErrorsData;

public class TelefoneView extends JFrame {
	
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNumero;
	JLabel lblMessageNum;
	JComboBox<String> cbCliente;
	JLabel lblMessageCliente;
	
	private Long idTelefone = 0L;
	
	private TelefoneService telefoneService;
	private Telefone telefone = null;
	private Cliente cliente = null;

	private ModelResponse<Telefone> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TelefoneView frame = new TelefoneView();
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
	public TelefoneView(Telefone telefone, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Pagamento com Boleto");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(telefone.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(telefone.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(telefone.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 111, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idTelefone == 0L) {
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
		}
		@SuppressWarnings("unchecked")
		public void add() {
			telefoneService = getTelefoneService();
			telefone = getTelefone();
			int i = 1;
			setTelefoneFromView();
			
			errors = (ModelResponse<ErrorsData>) telefoneService.validarDadosFromView(telefone);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+telefone.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<Telefone>) telefoneService.add(telefone);
					telefone = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}			
		}
		
		@SuppressWarnings("unchecked")
		public void update() {
			telefoneService = getTelefoneService();
			telefone = getTelefone();
			int i = 1;
			telefone.setId(idTelefone);
			setTelefoneFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+telefone.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				modelResponse = (ModelResponse<Telefone>) telefoneService.update(telefone);
			}	
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				telefone = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			telefoneService = getTelefoneService();
			idTelefone = telefone.getId();
			setTelefoneFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+telefone.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Telefone>) telefoneService.remove(idTelefone);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				telefone = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();		
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			telefoneService = getTelefoneService();
			telefone = getTelefone();
			modelResponse = (ModelResponse<Telefone>) telefoneService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				telefone = modelResponse.getObject();
				getTelefoneFromDataBase();
			}
			
		}
				
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageNum.setVisible(true);
					lblMessageNum.setForeground(Color.red);
					lblMessageNum.setText(erro.getShowMensagemError());
					txtNumero.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 5) {
					lblMessageCliente.setVisible(true);
					lblMessageCliente.setForeground(Color.red);
					lblMessageCliente.setText(erro.getShowMensagemError());
					cbCliente.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}
		}
		
		private void limpa() {
			
			idTelefone = 0L;
			txtNumero.setText("");
		}
		
		@SuppressWarnings("unchecked")
		private void setTelefoneFromView() {
			telefone.setNumero(txtNumero.getText());	
			ModelResponse<Cliente> mrCliente = new ModelResponse<Cliente>();
			mrCliente = (ModelResponse<Cliente>) getClienteService()
					.findByName(cbCliente.getItemAt(cbCliente.getSelectedIndex()));
			cliente = mrCliente.getObject();
			telefone.setCliente(cliente);
		}
		
		private void getTelefoneFromDataBase() {
			idTelefone = telefone.getId();
			txtNumero.setText(String.valueOf(telefone.getNumero()));
			cbCliente.setSelectedItem(telefone.getCliente().getNome());
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 324);
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
			
			JLabel lblNewLabel = new JLabel("Telefone");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
			lblNewLabel.setBounds(10, 30, 386, 46);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(0, 105, 555, 158);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(119, 110, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(318, 110, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblNum = new JLabel("Número: ");
			lblNum.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNum.setBounds(10, 25, 59, 21);
			panel_1.add(lblNum);
			
			JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Cliente:");
			lblNewLabel_1_1_2_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_2_1_1.setBounds(10, 57, 61, 21);
			panel_1.add(lblNewLabel_1_1_2_1_1);
			
			cbCliente = new JComboBox<String>();
			cbCliente.setModel(new DefaultComboBoxModel <String>(getClienteService().stringListAllClientes()));
			cbCliente.setBounds(81, 59, 452, 22);
			panel_1.add(cbCliente);
			
			lblMessageCliente = new JLabel("");
			lblMessageCliente.setBounds(81, 81, 448, 14);
			panel_1.add(lblMessageCliente);
			
			txtNumero = new JTextField();
			txtNumero.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNumero.setColumns(10);
			txtNumero.setBounds(79, 26, 454, 19);
			panel_1.add(txtNumero);
			
			lblMessageNum = new JLabel("");
			lblMessageNum.setBounds(79, 45, 454, 14);
			panel_1.add(lblMessageNum);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 264, 555, 21);
			contentPane.add(panel_2);
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setLayout(null);
		}
		
		public TelefoneService getTelefoneService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new TelefoneService(em, new TelefoneDAO(em));
			
		}
		
		public Telefone getTelefone() {
			return new Telefone();
		}
		
		public ClienteService getClienteService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new ClienteService(em, new ClienteDAO(em));
		}

		public Cliente getCliente() {
			return new Cliente();
		}
}
