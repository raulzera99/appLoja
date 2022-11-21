package view.pagamentoComCartao;

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
import dao.PagamentoComCartaoDAO;
import message.ModelResponse;
import models.PagamentoComCartao;
import models.enums.EstadoPagamento;
import persistence.DataBaseConnection;
import services.PagamentoComCartaoService;
import services.errors.ErrorsData;

public class PagamentoComCartaoView extends JFrame {

	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JComboBox<String> comboBoxEstado;
	JTextField txtNumeroDeParcelas;
	JLabel lblMessageEstado;
	JLabel lblMessageNum;
	
	private Long idPagamentoComCartao = 0l;
	
	private PagamentoComCartaoService pagamentoCartaoService;
	private PagamentoComCartao pagamentoCartao;
	
	private ModelResponse<PagamentoComCartao> modelResponse = null;
	private ModelResponse<ErrorsData> errors;
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PagamentoComCartaoView frame = new PagamentoComCartaoView();
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
	public PagamentoComCartaoView(PagamentoComCartao pagamentoCartao, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Pagamento com Boleto");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(pagamentoCartao.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(pagamentoCartao.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(pagamentoCartao.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idPagamentoComCartao == 0L) {
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
			comboBoxEstado.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					comboBoxEstado.setBorder(null);
					lblMessageEstado.setVisible(false);
				}
			});
			txtNumeroDeParcelas.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					txtNumeroDeParcelas.setBorder(null);
					lblMessageNum.setVisible(false);
				}
			});
		}
		
		@SuppressWarnings("unchecked")
		public void add() {
			pagamentoCartaoService = getPagamentoComCartaoService();
			pagamentoCartao = getPagamentoComCartao();
			int i = 1;
			setPagamentoCartaoFromView();
			
			errors = (ModelResponse<ErrorsData>) pagamentoCartaoService.validarDadosFromView(pagamentoCartao);
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoCartao.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				if(errors.isError()) {
					showErrorFromServidor();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					modelResponse = (ModelResponse<PagamentoComCartao>) pagamentoCartaoService.add(pagamentoCartao);
					pagamentoCartao = modelResponse.getObject();
					JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Adicionado", JOptionPane.INFORMATION_MESSAGE);
				}
				limpa();
			}			
			
		}
		

		@SuppressWarnings("unchecked")
		public void update() {
			pagamentoCartao = getPagamentoComCartao();
			pagamentoCartaoService = getPagamentoComCartaoService();
			int i = 1;
			pagamentoCartao.setId(idPagamentoComCartao);
			setPagamentoCartaoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoCartao.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				modelResponse = (ModelResponse<PagamentoComCartao>) pagamentoCartaoService.update(pagamentoCartao);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamentoCartao = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Alterado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
			
		}
		
		@SuppressWarnings("unchecked")
		public void remove() {
			int i = 1;
			pagamentoCartaoService = getPagamentoComCartaoService();
			idPagamentoComCartao = pagamentoCartao.getId();
			setPagamentoCartaoFromView();
			
			i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+pagamentoCartao.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				modelResponse = (ModelResponse<PagamentoComCartao>) pagamentoCartaoService.remove(idPagamentoComCartao);
			}
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamentoCartao = modelResponse.getObject();
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Excluído", JOptionPane.INFORMATION_MESSAGE);
			}
			
			limpa();
		}
		
		@SuppressWarnings("unchecked")
		public void findById(Long id) {
			pagamentoCartaoService = getPagamentoComCartaoService();
			pagamentoCartao = getPagamentoComCartao();
			
			modelResponse = (ModelResponse<PagamentoComCartao>) pagamentoCartaoService.findById(idPagamentoComCartao);
			
			if(modelResponse.isError()) {
				JOptionPane.showMessageDialog(null, modelResponse.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				pagamentoCartao = modelResponse.getObject();
				getPagamentoCartaoFromDataBase();
			}
		}
				
		
		private void limpa() {
			
			idPagamentoComCartao = 0L;
			txtNumeroDeParcelas.setText("");
			comboBoxEstado.setSelectedIndex(-1);
		}
		
		private void setPagamentoCartaoFromView() {
			pagamentoCartao.setEstado(comboBoxEstado.getSelectedIndex()+1);
			pagamentoCartao.setNumeroDeParcelas(Integer.parseInt(txtNumeroDeParcelas.getText()));
		}
		
		private void getPagamentoCartaoFromDataBase() {
			idPagamentoComCartao = pagamentoCartao.getId();
			comboBoxEstado.setSelectedIndex(pagamentoCartao.getEstado() - 1);
			txtNumeroDeParcelas.setText(String.valueOf(pagamentoCartao.getNumeroDeParcelas()));
		}
		
		private void showErrorFromServidor() {
			for(ErrorsData erro : errors.getListObject()) {
				if(erro.getNumeroCampo() == 1) {
					lblMessageEstado.setVisible(true);
					lblMessageEstado.setForeground(Color.red);
					lblMessageEstado.setText(erro.getShowMensagemError());
					comboBoxEstado.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
				if(erro.getNumeroCampo() == 2) {
					lblMessageNum.setVisible(true);
					lblMessageNum.setForeground(Color.red);
					lblMessageNum.setText(erro.getShowMensagemError());
					txtNumeroDeParcelas.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				}
			}
		}
		
		private void initComponents() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 571, 344);
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
			
			JLabel lblNewLabel = new JLabel("Pagamento Com Cartão");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Segoe UI", Font.ITALIC, 30));
			lblNewLabel.setBounds(10, 30, 386, 46);
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(255, 255, 255));
			panel_1.setBounds(0, 105, 555, 200);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			
			btnSalvar.setForeground(new Color(255, 255, 255));
			btnSalvar.setBackground(new Color(211, 61, 48));
			btnSalvar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnSalvar.setBounds(120, 131, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(320, 131, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblestado = new JLabel("Estado :");
			lblestado.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblestado.setBounds(10, 25, 85, 21);
			panel_1.add(lblestado);
			
			JLabel lblNewLabel_1_1 = new JLabel("Número de parcelas :");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 56, 151, 21);
			panel_1.add(lblNewLabel_1_1);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 179, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
			txtNumeroDeParcelas = new JTextField();
			txtNumeroDeParcelas.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNumeroDeParcelas.setColumns(10);
			txtNumeroDeParcelas.setBounds(159, 57, 374, 19);
			panel_1.add(txtNumeroDeParcelas);
			
			comboBoxEstado = new JComboBox<String>();
			comboBoxEstado.setModel(new DefaultComboBoxModel<String>(EstadoPagamento.enumsToStringArray()));
			comboBoxEstado.setBounds(159, 27, 374, 19);
			panel_1.add(comboBoxEstado);
			
			lblMessageEstado = new JLabel("");
			lblMessageEstado.setBounds(159, 45, 374, 14);
			panel_1.add(lblMessageEstado);
			
			lblMessageNum = new JLabel("");
			lblMessageNum.setBounds(159, 70, 374, 14);
			panel_1.add(lblMessageNum);
			
		}
		
		public PagamentoComCartaoService getPagamentoComCartaoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new PagamentoComCartaoService(em, new PagamentoComCartaoDAO(em));
		}
		
		public PagamentoComCartao getPagamentoComCartao() {
			return new PagamentoComCartao();
		}

}
