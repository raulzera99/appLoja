package serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import view.itemPedido.ItemPedidoView;

public class SerialConnection implements SerialPortEventListener {

	private SerialPort serialPort;
	private CommPortIdentifier identificadorPorta;
	private CommPort commPort;
    private List<String> ports;
    private String codigo = null;
	private ItemPedidoView itemPedidoView;
	
	private BufferedReader leitura;
	private OutputStream escrita;

	private boolean portOpen = false;
	private int baudRate = 0;
	private int dataBits = 0;
	private int parity = 0;
	private int stopBits = 0;

	public SerialConnection() {
		this.baudRate = 9600;
		this.dataBits = SerialPort.DATABITS_8;
		this.parity = SerialPort.PARITY_NONE;
		this.stopBits = SerialPort.STOPBITS_1;
	}
	
	public SerialConnection(ItemPedidoView itemPedido) {
		this.baudRate = 9600;
		this.dataBits = SerialPort.DATABITS_8;
		this.parity = SerialPort.PARITY_NONE;
		this.stopBits = SerialPort.STOPBITS_1;
		this.itemPedidoView = itemPedido;
	}

	public SerialConnection(int baudRate, int dataBits, int paridade, int stopBits) {
		this.baudRate = baudRate;
		this.dataBits = dataBits;
		this.parity = paridade;
		this.stopBits = stopBits;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	public CommPortIdentifier getIdentificadorPorta() {
		return identificadorPorta;
	}

	public void setIdentificadorPorta(CommPortIdentifier identificadorPorta) {
		this.identificadorPorta = identificadorPorta;
	}

	public CommPort getCommPort() {
		return commPort;
	}

	public void setCommPort(CommPort commPort) {
		this.commPort = commPort;
	}

	public List<String> findPortas() {
		
		ports = new ArrayList<String>();
		Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers(); 
		while (portList.hasMoreElements()) {
			identificadorPorta = (CommPortIdentifier) portList.nextElement();
			if (identificadorPorta.getPortType() == CommPortIdentifier.PORT_SERIAL) {
			   ports.add(identificadorPorta.getName());	
			}
		}
		return ports;
	}
	
	
	

	public boolean openConnection(String porta) {
		System.out.println("tentando abrir a porta "+ porta);
		if (!existe(porta)) {
			
			return false;
		}
		if (portOpen==true) {
			portOpen = false;
			close();
		}
		else {
			try {
				identificadorPorta = CommPortIdentifier.getPortIdentifier(porta);
				if ( identificadorPorta.isCurrentlyOwned()) {
					portOpen = true;
					return portOpen;
				}
				if (portOpen==false) {
					System.out.println("abrindo a porta ");
					commPort = identificadorPorta.open("",2000);
					serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(baudRate,dataBits,stopBits,parity);
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
					leitura = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
					escrita = serialPort.getOutputStream();
					serialPort.addEventListener(this);
					serialPort.notifyOnDataAvailable(true);
					portOpen = true;
					System.out.println("porta aberta "+portOpen);
				}
				
			}catch(PortInUseException e) {
				e.printStackTrace();
			}catch(UnsupportedCommOperationException e) {
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return portOpen;
	}

	public void close() {
		if ( serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	private boolean existe(String porta) {
		try {
			identificadorPorta = CommPortIdentifier.getPortIdentifier(porta);
		} catch(NoSuchPortException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void sendData(String palavra) {
        System.out.println("tentado enviar mensagem >>>>>>>>> "+palavra +" " + portOpen);
		try {
			if (portOpen) {
				System.out.println("escrevendo na serial >>>>>>");
				escrita.write(palavra.getBytes());
				escrita.flush();
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void serialEvent(SerialPortEvent event) {
		System.out.println("evento disparado");
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			System.out.println("existe informacao");
			try {
				
				String resultado = leitura.readLine();
				
				System.out.println("recebendo dados "+resultado);
				
				setCodigo(resultado);
				itemPedidoView.setProdutoFromSerial();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


}







