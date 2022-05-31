import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public abstract class Client implements IClient {
	
	private IResponse currentResponse;
	
	private ILogicServerRequest currentRequest;
	
	private int serverPort;
	
	private InetAddress serverAddress;
	
	/**
	 * Сокет для взаимодействия с конкретным сервером
	 */
	private SocketAddress socketAddress;
	
	private DatagramChannel datagramChannel;
	
	

	public Client(int serverPort, InetAddress serverAddress) {
		this.serverPort = serverPort;
		this.serverAddress = serverAddress;
		//TODO: пока оставляем так потом разобраться
		this.currentRequest = null;
		this.datagramChannel = null;
		this.socketAddress = null;
	}

	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	public void connect(InetSocketAddress address, int port) {
		// TODO Auto-generated method stub

	}

	public String getConnectionDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract void run() throws IOException;
	
	public void init() throws IOException {
		this.socketAddress = new InetSocketAddress(this.serverAddress, this.serverPort);
		this.datagramChannel = DatagramChannel.open();
	}

	public ILogicServerRequest getCurrentRequest() {
		return currentRequest;
	}

	public void setCurrentRequest(ILogicServerRequest currentRequest) {
		this.currentRequest = currentRequest;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public InetAddress getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(InetAddress serverAddress) {
		this.serverAddress = serverAddress;
	}

	public SocketAddress getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	public DatagramChannel getDatagramChannel() {
		return datagramChannel;
	}

	public void setDatagramChannel(DatagramChannel datagramChannel) {
		this.datagramChannel = datagramChannel;
	}

	public IResponse getCurrentResponse() {
		return currentResponse;
	}

	public void setCurrentResponse(IResponse currentResponse) {
		this.currentResponse = currentResponse;
	}

}
