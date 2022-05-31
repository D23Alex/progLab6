import java.io.IOException;
import java.net.InetAddress;

public interface IDataServerReceiver {
	
	public int getCurrentClientPort();
	
	public void setCurrentClientPort(int port);
	
	public InetAddress getCurrentClientAddress();
	
	public void setCurrentClientAddress(InetAddress address);
	
	public IDataServerRequest getCurrentRequest();

	public void receive() throws IOException;
}
