package view.serial;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import serial.SerialConnection;
import javax.swing.JButton;

public class SerialCommunication extends JFrame {

	private static final long serialVersionUID = 2915061128004655605L;
	private JPanel contentPane;

	private String baudRate[] = {"300", "600", "1200","2400","9600", "14400", "19200", "38400", "57600", "115200"};
	private String dataBits[] = {"5", "6", "7", "8"};
	private String parity[] = {"0", "1", "2", "3", "4"};
	private String stopBits[] = {"0", "1", "2", "3"};
	
	private String port = new String();
	private boolean isPortOpen = false;
	private boolean connected = false;
	private int intPort = 0;
	private int intBaudRate = 0;
	private int intDataBits = 0;
	private int intParity = 0;
	private int intStopBits = 0;
	
	private String directory = null;
	JPanel panel;
	JLabel lblPort;
	JComboBox<String> cbPort;
	JLabel lblBaudRate;
	JComboBox<String> cbBaudRate;
	JLabel lblParity;
	JComboBox<String> cbParity;
	JLabel lblDataBits;
	JComboBox<String> cbDataBits;
	JLabel lblStopBits;
	JComboBox<String> cbStopBits;
	JButton btnConnect;
	private JButton btnProcess;
	
	private String dir;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SerialCommunication frame = new SerialCommunication();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SerialCommunication() {
		getLib();
		initComponents();		
	}
	
	private void getLib() {
		dir = System.getProperty("user.dir");
		try {
			System.load(dir+"\\rxtxSerial.dll");
			System.load(dir+"\\rxtxParallel.dll");
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private void readBaudRate() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbBaudRate.removeAllItems();
		for(String str: this.getBaudRate()) {
			model.addElement(str);
		}
		cbBaudRate.setModel(model);
	}
	
	private void readDataBits() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbDataBits.removeAllItems();
		for(String str: this.getDataBits()) {
			model.addElement(str);
		}
		cbDataBits.setModel(model);
	}
	
