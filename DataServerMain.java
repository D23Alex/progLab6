import java.io.IOException;
import java.net.DatagramSocket;

public class DataServerMain implements IServerMain {

	private IDataServerReceiver recieverModule;
	
	private IServerResponser responserModule;
	
	private IDataServerCommandExecuter commandExecuterModule;
	
	private DatagramSocket datagramSocket;

	@Override
	public void run() throws IOException {
		while (true) {
			System.out.println("Ready for a new request");
			this.recieverModule.receive();
			System.out.println("Got a new request - executing");
			this.responserModule.resetResponse();
			this.commandExecuterModule.executeRequest(this.recieverModule.getCurrentRequest());
			System.out.println("Request executed - sending the response");
			this.responserModule.sendResponse();
			System.out.println("Response sent");
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DatagramSocket getDatagramSocket() {
		return this.datagramSocket;
	}

	public IDataServerReceiver getRecieverModule() {
		return recieverModule;
	}

	public void setRecieverModule(IDataServerReceiver recieverModule) {
		this.recieverModule = recieverModule;
	}

	public IServerResponser getResponserModule() {
		return responserModule;
	}

	public void setResponserModule(IServerResponser responserModule) {
		this.responserModule = responserModule;
	}

	public IDataServerCommandExecuter getCommandExecuterModule() {
		return commandExecuterModule;
	}

	public void setCommandExecuterModule(IDataServerCommandExecuter commandExecuterModule) {
		this.commandExecuterModule = commandExecuterModule;
	}

	public void setDatagramSocket(DatagramSocket datagramSocket) {
		this.datagramSocket = datagramSocket;
	}
	
	
	
}
