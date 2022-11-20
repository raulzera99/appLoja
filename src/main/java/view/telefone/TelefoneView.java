package view.telefone;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.persistence.EntityManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.Constantes;
import dao.TelefoneDAO;
import message.ModelResponse;
import models.Telefone;
import persistence.DataBaseConnection;
import services.TelefoneService;
import services.errors.ErrorsData;

public class TelefoneView extends JFrame {
	
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNumero;
	JLabel lblMessageNum;
	
	private Long idTelefone = 0L;
	
	private TelefoneService telefoneService;
	private Telefone telefone = null;

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
			btnCancelar.setBounds(225, 131, 114, 37);
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
			}
		}
		
		private void limpa() {
			
			idTelefone = 0l;
			txtNumero.setText("");
		}
		
		private void setTelefoneFromView() {
			telefone.setNumero(txtNumero.getText());			
		}
		
		private void getTelefoneFromDataBase() {
			idTelefone = telefone.getId();
			txtNumero.setText(String.valueOf(telefone.getNumero()));
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 290);
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
			panel_1.setBounds(0, 105, 555, 146);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(119, 76, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(318, 76, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblNum = new JLabel("Número: ");
			lblNum.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNum.setBounds(10, 25, 59, 21);
			panel_1.add(lblNum);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 124, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
			txtNumero = new JTextField();
			txtNumero.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNumero.setColumns(10);
			txtNumero.setBounds(79, 26, 454, 19);
			panel_1.add(txtNumero);
			
			lblMessageNum = new JLabel("");
			lblMessageNum.setBounds(79, 45, 454, 14);
			panel_1.add(lblMessageNum);
		}
		
		public TelefoneService getTelefoneService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new TelefoneService(em, new TelefoneDAO(em));
			
		}
		
		public Telefone getTelefone() {
			return new Telefone();
		}
}
