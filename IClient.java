import java.io.IOException;
import java.net.InetSocketAddress;

public interface IClient extends IStructure {
	
	/**
	 * Метод проверяет, подключён ли клиент к какому-либо серверу.
	 * @return true, если подключён, иначе false
	 */
	public boolean isConnected();
	
	/*
	 * Метод выполняет подключение к указанному серверу
	 */
	public void connect(InetSocketAddress address, int port);

	/**
	 * Метод узнаёт детали подключения клиента к серверу
	 * @return Пока будем возвращать строку с деталями, но лучше переделать
	 * TODO: переделать
	 */
	public String getConnectionDetails();
	
	public void sendRequest() throws IOException;
	
	public void receiveResponse() throws IOException;
}
