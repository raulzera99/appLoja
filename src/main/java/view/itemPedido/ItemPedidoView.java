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
import javax.swing.JTextField;
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
import serial.SerialConnection;
import services.ItemPedidoService;
import services.PedidoService;
import services.ProdutoService;
import services.errors.ErrorsData;

public class ItemPedidoView extends JFrame {
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	
	
	JComboBox<String> cbPedido;
	JComboBox<String> cbProduto;
	private JTextField txtDesconto;
	private JTextField txtQuantidade;
	JLabel lblMessageProduto;
	JLabel lblMessagePedido;
	JLabel lblMessageDesconto;
	JLabel lblMessageQuantidade;
	
	private Long idItemPedido = 0L;
	
	private ItemPedidoService itemPedidoService;
	private ItemPedido itemPedido = null;
	private Pedido pedido = null;
	private Produto produto = null;
	
	private String porta = new String();

	private boolean portOpen = false;
	private boolean conectado = false;
	private SerialConnection conexao;
	
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
		setTitle("Itens do Pedido");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			getConnection();
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
			btnCancelar.setBounds(225, 141, 114, 37);
			btnCancelar.setText("Sair");
		}
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idItemPedido == 0L) {
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
			
			txtDesconto.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtDesconto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
					lblMessageDesconto.setVisible(false);
				}
			});
			
			txtQuantidade.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtQuantidade.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
					lblMessageQuantidade.setVisible(false);
				}
			});
			
			
			cbProduto.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbProduto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
					lblMessageProduto.setVisible(false);
				}
			});
			cbPedido.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					cbPedido.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
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
					conexao.close();
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
				conexao.close();
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
			cbPedido.setSelectedIndex(-1);
			cbProduto.setSelectedIndex(-1);
		}
		
		@SuppressWarnings("unchecked")
		public void setProdutoFromSerial() {
			String codigo = conexao.getCodigo();
			ModelResponse<Produto> mrProduto = new ModelResponse<Produto>();
			mrProduto = (ModelResponse<Produto>) getProdutoService()
					.findByCodigo(codigo);
			produto = mrProduto.getObject();
			cbProduto.setSelectedItem(produto.getNome());
		}
		
		@SuppressWarnings("unchecked")
		private void setItemPedidoFromView() {
			
			itemPedido.setDesconto(Double.valueOf(txtDesconto.getText()));
			itemPedido.setQuantidade(Integer.valueOf(txtQuantidade.getText()));
			
			ModelResponse<Pedido> mrPedido = new ModelResponse<Pedido>();
			mrPedido = (ModelResponse<Pedido>) getPedidoService()
					.findByName(cbPedido.getItemAt(cbPedido.getSelectedIndex()));
			pedido = mrPedido.getObject();
			itemPedido.setPedido(pedido);
			
			ModelResponse<Produto> mrProduto = new ModelResponse<Produto>();
			mrProduto = (ModelResponse<Produto>) getProdutoService()
					.findByName(cbProduto.getItemAt(cbProduto.getSelectedIndex()));
			produto = mrProduto.getObject();
			itemPedido.setProduto(produto);
			
			itemPedido.setPreco((itemPedido.getQuantidade()*itemPedido.getProduto().getPreco()) - itemPedido.getDesconto());
			
		}
		
		private void getItemPedidoFromDataBase() {
			idItemPedido = itemPedido.getId();
			txtDesconto.setText(String.valueOf(itemPedido.getDesconto()));
			txtQuantidade.setText(String.valueOf(itemPedido.getQuantidade()));
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
			setBounds(100, 100, 571, 393);
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
			panel_1.setBounds(0, 105, 555, 228);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(116, 175, 114, 37);
			panel_1.add(btnSalvar);
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(315, 175, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblNewLabel_1_1 = new JLabel("Nome do produto:");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 89, 151, 21);
			panel_1.add(lblNewLabel_1_1);
			
			cbProduto = new JComboBox<String>();
			cbProduto.setEnabled(false);
			cbProduto.setModel(new DefaultComboBoxModel<String>(getProdutoService().stringListAllProdutos()));
			cbProduto.setBounds(159, 93, 374, 19);
			panel_1.add(cbProduto);
			
			lblMessageProduto = new JLabel("");
			lblMessageProduto.setBounds(159, 112, 374, 14);
			panel_1.add(lblMessageProduto);
			
			cbPedido = new JComboBox<String>();
			cbPedido.setModel(new DefaultComboBoxModel<String>(getPedidoService().stringListAllPedidos()));
			cbPedido.setBounds(159, 129, 374, 19);
			panel_1.add(cbPedido);
			
			lblMessagePedido = new JLabel("");
			lblMessagePedido.setBounds(159, 150, 374, 14);
			panel_1.add(lblMessagePedido);
			
			JLabel lblNewLabel_1_1_1 = new JLabel("Descrição do pedido:");
			lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_1.setBounds(10, 125, 128, 21);
			panel_1.add(lblNewLabel_1_1_1);
			
			JLabel lblNewLabel_1_1_2 = new JLabel("Desconto:");
			lblNewLabel_1_1_2.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_2.setBounds(10, 19, 151, 21);
			panel_1.add(lblNewLabel_1_1_2);
			
			JLabel lblNewLabel_1_1_2_1 = new JLabel("Quantidade:");
			lblNewLabel_1_1_2_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1_2_1.setBounds(10, 57, 151, 21);
			panel_1.add(lblNewLabel_1_1_2_1);
			
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
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 333, 555, 21);
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
		
		private void getConnection() {
			
			portOpen = conexao.openConnection(porta);
			
			if(portOpen == false) {
				JOptionPane.showMessageDialog(null, "Erro: porta nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			if(portOpen == true && conectado == false) {
				conectado = true;
			}
			
		}

		
}
