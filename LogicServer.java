import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

/**
 * Это тот класс, что стоит за фасадом логического сервера.
 * Он опирается на несколько описанных далее модулей.
 * Основной loop сервера в модуле coreModule
 * @author Алексей
 *
 */
public class LogicServer extends Server implements ILogicServer {
	
	/**
	 * Модуль приёма подкючений/запросов
	 */
	private ILogicServerReceiver recieverModule;
	
	/**
	 * Модуль для обращения к серверу базы данных
	 */
	private ILogicServerClient databaseClientModule;
	
	/**
	 * Модуль выполнения команд
	 */
	private ILogicServerCommandExecuter commandExecuterModule;
	
	


	public LogicServer(IServerMain coreModule, IServerResponser responderModule, ILogicServerReceiver recieverModule,
			ILogicServerClient databaseClientModule, ILogicServerCommandExecuter commandExecuterModule) {
		super(coreModule, responderModule);
		this.recieverModule = recieverModule;
		this.databaseClientModule = databaseClientModule;
		this.commandExecuterModule = commandExecuterModule;
	}

	public static void main(String args[]) throws IOException {
		int serverPort = 6789;
		String dataServerName = "localhost";
		int dataServerPort = 9876;
		
		Map<String, String> env = System.getenv();
        if (env.containsKey("DBPORT")) {
        	dataServerPort = Integer.parseInt(env.get("DBPORT"));
        }
        
        if (env.containsKey("LOGICPORT")) {
        	serverPort = Integer.parseInt(env.get("LOGICPORT"));
        }
        
        
		
		// Компоненты логического сервера
		LogicServerClient logicServerClient = new LogicServerClient(InetAddress.getByName(dataServerName), dataServerPort);
		LogicServerCommandExecuter logicServerCommandExecuter = new LogicServerCommandExecuter();
		LogicServerMain logicServerMain = new LogicServerMain();
		LogicServerReceiver logicServerReceiver = new LogicServerReceiver();
		LogicServerResponser logicServerResponser = new LogicServerResponser();
		
		logicServerClient.setResponderModule(logicServerResponser);
		logicServerClient.setCoreModule(logicServerMain);
		logicServerClient.setReceiverModule(logicServerReceiver);
		
		logicServerCommandExecuter.setDatabaseClientModule(logicServerClient);
		logicServerCommandExecuter.setReceiverModule(logicServerReceiver);
		logicServerCommandExecuter.setResponserModule(logicServerResponser);
		
		logicServerMain.setCommandExecuterModule(logicServerCommandExecuter);
		logicServerMain.setRecieverModule(logicServerReceiver);
		logicServerMain.setResponserModule(logicServerResponser);
		logicServerMain.setDatagramSocket(new DatagramSocket(serverPort));
		
		logicServerReceiver.setCoreModule(logicServerMain);
		
		logicServerResponser.setCoreModule(logicServerMain);
		logicServerResponser.setReceiverModule(logicServerReceiver);
		
		
		IServer logicServer = new LogicServer(logicServerMain, logicServerResponser, logicServerReceiver, logicServerClient, logicServerCommandExecuter);
	
		logicServer.run();
	}

	public ILogicServerReceiver getRecieverModule() {
		return recieverModule;
	}

	public void setRecieverModule(ILogicServerReceiver recieverModule) {
		this.recieverModule = recieverModule;
	}

	public ILogicServerClient getDatabaseClientModule() {
		return databaseClientModule;
	}

	public void setDatabaseClientModule(ILogicServerClient databaseClientModule) {
		this.databaseClientModule = databaseClientModule;
	}

	public ILogicServerCommandExecuter getCommandExecuterModule() {
		return commandExecuterModule;
	}

	public void setCommandExecuterModule(ILogicServerCommandExecuter commandExecuterModule) {
		this.commandExecuterModule = commandExecuterModule;
	}
	
}
