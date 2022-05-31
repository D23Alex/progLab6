import java.io.IOException;
import java.net.InetAddress;

/**
 * Модуль приёма подключений и обработки запросов.
 * @author Алексей
 *
 */
public interface ILogicServerReceiver {
	
	public int getCurrentClientPort();
	
	public void setCurrentClientPort(int port);
	
	public InetAddress getCurrentClientAddress();
	
	public void setCurrentClientAddress(InetAddress address);
	
	public ILogicServerRequest getCurrentRequest();

	public void receive() throws IOException;

}
