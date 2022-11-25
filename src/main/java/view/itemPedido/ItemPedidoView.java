package view.itemPedido;

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
import dao.ItemPedidoDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import message.ModelResponse;
import models.ItemPedido;
import models.Pedido;
import models.Produto;
import persistence.DataBaseConnection;
import services.ItemPedidoService;
import services.PedidoService;
import services.ProdutoService;
import services.errors.ErrorsData;
import javax.swing.JTextField;

public class ItemPedidoView extends JFrame {
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	
	
	JComboBox<String> cbPedido;
	JComboBox<String> cbProduto;
	private JTextField txtDesconto;
	private JTextField txtQuantidade;
	private JTextField txtPreco;
	JLabel lblMessageProduto;
	JLabel lblMessagePedido;
	JLabel lblMessageDesconto;
	JLabel lblMessageQuantidade;
	JLabel lblMessagePreco;
	
	private Long idItemPedido = 0L;
	
	private ItemPedidoService itemPedidoService;
	private ItemPedido itemPedido = null;
	private Pedido pedido = null;
	private Produto produto = null;
	
	private ModelResponse<ItemPedido> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ItemPedidoView frame = new ItemPedidoView();
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
	public ItemPedidoView(ItemPedido itemPedido, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("ItemPedido com Boleto");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(itemPedido.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(itemPedido.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(itemPedido.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idItemPedido == 0l) {
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
			
			cbProduto.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbProduto.setBorder(BorderFactory.createLineBorder(Color.black, 1));
					lblMessageProduto.setVisible(false);
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
			itemPedidoService = getItemPedidoService();
			itemPedido = getItemPedido();
			int i = 1;
			setItemPedidoFromView();
			
			errors = (ModelResponse<ErrorsData>) itemPedidoService.validarDadosFromView(itemPedido);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+itemPedido.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<ItemPedido>) itemPedidoService.add(itemPedido);
					itemPedido = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}			
		}
		
		@SuppressWarnings("unchecked")
		public void update() {
			itemPedido = getItemPedido();
			itemPedidoService = getItemPedidoService();
			int i = 1;
			itemPedido.setId(idItemPedido);
			setItemPedidoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+itemPedido.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				modelResponse = (ModelResponse<ItemPedido>) itemPedidoService.update(itemPedido);
			}	
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				itemPedido = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			itemPedidoService = getItemPedidoService();
			idItemPedido = itemPedido.getId();
			setItemPedidoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+itemPedido.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(i == 0) {
				modelResponse = (ModelResponse<ItemPedido>) itemPedidoService.remove(idItemPedido);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				itemPedido = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();		
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			itemPedidoService = getItemPedidoService();
			itemPedido = getItemPedido();
			modelResponse = (ModelResponse<ItemPedido>) itemPedidoService.findById(id);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				itemPedido = modelResponse.getObject();
				getItemPedidoFromDataBase();
			}
			
		}
				
		
		private void limpa() {
			
			idItemPedido = 0L;
			cbPedido.setSelectedIndex(-1);;
			cbProduto.setSelectedIndex(-1);;
		}
		
		@SuppressWarnings("unchecked")
		private void setItemPedidoFromView() {
			
			ModelResponse<Pedido> mrPedido = new ModelResponse<Pedido>();
			mrPedido = (ModelResponse<Pedido>) getPedidoService()
					.findByName(cbPedido.getItemAt(cbPedido.getSelectedIndex()));
			pedido = mrPedido.getObject();
			itemPedido.setPedido(pedido);
			
			ModelResponse<Produto> mrProduto = new ModelResponse<Produto>();
			mrProduto = (ModelResponse<Produto>) getPedidoService()
					.findByName(cbProduto.getItemAt(cbProduto.getSelectedIndex()));
			produto = mrProduto.getObject();
			itemPedido.setProduto(produto);
		}
		
		private void getItemPedidoFromDataBase() {
			idItemPedido = itemPedido.getId();
			cbProduto.setSelectedItem(itemPedido.getProduto());
			cbPedido.setSelectedItem(itemPedido.getPedido());
		}
		
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageProduto.setVisible(true);
					lblMessageProduto.setForeground(Color.red);
					lblMessageProduto.setText(erro.getShowMensagemError());
					cbProduto.setBorder(BorderFactory.createLineBorder(Color.red, 2));
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
			setBounds(100, 100, 571, 431);
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
			
