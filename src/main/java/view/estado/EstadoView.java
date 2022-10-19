package view.estado;

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
import models.Estado;
import services.EstadoService;

public class EstadoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNome;
	
	private Long idEstado = 0l;
	
	private EstadoService estadoService;
	private Estado estado;
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					EstadoView frame = new EstadoView();
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
	public EstadoView(Estado estado, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Pagamento com Boleto");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(estado.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(estado.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(estado.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idEstado == 0l) {
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
			estadoService = getEstadoService();
			estado = getEstado();
			
			setEstadoFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+estado.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				estadoService.add(estado);	
				dispose();
				idEstado = 0L;
				limpa();
			}			
		}
		
		public void update() {
			estado = getEstado();
			estadoService = getEstadoService();
			
			estado.setId(idEstado);
			setEstadoFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+estado.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				estadoService.update(estado);
				dispose();
				idEstado = 0L;
				limpa();
			}			
		}
		
		public void remove() {
			estadoService = getEstadoService();
			Estado estado = new Estado();
			estado = estadoService.findById(idEstado);
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+estado.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				estadoService.remove(estado);
				dispose();
				idEstado = 0L;
				limpa();
			}			
		}
		
		public void findById(Long id) {
			estadoService = getEstadoService();
			estado = getEstado();
			
			estado = estadoService.findById(id);
			
			getEstadoFromDataBase();
		}
				
		
		private void limpa() {
			
			idEstado = 0l;
			txtNome.setText("");
		}
		
		private void setEstadoFromView() {
			estado.setId(idEstado);
			estado.setNome(txtNome.getText());			
		}
		
		private void getEstadoFromDataBase() {
			idEstado = estado.getId();
			txtNome.setText(String.valueOf(estado.getNome()));
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
			
			JLabel lblNewLabel = new JLabel("Estado");
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
		
		public EstadoService getEstadoService() {
			return new EstadoService();
		}
		
		public Estado getEstado() {
			return new Estado();
		}
}
