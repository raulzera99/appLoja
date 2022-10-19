package view.cidade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import models.Cidade;
import services.CidadeService;
import services.EstadoService;

public class CidadeView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2933295863195236029L;
	private JPanel contentPane;
	JButton btnSalvar = new JButton("Salvar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtNome;
	JComboBox <String> comboBox;
	
	private Long idCidade = 0l;
	
	private CidadeService cidadeService;
	private EstadoService estadoService;
	private Cidade cidade;
	/**
	 * Launch the application.
	 */
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CidadeView frame = new CidadeView();
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
	public CidadeView(Cidade cidade, Integer opcaoCadastro) {
		setBackground(new Color(0, 0, 0));
		setTitle("Pagamento com Boleto");
		initComponents();
		eventhandler();
		
		if(opcaoCadastro == Constantes.INCLUIR) {
			btnSalvar.setText("Incluir");
			
		}
		else if(opcaoCadastro == Constantes.ALTERAR) {
			findById(cidade.getId());
			btnSalvar.setText("Alterar");
		
		}
		else if(opcaoCadastro == Constantes.EXCLUIR) {
			findById(cidade.getId());
			btnSalvar.setText("Excluir");
			
		}
		else if(opcaoCadastro == Constantes.CONSULTAR) {
			findById(cidade.getId());
			btnSalvar.setVisible(false);
			btnCancelar.setBounds(225, 131, 114, 37);
			btnCancelar.setText("Sair");
		}
		
		
	}
	
		private void eventhandler() {
		
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(idCidade == 0l) {
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
			cidadeService = getCidadeService();
			cidade = getCidade();
			
			setCidadeFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+cidade.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				cidadeService.add(cidade);	
				dispose();
				idCidade = 0L;
				limpa();
			}			
		}
		
		public void update() {
			cidade = getCidade();
			cidadeService = getCidadeService();
			
			cidade.setId(idCidade);
			setCidadeFromView();
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+cidade.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				cidadeService.update(cidade);
				dispose();
				idCidade = 0L;
				limpa();
			}			
		}
		
		public void remove() {
			cidadeService = getCidadeService();
			Cidade cidade = new Cidade();
			cidade = cidadeService.findById(idCidade);
			
			int i = JOptionPane.showConfirmDialog(null, "Confirme os dados : "
					+cidade.toString(),
					"Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(i == 0) {
				cidadeService.remove(cidade);
				dispose();
				idCidade = 0L;
				limpa();
			}			
		}
		
		public void findById(Long id) {
			cidadeService = getCidadeService();
			cidade = getCidade();
			
			cidade = cidadeService.findById(id);
			
			getCidadeFromDataBase();
		}
				
		
		private void limpa() {
			
			idCidade = 0l;
			txtNome.setText("");
		}
		
		private void setCidadeFromView() {
			cidade.setId(idCidade);
			cidade.setNome(txtNome.getText());			
		}
		
		private void getCidadeFromDataBase() {
			idCidade = cidade.getId();
			txtNome.setText(String.valueOf(cidade.getNome()));
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
			
			JLabel lblNewLabel = new JLabel("Cidade");
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
			lblNome.setBounds(10, 25, 151, 21);
			panel_1.add(lblNome);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBackground(new Color(211, 61, 48));
			panel_2.setBounds(0, 168, 555, 21);
			panel_1.add(panel_2);
			panel_2.setLayout(null);
			
			txtNome = new JTextField();
			txtNome.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			txtNome.setColumns(10);
			txtNome.setBounds(159, 26, 374, 19);
			panel_1.add(txtNome);
			
			JLabel lblEstado = new JLabel("Estado :");
			lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 15));
			lblEstado.setBounds(10, 53, 151, 21);
			panel_1.add(lblEstado);
			
			comboBox = new JComboBox<String>();
			comboBox.setModel(new DefaultComboBoxModel <String>(estadoService.listAllEstados()));
			comboBox.setBounds(159, 55, 374, 22);
			panel_1.add(comboBox);
		}
		
		public CidadeService getCidadeService() {
			return new CidadeService();
		}
		
		public Cidade getCidade() {
			return new Cidade();
		}
}
