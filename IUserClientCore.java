import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public interface IUserClientCore extends IStructure {
	public String getCurrentCollectionName();
	public SocketAddress getSocketAddress();
	public DatagramChannel getDatagramChannel();
	public void run();
}
