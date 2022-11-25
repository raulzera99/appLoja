package view.produto;

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
import dao.CategoriaDAO;
import dao.CodigoDAO;
import dao.ProdutoDAO;
import message.ModelResponse;
import models.Categoria;
import models.Codigo;
import models.Produto;
import persistence.DataBaseConnection;
import services.CategoriaService;
import services.CodigoService;
import services.ProdutoService;
import services.errors.ErrorsData;

public class ProdutoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNome;
	JLabel lblMessageNome;
	JTextField txtPreco;
	JLabel lblPreco;
	JLabel lblMessagePreco;
	JComboBox<String> cbCategoria;
	JLabel lblCategoria;
	JLabel lblMessageCategoria;
	private JComboBox<String> cbCodigoSerial;
	private JLabel lblCdigoSerial;
	private JLabel lblMessageCodigoSerial;
	
	private Long idProduto = 0L;
	
	private ProdutoService produtoService;
	private Produto produto = null;
	private Categoria categoria = null;
	private Codigo codigo = null ;
	
	private ModelResponse<Produto> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	
	
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProdutoView frame = new ProdutoView();
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
	public ProdutoView(Produto produto, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Produto");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(produto.getId());
			btnSalvar.setText("Alterar");
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(produto.getId());
			btnSalvar.setText("Excluir");
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(produto.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idProduto == 0L) {
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
					txtNome.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageNome.setVisible(false);
				}
			});
			
			txtPreco.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtPreco.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessagePreco.setVisible(false);
				}
			});
			
			txtPreco.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtPreco.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessagePreco.setVisible(false);
				}
			});
			
			cbCategoria.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbCategoria.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageCategoria.setVisible(false);
				}
			});
		}

		@SuppressWarnings("unchecked")
		public void add() {
			produtoService = getProdutoService();
			produto = getProduto();
			int i = 1;
			setProdutoFromView();
			
			errors = (ModelResponse<ErrorsData>) produtoService.validarDadosFromView(produto);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+produto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<Produto>) produtoService.add(produto);
					produto = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}
		}
		
		@SuppressWarnings("unchecked")
		public void update() {
			produto = getProduto();
			produtoService = getProdutoService();
			int i = 1;
			produto.setId(idProduto);
			setProdutoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+produto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Produto>) produtoService.update(produto);
			}

			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				produto = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
						
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			produtoService = getProdutoService();
			idProduto = produto.getId();
			
			setProdutoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+produto.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<Produto>) produtoService.remove(idProduto);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				produto = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			produtoService = getProdutoService();
			produto = getProduto();

			modelResponse = (ModelResponse<Produto>) produtoService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				produto = modelResponse.getObject();
				getProdutoFromDataBase();
			}
		}
				
		
		private void limpa() {
			
			idProduto = 0L;
			txtNome.setText("");
			txtPreco.setText("");
			cbCategoria.setSelectedIndex(-1);
			cbCodigoSerial.setSelectedIndex(-1);
		}
		
		@SuppressWarnings({ "null", "unchecked" })
		private void setProdutoFromView() {
			produto.setNome(txtNome.getText());
			produto.setPreco(Double.valueOf(txtPreco.getText()));
			
			ModelResponse<Categoria> mrCategoria = new ModelResponse<Categoria>();
			mrCategoria = (ModelResponse<Categoria>) getCategoriaService()
					.findByName(cbCategoria.getItemAt(cbCategoria.getSelectedIndex()));
			categoria = mrCategoria.getObject();
			produto.setCategoria(categoria);
			
			ModelResponse<Codigo> mrCodigo = new ModelResponse<Codigo>();
			mrCodigo = (ModelResponse<Codigo>) getCodigoService()
					.findByName(cbCodigoSerial.getItemAt(cbCodigoSerial.getSelectedIndex()));
			codigo = mrCodigo.getObject();
			produto.setCodigo(codigo);
		}
		
		private void getProdutoFromDataBase() {
			idProduto = produto.getId();
			txtNome.setText(produto.getNome());
			txtPreco.setText(String.valueOf(produto.getPreco()));
			cbCategoria.setSelectedItem(produto.getCategoria());;
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
					lblMessagePreco.setVisible(true);
					lblMessagePreco.setForeground(Color.red);
					lblMessagePreco.setText(erro.getShowMensagemError());
					txtPreco.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 3) {
					lblMessageCategoria.setVisible(true);
					lblMessageCategoria.setForeground(Color.red);
					lblMessageCategoria.setText(erro.getShowMensagemError());
					cbCategoria.setBorder(BorderFactory.createLineBorder(Color.red, 2));
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
			
			JLabel lblNewLabel = new JLabel("Produto");
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
			
			lblPreco = new JLabel("Preço: ");
			lblPreco.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblPreco.setBounds(10, 53, 62, 21);
			panel_1.add(lblPreco);
			
			txtPreco = new JTextField();
			txtPreco.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtPreco.setColumns(10);
			txtPreco.setBounds(82, 54, 451, 19);
			panel_1.add(txtPreco);
			
			lblMessagePreco = new JLabel("");
			lblMessagePreco.setBounds(82, 74, 451, 14);
			panel_1.add(lblMessagePreco);
			
			lblMessageCategoria = new JLabel("");
			lblMessageCategoria.setBounds(82, 106, 451, 14);
			panel_1.add(lblMessageCategoria);
			
			lblCategoria = new JLabel("Categoria : ");
			lblCategoria.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblCategoria.setBounds(10, 85, 74, 21);
			panel_1.add(lblCategoria);
			
			cbCategoria = new JComboBox<String>();
			cbCategoria.setModel(new DefaultComboBoxModel<String>(getCategoriaService().stringListAllCategorias()));
			cbCategoria.setBounds(82, 86, 451, 19);
			panel_1.add(cbCategoria);
			
			cbCodigoSerial = new JComboBox<String>();
			cbCodigoSerial.setBounds(102, 118, 431, 19);
			panel_1.add(cbCodigoSerial);
			
			lblCdigoSerial = new JLabel("Código serial:");
			lblCdigoSerial.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblCdigoSerial.setBounds(10, 117, 91, 21);
			panel_1.add(lblCdigoSerial);
			
			lblMessageCodigoSerial = new JLabel("");
			lblMessageCodigoSerial.setBounds(102, 138, 431, 14);
			panel_1.add(lblMessageCodigoSerial);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 352, 555, 21);
			contentPane.add(panel_2);
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setLayout(null);
			
		}
		
		public ProdutoService getProdutoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new ProdutoService(em, new ProdutoDAO(em));
		}
		
		public Produto getProduto() {
			return new Produto();
		}
		
		public CategoriaService getCategoriaService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new CategoriaService(em, new CategoriaDAO(em));
		}
		
		public Categoria getCategoria() {
			return new Categoria();
		}
		public CodigoService getCodigoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new CodigoService(em, new CodigoDAO(em));
		}
		
		public Codigo getCodigo() {
			return new Codigo();
		}
}
