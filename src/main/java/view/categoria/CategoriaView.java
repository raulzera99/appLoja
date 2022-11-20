package view.categoria;

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
import dao.CategoriaDAO;
import message.ModelResponse;
import models.Categoria;
import persistence.DataBaseConnection;
import services.CategoriaService;
import services.errors.ErrorsData;

public class CategoriaView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNome;
	JLabel lblMessageNome;
	
	private Long idCategoria = 0L;
	
	private CategoriaService categoriaService;
	private Categoria categoria = null;
	
	private ModelResponse<Categoria> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CategoriaView frame = new CategoriaView();
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
	public CategoriaView(Categoria categoria, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Categoria");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(categoria.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(categoria.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(categoria.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idCategoria == 0L) {
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
			categoriaService = getCategoriaService();
			categoria = getCategoria();
			int i = 1;
			setCategoriaFromView();
			
			errors = (ModelResponse<ErrorsData>) categoriaService.validarDadosFromView(categoria);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+categoria.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<Categoria>) categoriaService.add(categoria);
					categoria = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}
		}
		
		@SuppressWarnings("unchecked")
		public void update() {
			categoria = getCategoria();
			categoriaService = getCategoriaService();
			int i = 1;
			categoria.setId(idCategoria);
			setCategoriaFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+categoria.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Categoria>) categoriaService.update(categoria);
			}

			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				categoria = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
						
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			categoriaService = getCategoriaService();
			idCategoria = categoria.getId();
			
			setCategoriaFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+categoria.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Categoria>) categoriaService.remove(idCategoria);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				categoria = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			categoriaService = getCategoriaService();
			categoria = getCategoria();

			modelResponse = (ModelResponse<Categoria>) categoriaService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				categoria = modelResponse.getObject();
				getCategoriaFromDataBase();
			}
		}
				
		
		private void limpa() {
			
			idCategoria = 0L;
			txtNome.setText("");
		}
		
		private void setCategoriaFromView() {
			categoria.setNome(txtNome.getText());	
		}
		
		private void getCategoriaFromDataBase() {
			idCategoria = categoria.getId();
			txtNome.setText(categoria.getNome());
		}
		
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageNome.setVisible(true);
					lblMessageNome.setForeground(Color.red);
					lblMessageNome.setText(erro.getShowMensagemError());
					txtNome.setBorder(BorderFactory.createLineBorder(Color.red, 2));
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
			
			JLabel lblNewLabel = new JLabel("Categoria");
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
			
			JLabel lblNome = new JLabel("Nome : ");
			lblNome.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNome.setBounds(10, 21, 62, 21);
			panel_1.add(lblNome);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 168, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
			txtNome = new JTextField();
			txtNome.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNome.setColumns(10);
			txtNome.setBounds(82, 22, 451, 19);
			panel_1.add(txtNome);
			
			lblMessageNome = new JLabel("");
			lblMessageNome.setBounds(82, 42, 451, 14);
			panel_1.add(lblMessageNome);
			
		}
		
		public CategoriaService getCategoriaService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new CategoriaService(em, new CategoriaDAO(em));
		}
		
		public Categoria getCategoria() {
			return new Categoria();
		}
}
