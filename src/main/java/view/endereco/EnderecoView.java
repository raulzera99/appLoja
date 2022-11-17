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
import models.Endereco;
import persistence.DataBaseConnection;
import services.EnderecoService;

public class EnderecoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNome;
	
	private Long idEndereco = 0l;
	
	private EnderecoService enderecoService;
	private Endereco endereco;
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
					}else {
						update();
					}
				}
			});
			
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		
		public void add() {
			enderecoService = getEnderecoService();
			endereco = getEndereco();
			
			setEnderecoFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+endereco.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				enderecoService.add(endereco);	
				dispose();
				idEndereco = 0L;
				limpa();
			}			
		}
		
		public void update() {
			endereco = getEndereco();
			enderecoService = getEnderecoService();
			
			endereco.setId(idEndereco);
			setEnderecoFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+endereco.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				enderecoService.update(endereco);
				dispose();
				idEndereco = 0L;
				limpa();
			}			
		}
		
		public void remove() {
			enderecoService = getEnderecoService();
			Endereco endereco = new Endereco();
			endereco = enderecoService.findById(idEndereco);
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+endereco.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				enderecoService.remove(endereco);
				dispose();
				idEndereco = 0L;
				limpa();
			}			
		}
		
		public void findById(Long id) {
			enderecoService = getEnderecoService();
			endereco = getEndereco();
			
			endereco = enderecoService.findById(id);
			
			getEnderecoFromDataBase();
		}
				
		
		private void limpa() {
			
			idEndereco = 0l;
			txtNome.setText("");
		}
		
		private void setEnderecoFromView() {
			endereco.setNome(txtNome.getText());			
		}
		
		private void getEnderecoFromDataBase() {
			idEndereco = endereco.getId();
			txtNome.setText(String.valueOf(endereco.getNome()));
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
			
			JLabel lblNewLabel = new JLabel("Endereco");
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
			btnSalvar.setBounds(119, 76, 114, 37);
			panel_1.add(btnSalvar);
			
			
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			btnCancelar.setBackground(new Color(211, 61, 48));
			btnCancelar.setBounds(318, 76, 114, 37);
			panel_1.add(btnCancelar);
			
			JLabel lblNewLabel_1_1 = new JLabel("Nome: ");
			lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblNewLabel_1_1.setBounds(10, 25, 151, 21);
			panel_1.add(lblNewLabel_1_1);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 124, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
			txtNome = new JTextField();
			txtNome.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNome.setColumns(10);
			txtNome.setBounds(159, 26, 374, 19);
			panel_1.add(txtNome);
		}
		
		public EnderecoService getEnderecoService() {
			EntityManager em = DataBaseConnection.getConnection().getEntityManager();
			return new EnderecoService(em, new EnderecoDAO(em));
		}
		
		public Endereco getEndereco() {
			return new Endereco();
		}
}
