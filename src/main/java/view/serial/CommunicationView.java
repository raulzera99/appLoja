package view.serial;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import serial.SerialConnection;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import javax.swing.JButton;

public class CommunicationView extends JFrame {

	private JPanel contentPane;

	private static final long serialVersionUID = -5722548462663769970L;

	private String baudRate[] = { "300", "600", "1200", "2400", "9600", "14400", "19200", "38400", "57600", "115200" };

	private String dataBits[] = { "5", "6", "7", "8" };

	private String paridade[] = { "0", "1", "2", "3", "4" };

	private String stopBits[] = { "0", "1", "2", "3" };

	private String porta = new String();

	private boolean portOpen = false;
	private boolean conectado = false;
    private SerialConnection conexao;
	
	private int intPorta    = 0;
	private int intBaudRate = 0;
	private int intDataBits = 0;
	private int intParidade = 0;
	private int intStopBits = 0;

	private JLabel lblPorta;
	private JComboBox<String> cbPorta;
	private JLabel lblNewLabel;
	private JComboBox<String> cbBaudRate;
	private JLabel lblDataBits;
	private JComboBox<String> cbDataBits;
	private JLabel lblParidade;
	private JComboBox<String> cbParidade;
	private JComboBox<String> cbStopBits;
	private JLabel lblStopBits;
	private JButton btnConectar;
	
