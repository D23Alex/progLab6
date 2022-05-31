import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;

/**
 * Почти все методы имеют такую структуру: создай запрос определённого вида к БД , отправь, получи ответ
 * Работа с сервером БД осуществляется именно этим классом.
 * В случае, когда ответ от БД не увенчался успехом,
 * методы этого класса возвращают мусор, в то же время делая запрос не успешным.
 * Тут нет бизнес-логики. Каждый метод - одно обращение к БД, получение одного ответа
 * @author Алексей
 *
 */
public class LogicServerClient implements ILogicServerClient, ILogicToDataServerCommandExecuter {
	
	private InetAddress dataServerAddress;
	
	private int dataServerPort;
	
	private IServerResponser responserModule;
	
	private IServerMain coreModule;
	
	private ILogicServerReceiver receiverModule;

	
	public LogicServerClient(InetAddress dataServerAddress, int dataServerPort) {
		super();
		this.dataServerAddress = dataServerAddress;
		this.dataServerPort = dataServerPort;
	}
	
	//TODO: если необходимо, то описание ошибок можно сделать здесь

	@Override
	public Vehicle getById(long id)  {
		try {
			this.sendReqestToDataServer(new DataServerRequestGetById(this.receiverModule.getCurrentRequest().getCollectionName(), id));
			IResponse response = this.receiveResponseFromDataServer();
			this.setUserResponseProblemsByDataServerResponse(response);
			return response.getVehicle();
		} catch (IOException e) {
			this.responserModule.addProblem("A database connection error > " + e.getMessage());
			this.responserModule.makeRequestUnsuccessful();
		}
		return null;
	}

	@Override
	public void removeById(long id) {
		try {
			this.sendReqestToDataServer(new DataServerRequestRemoveById(this.receiverModule.getCurrentRequest().getCollectionName(), id));
			IResponse response = this.receiveResponseFromDataServer();
			this.setUserResponseProblemsByDataServerResponse(response);
		} catch (IOException e) {
			this.responserModule.addProblem("A database connection error > " + e.getMessage());
			this.responserModule.makeRequestUnsuccessful();
		}
	}

	@Override
	public void updateById(Vehicle vehicle, long id) {
		try {
			this.sendReqestToDataServer(new DataServerRequestUpdateById(this.receiverModule.getCurrentRequest().getCollectionName(), id, vehicle));
			IResponse response = this.receiveResponseFromDataServer();
			this.setUserResponseProblemsByDataServerResponse(response);
		} catch (IOException e) {
			this.responserModule.addProblem("A database connection error > " + e.getMessage());
			this.responserModule.makeRequestUnsuccessful();
		}
	}

	@Override
	public Vehicle add(Vehicle vehicle) {
		try {
			this.sendReqestToDataServer(new DataServerRequestAdd(this.receiverModule.getCurrentRequest().getCollectionName(), vehicle));
			IResponse response = this.receiveResponseFromDataServer();
			this.setUserResponseProblemsByDataServerResponse(response);
			return response.getVehicle();
		} catch (IOException e) {
			this.responserModule.addProblem("A database connection error > " + e.getMessage());
			this.responserModule.makeRequestUnsuccessful();
		}
		return null;
	}

	@Override
	public void removeAll() {
		try {
			this.sendReqestToDataServer(new DataServerRequestRemoveAll(this.receiverModule.getCurrentRequest().getCollectionName()));
			IResponse response = this.receiveResponseFromDataServer();
			this.setUserResponseProblemsByDataServerResponse(response);
		} catch (IOException e) {
			this.responserModule.addProblem("A database connection error > " + e.getMessage());
			this.responserModule.makeRequestUnsuccessful();
		}
	}

	@Override
	public void addAll(ArrayList<Vehicle> vehicles) {
		try {
			this.sendReqestToDataServer(new DataServerRequestAddAll(this.receiverModule.getCurrentRequest().getCollectionName(), vehicles));
			IResponse response = this.receiveResponseFromDataServer();
			this.setUserResponseProblemsByDataServerResponse(response);
		} catch (IOException e) {
			this.responserModule.addProblem("A database connection error > " + e.getMessage());
			this.responserModule.makeRequestUnsuccessful();
		}
	}

