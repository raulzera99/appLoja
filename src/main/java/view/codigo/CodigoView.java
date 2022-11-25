package view.codigo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
import dao.CodigoDAO;
import message.ModelResponse;
import models.Codigo;
import persistence.DataBaseConnection;
import services.CodigoService;
import services.errors.ErrorsData;

public class CodigoView extends JFrame {

	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNumero;
	JLabel lblMessageNumero;
	
	private Long idCodigo = 0L;
	
	private CodigoService codigoService;
	private Codigo codigo = null;
	
	private ModelResponse<Codigo> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CodigoView frame = new CodigoView();
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
	public CodigoView(Codigo codigo, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Codigo");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(codigo.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(codigo.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(codigo.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idCodigo == 0L) {
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
					lblMessageNumero.setVisible(false);
				}
			});
		}

		@SuppressWarnings("unchecked")
		public void add() {
			codigoService = getCodigoService();
			codigo = getCodigo();
			int i = 1;
			setCodigoFromView();
			
			errors = (ModelResponse<ErrorsData>) codigoService.validarDadosFromView(codigo);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+codigo.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<Codigo>) codigoService.add(codigo);
					codigo = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}
		}
		
		@SuppressWarnings("unchecked")
		public void update() {
			codigo = getCodigo();
			codigoService = getCodigoService();
			int i = 1;
			codigo.setId(idCodigo);
			setCodigoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+codigo.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Codigo>) codigoService.update(codigo);
			}

			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				codigo = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
						
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			codigoService = getCodigoService();
			idCodigo = codigo.getId();
			
			setCodigoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+codigo.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Codigo>) codigoService.remove(idCodigo);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				codigo = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			codigoService = getCodigoService();
			codigo = getCodigo();

			modelResponse = (ModelResponse<Codigo>) codigoService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				codigo = modelResponse.getObject();
				getCodigoFromDataBase();
			}
		}
				
		
		private void limpa() {
			
			idCodigo = 0L;
			txtNumero.setText("");
		}
		
		private void setCodigoFromView() {
			codigo.setNumero(txtNumero.getText());	
		}
		
		private void getCodigoFromDataBase() {
			idCodigo = codigo.getId();
			txtNumero.setText(codigo.getNumero());
		}
		
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageNumero.setVisible(true);
					lblMessageNumero.setForeground(Color.red);
					lblMessageNumero.setText(erro.getShowMensagemError());
					txtNumero.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 332);
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
			
			JLabel lblNewLabel = new JLabel("Codigo");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
			lblNewLabel.setBounds(10, 30, 386, 46);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(0, 105, 555, 188);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(120, 109, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(315, 109, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblInstante = new JLabel("Instante : ");
			lblInstante.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblInstante.setBounds(10, 21, 62, 21);
			panel_1.add(lblInstante);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 168, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
			txtNumero = new JTextField();
			txtNumero.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNumero.setColumns(10);
			txtNumero.setBounds(82, 22, 451, 19);
			panel_1.add(txtNumero);
			
			lblMessageNumero = new JLabel("");
			lblMessageNumero.setBounds(159, 42, 374, 14);
			panel_1.add(lblMessageNumero);
			
		}
		
		public CodigoService getCodigoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new CodigoService(em, new CodigoDAO(em));
		}
		
		public Codigo getCodigo() {
			return new Codigo();
		}
}