	private void readParity() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbParity.removeAllItems();
		for(String str: this.getParity()) {
			model.addElement(str);
		}
		cbParity.setModel(model);
	}

	private void readStopBits() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbStopBits.removeAllItems();
		for(String str: this.getStopBits()) {
			model.addElement(str);
		}
		cbStopBits.setModel(model);
	}
	
	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
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

	public String[] getParity() {
		return parity;
	}

	public void setParity(String[] parity) {
		this.parity = parity;
	}

	public String[] getStopBits() {
		return stopBits;
	}

	public void setStopBits(String[] stopBits) {
		this.stopBits = stopBits;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isPortOpen() {
		return isPortOpen;
	}

	public void setPortOpen(boolean isPortOpen) {
		this.isPortOpen = isPortOpen;
	}

	public int getIntPort() {
		return intPort;
	}

	public void setIntPort(int intPort) {
		this.intPort = intPort;
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

	public int getIntParity() {
		return intParity;
	}

	public void setIntParity(int intParity) {
		this.intParity = intParity;
	}

	public int getIntStopBits() {
		return intStopBits;
	}

	public void setIntStopBits(int intStopBits) {
		this.intStopBits = intStopBits;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	public void eventHandle() {
		cbPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectPort();
			}
		});
		cbPort.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(getIntPort() == 0) {
					JOptionPane.showMessageDialog(null, "A porta deve ser informada", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cbBaudRate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(getIntBaudRate() == 0) {
					JOptionPane.showMessageDialog(null, "O valor de BaudRate deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		cbBaudRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectBaudRate();
			}
		});
		
		cbDataBits.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(getIntDataBits() == 0) {
					JOptionPane.showMessageDialog(null, "O valor de DataBits deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		cbDataBits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDataBits();
			}
		});
		
		cbParity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectParity();
			}
		});
		
		cbParity.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(getIntParity() == 0) {
					JOptionPane.showMessageDialog(null, "O valor de Parity deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cbStopBits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectStopBits();
			}
		});
		cbStopBits.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(getIntStopBits() == 0) {
					JOptionPane.showMessageDialog(null, "O valor de StopBits deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}
	private void selectPort() {
		this.port = cbPort.getSelectedItem().toString();
	}
	
	private void selectBaudRate() {
		this.intBaudRate = Integer.parseInt(cbBaudRate.getSelectedItem().toString());
	}
	private void selectDataBits() {
		this.intDataBits = Integer.parseInt(cbDataBits.getSelectedItem().toString());
	}
	private void selectParity() {
		this.intParity = Integer.parseInt(cbParity.getSelectedItem().toString());
	}
	private void selectStopBits() {
		this.intStopBits = Integer.parseInt(cbStopBits.getSelectedItem().toString());
	}
	
	private void readPorts() {
		SerialConnection connection = new SerialConnection();
		List<String> listPorts = connection.findPorts();
		
		if(listPorts.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nenhuma porta encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbPort.removeAllItems();
		for(String str: listPorts) {
			model.addElement(str);
		}
		cbPort.setModel(model);
	}
	
	private void getConnection() {
		SerialConnection connection = new SerialConnection(
				this.getIntBaudRate(), 
				this.getIntDataBits(),
				this.getIntParity(),
				this.getIntStopBits());
		isPortOpen = connection.openConnection(port);
		
		if(isPortOpen == false) {
			JOptionPane.showMessageDialog(null, "Erro: porta nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
			cbPort.requestFocus();
		}
		
		if(isPortOpen == true && connected == false) {
			btnConnect.repaint();
			connected = true;
		}
		
	}
	
	private void readMicroController() {
		SerialConnection connection = new SerialConnection(
				this.getIntBaudRate(), 
				this.getIntDataBits(),
				this.getIntParity(),
				this.getIntStopBits());
		
		if(isPortOpen == true && connected == true) {
			String c = "A";
			Thread readLed = new Thread() {
				public void run() {
					while(true) {
						connection.sendData(c);
						try {
							Thread.sleep(10000);
						}catch(InterruptedException e) {
							System.out.println(e.getMessage());
						}
					}
				}
			};
			readLed.start();
		}
	}
	
	private void initComponents() {
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 496, 608);
		setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(1, 0, 485, 569);
		add(panel);
		panel.setLayout(null);
		
		lblPort = new JLabel("Port Velocity:");
		lblPort.setBounds(10, 12, 116, 14);
		panel.add(lblPort);
		
		cbPort = new JComboBox<String>();
		cbPort.setBounds(136, 8, 325, 22);
		panel.add(cbPort);
		
		readPorts();
		
		lblBaudRate = new JLabel("Baud Rate:");
		lblBaudRate.setBounds(10, 41, 116, 14);
		panel.add(lblBaudRate);
		
		cbBaudRate = new JComboBox<String>();
		cbBaudRate.setBounds(136, 37, 325, 22);
		
		readBaudRate();
		panel.add(cbBaudRate);
		
		
		lblParity = new JLabel("Parity:");
		lblParity.setBounds(10, 99, 116, 14);
		panel.add(lblParity);
		
		cbParity = new JComboBox<String>();
		cbParity.setBounds(136, 95, 325, 22);
		readParity();
		panel.add(cbParity);
		
		lblDataBits = new JLabel("Data Bits:");
		lblDataBits.setBounds(10, 70, 116, 14);
		panel.add(lblDataBits);
		
		cbDataBits = new JComboBox<String>();
		cbDataBits.setBounds(136, 66, 325, 22);
		readDataBits();
		panel.add(cbDataBits);
		
		lblStopBits = new JLabel("Stop Bits:");
		lblStopBits.setBounds(10, 128, 116, 14);
		panel.add(lblStopBits);
		
		cbStopBits = new JComboBox<String>();
		cbStopBits.setBounds(136, 124, 325, 22);
		readStopBits();
		panel.add(cbStopBits);
		
		btnConnect = new JButton("Conectar");
		btnConnect.setBounds(95, 183, 89, 23);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getConnection();
			}
		});
		panel.add(btnConnect);
		
		btnProcess = new JButton("Processar");
		btnProcess.setBounds(254, 183, 89, 23);
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readMicroController();
			}
		});
		panel.add(btnProcess);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
	}
}
