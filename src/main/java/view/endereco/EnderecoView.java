package view.endereco;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.Constantes;
import dao.EnderecoDAO;
import message.ModelResponse;
import models.Endereco;
import persistence.DataBaseConnection;
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
	private JTextField txtBairro;
	private JTextField txtCEP;
	
	private Long idEndereco = 0l;
	
	private EnderecoService enderecoService;
	private Endereco endereco = null;
	
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
		}
		
		private void setEnderecoFromView() {
			endereco.setNumero(txtNumero.getText());		
			endereco.setBairro(txtBairro.getText());
			endereco.setCep(txtCEP.getText());
		}
		
		private void getEnderecoFromDataBase() {
			idEndereco = endereco.getId();
			txtNumero.setText(String.valueOf(endereco.getNumero()));
			txtBairro.setText(String.valueOf(endereco.getBairro()));
			txtCEP.setText(String.valueOf(endereco.getCep()));
		}

		private void showErrorFromServidor() {
			
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 383);
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
			
			JLabel lblNewLabel = new JLabel("Endereco");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
			lblNewLabel.setBounds(10, 30, 386, 46);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(0, 105, 555, 239);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(120, 170, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(315, 170, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblNewLabel_1_1 = new JLabel("Número: ");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 25, 61, 21);
			panel_1.add(lblNewLabel_1_1);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 218, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
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
		}
		
		public EnderecoService getEnderecoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new EnderecoService(em, new EnderecoDAO(em));
		}
		
		public Endereco getEndereco() {
			return new Endereco();
		}
}