	private String dir;
	private JButton btnProcessar;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CommunicationView frame = new CommunicationView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public CommunicationView() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 344, 263);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	
		lblPorta = new JLabel("Porta:");
		lblPorta.setHorizontalAlignment(SwingConstants.LEFT);
		lblPorta.setBounds(22, 29, 54, 14);
		contentPane.add(lblPorta);

		cbPorta = new JComboBox<String>();
		cbPorta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionaPorta();
			}
		
		});
		cbPorta.setBounds(86, 26, 218, 20);
		contentPane.add(cbPorta);
		
		leiaPortas();

		lblNewLabel = new JLabel("Baud Rate:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(22, 60, 65, 14);
		contentPane.add(lblNewLabel);

		cbBaudRate = new JComboBox<String>();
		cbBaudRate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (getIntBaudRate()==0) {
					JOptionPane.showMessageDialog(null, 
							"Baud Rate deve ser informado", 
							 "Erro", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		cbBaudRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionaBaudRate();
			}
		});
		cbBaudRate.setBounds(86, 57, 218, 20);
		contentPane.add(cbBaudRate);

		lerBaudRate();

		lblDataBits = new JLabel("Data Bits:");
		lblDataBits.setHorizontalAlignment(SwingConstants.LEFT);
		lblDataBits.setBounds(22, 91, 54, 14);
		contentPane.add(lblDataBits);

		cbDataBits = new JComboBox<String>();
		cbDataBits.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (getIntDataBits()==0) {
					JOptionPane.showMessageDialog(null, 
							"Data Bits deve ser informado", 
							 "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		cbDataBits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionaDataBits();
			}
		});
		cbDataBits.setBounds(86, 88, 218, 20);
		contentPane.add(cbDataBits);

		lerDataBits();

		lblParidade = new JLabel("Paridade:");
		lblParidade.setHorizontalAlignment(SwingConstants.LEFT);
		lblParidade.setBounds(22, 123, 54, 14);
		contentPane.add(lblParidade);

		cbParidade = new JComboBox<String>();
		cbParidade.setBounds(86, 120, 218, 20);
		contentPane.add(cbParidade);
		
		lerParidade();

		cbStopBits = new JComboBox<String>();
		cbStopBits.setBounds(86, 151, 218, 20);
		contentPane.add(cbStopBits);

		lblStopBits = new JLabel("Stop Bits:");
		lblStopBits.setHorizontalAlignment(SwingConstants.LEFT);
		lblStopBits.setBounds(22, 154, 54, 14);
		contentPane.add(lblStopBits);
		
		lerStopBits();
		
		setContentPane(contentPane);
		
		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getConnection();
			}
		});
		btnConectar.setBounds(10, 190, 91, 23);
		contentPane.add(btnConectar);
		
		btnProcessar = new JButton("Processar");
		btnProcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leituraMicroControlador();
			}
		});
		btnProcessar.setBounds(111, 190, 100, 23);
		contentPane.add(btnProcessar);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnFechar.setBounds(221, 190, 97, 23);
		contentPane.add(btnFechar);
		
		getLib();
	}
	
	private void leiaPortas() {
		
		conexao = new SerialConnection();
		List<String> listaPortas = conexao.findPortas();
		
		if(listaPortas.isEmpty()) {
			JOptionPane.showMessageDialog(null, 
					"Nenhuma Porta Encontrada!!!!!!", 
					 "Erro", JOptionPane.ERROR_MESSAGE);
		}
			
		lerPortas(listaPortas);
	}

	private void selecionaPorta() {
		porta = cbPorta.getSelectedItem().toString();
	}

	protected void selecionaDataBits() {
		this.intDataBits = Integer.parseInt(cbDataBits.getSelectedItem().toString());
	}

	protected void selecionaBaudRate() {
		this.intBaudRate = Integer.parseInt(cbBaudRate.getSelectedItem().toString());
	}

	private void getLib() {
		dir = System.getProperty("user.dir");
		try {
			System.load(dir+"\\rxtxSerial.dll");
			System.load(dir+"\\rxtxParallel.dll");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private void lerPortas(List<String> portas) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbPorta.removeAllItems();
		for(String str : portas) {
			model.addElement(str);
		}
		cbPorta.setModel(model);
	}
	
	private void lerBaudRate() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbBaudRate.removeAllItems();
		for (String str : this.getBaudRate()) {
			model.addElement(str);
		}
		cbBaudRate.setModel(model);
	}

	private void lerDataBits() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbDataBits.removeAllItems();
		for (String str : this.getDataBits()) {
			model.addElement(str);
		}
		cbDataBits.setModel(model);
	}

	private void lerParidade() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbParidade.removeAllItems();
		for (String str : this.getParidade()) {
			model.addElement(str);
		}
		cbParidade.setModel(model);
	}

	private void lerStopBits() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbStopBits.removeAllItems();
		for (String str : this.getStopBits()) {
			model.addElement(str);
		}
		cbStopBits.setModel(model);

	}
	
	private void getConnection() {
		
		portOpen = conexao.openConnection(porta);
		
		if(portOpen == false) {
			JOptionPane.showMessageDialog(null, "Erro: porta nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
			cbPorta.requestFocus();
		}
		
		if(portOpen == true && conectado == false) {
			btnConectar.repaint();
			conectado = true;
		}
		
	}

	private void leituraMicroControlador() {
		
		if (portOpen==true && conectado == true) {
			Thread leitura = new Thread() {
				public void run() {
				
					while (true) {
						conexao.sendData("1");
						try {
						 Thread.sleep(10000);
						} catch(InterruptedException e) {
							System.out.println(e.getMessage());
						}
					}				
				}
			};
			leitura.start();
		}
		
	}
	
	public String[] getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(String[] baudRate) {
		this.baudRate = baudRate;
	}

	public String[] getDataBits() {
		return dataBits;
	}

	public void setDataBits(String[] dataBits) {
		this.dataBits = dataBits;
	}

	public String[] getParidade() {
		return paridade;
	}

	public void setParidade(String[] paridade) {
		this.paridade = paridade;
	}

	public String[] getStopBits() {
		return stopBits;
	}

	public void setStopBits(String[] stopBits) {
		this.stopBits = stopBits;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public boolean isPortOpen() {
		return portOpen;
	}

	public void setPortOpen(boolean portOpen) {
		this.portOpen = portOpen;
	}

	public int getIntBaudRate() {
		return intBaudRate;
	}

	public void setIntBaudRate(int intBaudRate) {
		this.intBaudRate = intBaudRate;
	}

	public int getIntDataBits() {
		return intDataBits;
	}

	public void setIntDataBits(int intDataBits) {
		this.intDataBits = intDataBits;
	}

	public int getIntParidade() {
		return intParidade;
	}

	public void setIntParidade(int intParidade) {
		this.intParidade = intParidade;
	}

	public int getIntStopBits() {
		return intStopBits;
	}

	public void setIntStopBits(int intStopBits) {
		this.intStopBits = intStopBits;
	}

	public int getIntPorta() {
		return intPorta;
	}

	public void setIntPorta(int intPorta) {
		this.intPorta = intPorta;
	}
}