	@Override
	public int getCollectionSize() {
		try {
			this.sendReqestToDataServer(new DataServerRequestGetSize(this.receiverModule.getCurrentRequest().getCollectionName()));
			IResponse response = this.receiveResponseFromDataServer();
			this.setUserResponseProblemsByDataServerResponse(response);
			return response.getAmount();
		} catch (IOException e) {
			this.responserModule.addProblem("A database connection error > " + e.getMessage());
			this.responserModule.makeRequestUnsuccessful();
		}
		return -1;
	}

	@Override
	public ArrayList<Vehicle> getAll() {
		try {
			this.sendReqestToDataServer(new DataServerRequestGetAll(this.receiverModule.getCurrentRequest().getCollectionName()));
			IResponse response = this.receiveResponseFromDataServer();
			this.setUserResponseProblemsByDataServerResponse(response);
			return response.getVehicles();
		} catch (IOException e) {
			this.responserModule.addProblem("A database connection error > " + e.getMessage());
			this.responserModule.makeRequestUnsuccessful();
		}
		return null;
	}
	
	/**
	 * Метод анализирует ответ от дата сервера, и если запрос не был успешным,
	 * то портим пользовательский запрос и добавляем проблемы запроса к БД в список проблем,
	 * который будет посылаться клиенту
	 * @param response - анализируемый ответ
	 */
	private void setUserResponseProblemsByDataServerResponse(IResponse response) {
		if (!response.isRequestSuccessful()) {
			this.responserModule.makeRequestUnsuccessful();
			for (String problem : response.getProblems()) {
				this.responserModule.addProblem(problem);
			}
		}
	}
	
	/**
	 * Посылает дата серверу указанный запрос
	 * @param request запрос, который присылаем дата серверу,
	 * на который рассчитываем получить ответ
	 * @throws IOException, если не удаётся отправить запрос серверу
	 */
	private void sendReqestToDataServer(IDataServerRequest request) throws IOException {
		byte[] serializedRequest = SerializationUtils.serialize(request);
		DatagramPacket packetToSend = new DatagramPacket(serializedRequest, 
				serializedRequest.length, dataServerAddress, dataServerPort);
		this.coreModule.getDatagramSocket().send(packetToSend);
	}
	
	/**
	 * Метод получает ответ от дата сервера в блокирующем режиме с помощью дейтаграммы
	 * @return объект-ответ, что дата сервер прислал нам на запрос
	 * @throws IOException, если получить ответ не удалось
	 */
	private IResponse receiveResponseFromDataServer() throws IOException {
		DatagramPacket datagramPacket = new DatagramPacket(new byte[10000], 10000);
		this.coreModule.getDatagramSocket().receive(datagramPacket);
		// если в этот момент запрос нам прислал кто-то другой, а не БД, то получи запрос от БД заного
		if (datagramPacket.getPort() != this.dataServerPort || !datagramPacket.getAddress().equals(this.dataServerAddress)) {
			return this.receiveResponseFromDataServer();
		}
		return (IResponse) SerializationUtils.deserialize(datagramPacket.getData());
	}

	public IServerResponser getResponderModule() {
		return responserModule;
	}

	public void setResponderModule(IServerResponser responderModule) {
		this.responserModule = responderModule;
	}

	public IServerMain getCoreModule() {
		return coreModule;
	}

	public void setCoreModule(IServerMain coreModule) {
		this.coreModule = coreModule;
	}

	public int getDataServerPort() {
		return dataServerPort;
	}

	public void setDataServerPort(int dataServerPort) {
		this.dataServerPort = dataServerPort;
	}

	public InetAddress getDataServerAddress() {
		return dataServerAddress;
	}

	public void setDataServerAddress(InetAddress dataServerAddress) {
		this.dataServerAddress = dataServerAddress;
	}

	public ILogicServerReceiver getReceiverModule() {
		return receiverModule;
	}

	public void setReceiverModule(ILogicServerReceiver receiverModule) {
		this.receiverModule = receiverModule;
	}


}
