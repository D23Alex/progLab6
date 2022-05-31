import java.io.IOException;
import java.net.DatagramSocket;

public class LogicServerMain implements IServerMain {
	
	private ILogicServerReceiver recieverModule;
	
	private IServerResponser responserModule;
	
	private ILogicServerCommandExecuter commandExecuterModule;
	
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

	public ILogicServerReceiver getRecieverModule() {
		return recieverModule;
	}

	public void setRecieverModule(ILogicServerReceiver recieverModule) {
		this.recieverModule = recieverModule;
	}

	public IServerResponser getResponserModule() {
		return responserModule;
	}

	public void setResponserModule(IServerResponser responderModule) {
		this.responserModule = responderModule;
	}

	public ILogicServerCommandExecuter getCommandExecuterModule() {
		return commandExecuterModule;
	}

	public void setCommandExecuterModule(ILogicServerCommandExecuter commandExecuterModule) {
		this.commandExecuterModule = commandExecuterModule;
	}

	public DatagramSocket getDatagramSocket() {
		return datagramSocket;
	}

	public void setDatagramSocket(DatagramSocket datagramSocket) {
		this.datagramSocket = datagramSocket;
	}

}
