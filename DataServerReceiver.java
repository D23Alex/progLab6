import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import org.apache.commons.lang3.SerializationUtils;

public class DataServerReceiver implements IDataServerReceiver {
	
	private IServerMain coreModule;
	
	private IDataServerRequest currentRequest;
	
	private int currentClientPort;
	
	private InetAddress currentClientAddress;
	
	
	@Override
	public void receive() throws IOException {
		//TODO: захардкодил это, починить потом
		DatagramPacket currentDatagramPacket = new DatagramPacket(new byte[10000], 10000);
		this.coreModule.getDatagramSocket().receive(currentDatagramPacket);
		
		this.currentClientAddress = currentDatagramPacket.getAddress();
		this.currentClientPort = currentDatagramPacket.getPort();
		
		this.currentRequest = (IDataServerRequest) SerializationUtils.deserialize(currentDatagramPacket.getData());
	
	}

	public IDataServerRequest getCurrentRequest() {
		return currentRequest;
	}

	public void setCurrentRequest(IDataServerRequest currentRequest) {
		this.currentRequest = currentRequest;
	}

	public int getCurrentClientPort() {
		return currentClientPort;
	}

	public void setCurrentClientPort(int currentClientPort) {
		this.currentClientPort = currentClientPort;
	}

	public InetAddress getCurrentClientAddress() {
		return currentClientAddress;
	}

	public void setCurrentClientAddress(InetAddress currentClientAddress) {
		this.currentClientAddress = currentClientAddress;
	}

	public IServerMain getCoreModule() {
		return coreModule;
	}

	public void setCoreModule(IServerMain coreModule) {
		this.coreModule = coreModule;
	}

	
}
