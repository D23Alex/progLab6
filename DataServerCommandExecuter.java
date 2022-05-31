import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;


/**
 * Пока что это МОК
 * @author Алексей
 *
 */
public class DataServerCommandExecuter implements IDataServerCommandExecuter, ILogicToDataServerCommandExecuter {
	
	IDataServerReceiver receiverModule;
	
	private IRequestResponser responserModule;
	
	@Override
	public void executeRequest(IDataServerRequest currentRequest) {
		currentRequest.execute(this, this.responserModule);
	}

	@Override
	public Vehicle getById(long id) {
		try {
			VehicleCollection vehicleCollection = this.getCollectionFromFile(this.receiverModule.getCurrentRequest().getCollectionName());
			for (Vehicle currentVehicle : vehicleCollection.getCollection()) {
				if (currentVehicle.getId().equals(id)) {
					return currentVehicle;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// если мы тут, то не нашли, надо испортить запрос и отправить какой-нибудь мусор
		this.responserModule.addProblem("The vehicle with id " + id + " doesn't exist");
		this.responserModule.makeRequestUnsuccessful();
		
		return null;
	}

	@Override
	public void removeById(long id) {
		try {
			VehicleCollection vehicleCollection = this.getCollectionFromFile(this.receiverModule.getCurrentRequest().getCollectionName());
			Vehicle vehicleToDelete = null;
			for (Vehicle currentVehicle : vehicleCollection.getCollection()) {
				if (currentVehicle.getId().equals(id)) {
					vehicleToDelete = currentVehicle;
				}
			}
			if (vehicleToDelete != null) {
				// нашли - удаляем
				vehicleCollection.getCollection().remove(vehicleToDelete);
				this.saveToFile(vehicleCollection, this.receiverModule.getCurrentRequest().getCollectionName());
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// если мы тут, то не нашли, надо испортить запрос и отправить какой-нибудь мусор
		this.responserModule.addProblem("The vehicle with id " + id + " doesn't exist");
		this.responserModule.makeRequestUnsuccessful();
	}

	@Override
	public void updateById(Vehicle vehicle, long id) {
		try {
			VehicleCollection vehicleCollection = this.getCollectionFromFile(this.receiverModule.getCurrentRequest().getCollectionName());
			vehicle.setCreationDate(LocalDate.now());
			vehicle.setId(id);
			// нельзя просто поменять элемент во время итерации, можно только вот эту срань с дополнительным стэком
			int lim1 = vehicleCollection.getCollection().size();
			Stack<Vehicle> bufferedStack = new Stack<>();
			for (int i = 0; i < lim1; i++) {
				Vehicle currentVehicle = vehicleCollection.getCollection().pop();
				if (currentVehicle.getId() == id) {
					vehicleCollection.getCollection().push(vehicle);
					int lim2 = bufferedStack.size();
					for (int j = 0; j < lim2; j++) {
						vehicleCollection.getCollection().push(bufferedStack.pop());
					}
					this.saveToFile(vehicleCollection, this.receiverModule.getCurrentRequest().getCollectionName());
					return;
				}
				else {
					bufferedStack.push(currentVehicle);
				}
			}
			// если мы тут, то не нашлось техники с таким id
			int lim3 = bufferedStack.size();
			for (int i = 0; i < lim3; i++) {
				vehicleCollection.getCollection().push(bufferedStack.pop());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// если мы тут, то не нашли, надо испортить запрос и отправить какой-нибудь мусор
		this.responserModule.addProblem("The vehicle with id " + id + " doesn't exist");
		this.responserModule.makeRequestUnsuccessful();
	}

	@Override
	public Vehicle add(Vehicle vehicle) {
		try {
			VehicleCollection vehicleCollection = this.getCollectionFromFile(this.receiverModule.getCurrentRequest().getCollectionName());
			
			vehicle.setCreationDate(LocalDate.now());
			vehicle.setId(this.getMinAppropriateId(vehicleCollection.getCollection()));
			
			vehicleCollection.getCollection().add(vehicle);
			this.saveToFile(vehicleCollection, this.receiverModule.getCurrentRequest().getCollectionName());
			return vehicle;
		} catch (Exception e) {
			this.responserModule.addProblem("The vehicle could not be added");
			this.responserModule.makeRequestUnsuccessful();
			return null;
		}
	}

	@Override
	public void removeAll() {
		try {
			VehicleCollection vehicleCollection = this.getCollectionFromFile(this.receiverModule.getCurrentRequest().getCollectionName());
			vehicleCollection.getCollection().clear();
			this.saveToFile(vehicleCollection, this.receiverModule.getCurrentRequest().getCollectionName());
		} catch (Exception e) {
			this.responserModule.addProblem("The vehicle collection could not be cleared");
			this.responserModule.makeRequestUnsuccessful();
		}
	}

	/**
	 * @Override
	 * Метод не меняет никакие параметры машин, в отличае от добавления одной машины
	 */
	public void addAll(ArrayList<Vehicle> vehicles) {
		try {
			VehicleCollection vehicleCollection = this.getCollectionFromFile(this.receiverModule.getCurrentRequest().getCollectionName());
			
			for (Vehicle vehicle : vehicles) {
				vehicleCollection.getCollection().add(vehicle);
			}
			this.saveToFile(vehicleCollection, this.receiverModule.getCurrentRequest().getCollectionName());
		} catch (Exception e) {
			this.responserModule.addProblem("The vehicles could not be added");
			this.responserModule.makeRequestUnsuccessful();
		}
	}

	@Override
	public int getCollectionSize() {
		try {
			VehicleCollection vehicleCollection = this.getCollectionFromFile(this.receiverModule.getCurrentRequest().getCollectionName());
			
			return vehicleCollection.getCollection().size();
			
		} catch (Exception e) {
			this.responserModule.addProblem("A problem has occured when trying to access your collection on the Data server");
			this.responserModule.makeRequestUnsuccessful();
			return 0;
		}
	}

	@Override
	public ArrayList<Vehicle> getAll() {
		try {
			VehicleCollection vehicleCollection = this.getCollectionFromFile(this.receiverModule.getCurrentRequest().getCollectionName());
			ArrayList<Vehicle> toReturn = new ArrayList<Vehicle>();
			
			for (Vehicle vehicle : vehicleCollection.getCollection()) {
				toReturn.add(vehicle);
			}
			
			return toReturn;
		} catch (Exception e) {
			this.responserModule.addProblem("A problem has occured when trying to access your collection on the Data server");
			this.responserModule.makeRequestUnsuccessful();
			return null;
		}
	}
	
	/**
	 * Метод сохраняет указанную коллекцию под указанным именем.
	 * Если Коллекции под таким именем не существует, то считаем,
	 * что необходимо создать новый файл и сохранить туда.
	 * @param vehicleCollection коллекция, которую надо сохранить
	 * @param collectionName название коллекции, от которого зависит, куда сохранять
	 * @throws Exception, если проблемы с сохранением (очень маловероятно, отчитываться никому не будем)
	 */
	private void saveToFile(VehicleCollection vehicleCollection, String collectionName) throws Exception {
	    
		JAXBContext context;
		context = JAXBContext.newInstance(VehicleCollection.class);
		Marshaller mar= context.createMarshaller();
	    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    OutputStream os = new FileOutputStream("./collections/" + collectionName + ".xml");
	    mar.marshal(vehicleCollection, os);
	}
	
	/**
	 * Поведение этого метода таково - если файл есть, то взять коллекцию из него,
	 * иначе создать новый файл с пустой коллекцией и вернуть эту пустую коллекцию
	 * @param collectionName - имя, по которому нужно доставать коллекцию
	 * @return коллекцию
	 * @throws Exception, если ошибка IO или JAXB - маловероятно
	 */
	private VehicleCollection getCollectionFromFile(String collectionName) throws Exception {
		
		File file = new File("./collections/" + collectionName + ".xml");
		if (!file.exists()) {
			VehicleCollection emptyCollection = new VehicleCollection(new Stack<Vehicle>());
			this.saveToFile(emptyCollection, collectionName);
			// теперь этот файл с пустой коллекцией существует, РЕКУРСИВНО его же и вызовем
			return this.getCollectionFromFile(collectionName);
		}
		
		JAXBContext context = JAXBContext.newInstance(VehicleCollection.class);
	    return (VehicleCollection) context.createUnmarshaller()
	      .unmarshal(new FileReader("./collections/" + collectionName + ".xml"));
	}
	
	private long getMinAppropriateId(Stack<Vehicle> vehicleStack) {
		if (vehicleStack.size() == 0)
			return 1;
		
		// массив по дефолту все false
		boolean IdsTaken[] = new boolean[vehicleStack.size() + 1];
		Long currentId;
		
		for (Vehicle currentVehicle : vehicleStack) {
			currentId = currentVehicle.getId();
			if (currentId <= vehicleStack.size()) {
				IdsTaken[currentId.intValue()] = true;
			}
		}
		
		int i;
		for (i = 1; i <= vehicleStack.size(); i++) {
			if (!IdsTaken[i]) {
				return i;
			}
		}
		return i;
	}

	public IDataServerReceiver getReceiverModule() {
		return receiverModule;
	}

	public void setReceiverModule(IDataServerReceiver receiverModule) {
		this.receiverModule = receiverModule;
	}

	public IRequestResponser getResponserModule() {
		return responserModule;
	}

	public void setResponserModule(IRequestResponser responserModule) {
		this.responserModule = responserModule;
	}
}
