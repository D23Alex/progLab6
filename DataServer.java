import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Map;

public class DataServer extends Server implements IDataServer {

	/**
	 * Модуль приёма подкючений/запросов
	 */
	private IDataServerReceiver recieverModule;
	
	/**
	 * Модуль выполнения команд
	 */
	private IDataServerCommandExecuter commandExecuterModule;

	public DataServer(IServerMain coreModule, IServerResponser responderModule, IDataServerReceiver recieverModule,
			IDataServerCommandExecuter commandExecuterModule) {
		super(coreModule, responderModule);
		this.recieverModule = recieverModule;
		this.commandExecuterModule = commandExecuterModule;
	}
	
	public static void main(String args[]) throws IOException {
		int serverPort = 9876;
		Map<String, String> env = System.getenv();
        if (env.containsKey("DBPORT")) {
        	serverPort = Integer.parseInt(env.get("DBPORT"));
        }
		
		// Компоненты дата сервера
		DataServerCommandExecuter dataServerCommandExecuter = new DataServerCommandExecuter();
		DataServerMain dataServerMain = new DataServerMain();
		DataServerReceiver dataServerReceiver = new DataServerReceiver();
		DataServerResponser dataServerResponser = new DataServerResponser();
		
		dataServerCommandExecuter.setReceiverModule(dataServerReceiver);
		dataServerCommandExecuter.setResponserModule(dataServerResponser);
		
		dataServerMain.setCommandExecuterModule(dataServerCommandExecuter);
		dataServerMain.setRecieverModule(dataServerReceiver);
		dataServerMain.setResponserModule(dataServerResponser);
		dataServerMain.setDatagramSocket(new DatagramSocket(serverPort));
		
		dataServerReceiver.setCoreModule(dataServerMain);
		
		dataServerResponser.setCoreModule(dataServerMain);
		dataServerResponser.setReceiverModule(dataServerReceiver);
		
		IServer dataServer = new DataServer(dataServerMain, dataServerResponser, dataServerReceiver, dataServerCommandExecuter);
		
		dataServer.run();
		
	}

	public IDataServerReceiver getRecieverModule() {
		return recieverModule;
	}

	public void setRecieverModule(IDataServerReceiver recieverModule) {
		this.recieverModule = recieverModule;
	}

	public IDataServerCommandExecuter getCommandExecuterModule() {
		return commandExecuterModule;
	}

	public void setCommandExecuterModule(IDataServerCommandExecuter commandExecuterModule) {
		this.commandExecuterModule = commandExecuterModule;
	}
	
	

}