			JLabel lblNewLabel = new JLabel("Itens do Pedido");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
			lblNewLabel.setBounds(10, 30, 386, 46);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(0, 105, 555, 265);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(118, 217, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(317, 217, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblNewLabel_1_1 = new JLabel("Nome do produto:");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 126, 151, 21);
			panel_1.add(lblNewLabel_1_1);
			
			cbProduto = new JComboBox<String>();
			cbProduto.setModel(new DefaultComboBoxModel<String>(getProdutoService().stringListAllProdutos()));
			cbProduto.setBounds(159, 130, 374, 19);
			panel_1.add(cbProduto);
			
			lblMessageProduto = new JLabel("");
			lblMessageProduto.setBounds(159, 149, 374, 14);
			panel_1.add(lblMessageProduto);
			
			cbPedido = new JComboBox<String>();
			cbPedido.setModel(new DefaultComboBoxModel<String>(getPedidoService().stringListAllPedidos()));
			cbPedido.setBounds(159, 166, 374, 19);
			panel_1.add(cbPedido);
			
			lblMessagePedido = new JLabel("");
			lblMessagePedido.setBounds(159, 187, 374, 14);
			panel_1.add(lblMessagePedido);
			
			JLabel lblNewLabel_1_1_1 = new JLabel("Instante do pedido:");
			lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_1.setBounds(10, 162, 128, 21);
			panel_1.add(lblNewLabel_1_1_1);
			
			JLabel lblNewLabel_1_1_2 = new JLabel("Desconto:");
			lblNewLabel_1_1_2.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_2.setBounds(10, 19, 151, 21);
			panel_1.add(lblNewLabel_1_1_2);
			
			JLabel lblNewLabel_1_1_2_1 = new JLabel("Quantidade:");
			lblNewLabel_1_1_2_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_2_1.setBounds(10, 57, 151, 21);
			panel_1.add(lblNewLabel_1_1_2_1);
			
			JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Preço total:");
			lblNewLabel_1_1_2_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_2_1_1.setBounds(10, 92, 151, 21);
			panel_1.add(lblNewLabel_1_1_2_1_1);
			
			txtDesconto = new JTextField();
			txtDesconto.setBounds(159, 22, 374, 20);
			panel_1.add(txtDesconto);
			txtDesconto.setColumns(10);
			
			lblMessageDesconto = new JLabel("");
			lblMessageDesconto.setBounds(159, 42, 374, 14);
			panel_1.add(lblMessageDesconto);
			
			txtQuantidade = new JTextField();
			txtQuantidade.setColumns(10);
			txtQuantidade.setBounds(159, 57, 374, 20);
			panel_1.add(txtQuantidade);
			
			lblMessageQuantidade = new JLabel("");
			lblMessageQuantidade.setBounds(159, 77, 374, 14);
			panel_1.add(lblMessageQuantidade);
			
			txtPreco = new JTextField();
			txtPreco.setColumns(10);
			txtPreco.setBounds(159, 93, 374, 20);
			panel_1.add(txtPreco);
			
			lblMessagePreco = new JLabel("");
			lblMessagePreco.setBounds(159, 113, 374, 14);
			panel_1.add(lblMessagePreco);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 371, 555, 21);
			contentPane.add(panel_2);
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setLayout(null);
		}
		
		public ItemPedidoService getItemPedidoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new ItemPedidoService(em, new ItemPedidoDAO(em));
		}
		
		public ItemPedido getItemPedido() {
			return new ItemPedido();
		}
		
		public PedidoService getPedidoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new PedidoService(em, new PedidoDAO(em));
		}
		
		public Pedido getPedido() {
			return new Pedido();
		}
		
		public ProdutoService getProdutoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new ProdutoService(em, new ProdutoDAO(em));
		}
		
		public Produto getProduto() {
			return new Produto();
		}

		public ModelResponse<ItemPedido> getModelResponse() {
			return modelResponse;
		}

		public void setModelResponse(ModelResponse<ItemPedido> modelResponse) {
			this.modelResponse = modelResponse;
		}
}
