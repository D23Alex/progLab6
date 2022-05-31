import java.io.IOException;
import java.net.DatagramSocket;

/**
 * ПЕРЕИМЕНОВАТЬ ЕГО СРОЧНО!
 * Этот интерфейс отвечает за работу основной части
 * ЛЮБОГО сервера - run(), init() и так далее
 * @author Алексей
 *
 */
public interface IServerMain {
	
	public void run() throws IOException;

	public void stop();
	
	public DatagramSocket getDatagramSocket();
	
}
