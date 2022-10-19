package view.telefone;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.Constantes;
import models.Telefone;
import services.TelefoneService;

public class TelefoneView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNome;
	
	private Long idTelefone = 0l;
	
	private TelefoneService telefoneService;
	private Telefone telefone;
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
					if(idTelefone == 0l) {
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
			telefoneService = getTelefoneService();
			telefone = getTelefone();
			
			setTelefoneFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+telefone.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				telefoneService.add(telefone);	
				dispose();
				idTelefone = 0L;
				limpa();
			}			
		}
		
		public void update() {
			telefone = getTelefone();
			telefoneService = getTelefoneService();
			
			telefone.setId(idTelefone);
			setTelefoneFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+telefone.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				telefoneService.update(telefone);
				dispose();
				idTelefone = 0L;
				limpa();
			}			
		}
		
		public void remove() {
			telefoneService = getTelefoneService();
			Telefone telefone = new Telefone();
			telefone = telefoneService.findById(idTelefone);
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+telefone.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				telefoneService.remove(telefone);
				dispose();
				idTelefone = 0L;
				limpa();
			}			
		}
		
		public void findById(Long id) {
			telefoneService = getTelefoneService();
			telefone = getTelefone();
			
			telefone = telefoneService.findById(id);
			
			getTelefoneFromDataBase();
		}
				
		
		private void limpa() {
			
			idTelefone = 0l;
			txtNome.setText("");
		}
		
		private void setTelefoneFromView() {
			telefone.setId(idTelefone);
			telefone.setNome(txtNome.getText());			
		}
		
		private void getTelefoneFromDataBase() {
			idTelefone = telefone.getId();
			txtNome.setText(String.valueOf(telefone.getNome()));
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
		
		public TelefoneService getTelefoneService() {
			return new TelefoneService();
		}
		
		public Telefone getTelefone() {
			return new Telefone();
		}
}
