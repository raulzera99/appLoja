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

public class SerialConnection implements SerialPortEventListener {

	private SerialPort serialPort;
	private CommPortIdentifier identifierPort;
	private CommPort commPort;
	private List<String> ports;
	private BufferedReader read;
	private OutputStream write;

	private boolean isPortOpen = false;
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

	public SerialConnection(int baudRate, int dataBits, int parity, int stopBits) {
		this.baudRate = baudRate;
		this.dataBits = dataBits;
		this.parity = parity;
		this.stopBits = stopBits;
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	public CommPortIdentifier getIdentifierPort() {
		return identifierPort;
	}

	public void setIdentifierPort(CommPortIdentifier identifierPort) {
		this.identifierPort = identifierPort;
	}

	public CommPort getCommPort() {
		return commPort;
	}

	public void setCommPort(CommPort commPort) {
		this.commPort = commPort;
	}

	public BufferedReader getRead() {
		return read;
	}

	public void setRead(BufferedReader read) {
		this.read = read;
	}

	public OutputStream getWrite() {
		return write;
	}

	public void setWrite(OutputStream write) {
		this.write = write;
	}

	public boolean isPortOpen() {
		return isPortOpen;
	}

	public void setPortOpen(boolean isPortOpen) {
		this.isPortOpen = isPortOpen;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	public int getDataBits() {
		return dataBits;
	}

	public void setDataBits(int dataBits) {
		this.dataBits = dataBits;
	}

	public int getParity() {
		return parity;
	}

	public void setParity(int parity) {
		this.parity = parity;
	}

	public int getStopBits() {
		return stopBits;
	}

	public void setStopBits(int stopBits) {
		this.stopBits = stopBits;
	}

	public List<String> findPorts() {
		ports = new ArrayList<String>();
		Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();	
		while(portList.hasMoreElements()) {
			identifierPort = (CommPortIdentifier) portList.nextElement();
			if(identifierPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				ports.add(identifierPort.getName());
			}
		}
		
		return ports;
	}
	
	private boolean exist(String port) {
		try {
			identifierPort = CommPortIdentifier.getPortIdentifier(port);
		}catch(NoSuchPortException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void close() {
		if(serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public boolean openConnection(String port) {
		if(exist(port)) {
			return false;
		}
		if(isPortOpen = true) {
			isPortOpen = false;
			close();
		}
		else {
			try {
				identifierPort = CommPortIdentifier.getPortIdentifier(port);
				if(identifierPort.isCurrentlyOwned()) {
					isPortOpen = true;
					return isPortOpen;
				}
				if(isPortOpen == false) {
					commPort = identifierPort.open("", 2000);
					serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
					read = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
					write = serialPort.getOutputStream();
					serialPort.addEventListener(this);
					serialPort.notifyOnDataAvailable(true);
					isPortOpen = true;
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
		return isPortOpen;
	}

	public void sendData(String data) {
		try {
			if(isPortOpen) {
				write.write(data.getBytes());
				write.flush();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int result = read.read();
				System.out.println(" recebendo dados " + result);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
