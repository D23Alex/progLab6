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
 * ���� ��� ��� ���
 * @author �������
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
		
		// ���� �� ���, �� �� �����, ���� ��������� ������ � ��������� �����-������ �����
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
				// ����� - �������
				vehicleCollection.getCollection().remove(vehicleToDelete);
				this.saveToFile(vehicleCollection, this.receiverModule.getCurrentRequest().getCollectionName());
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ���� �� ���, �� �� �����, ���� ��������� ������ � ��������� �����-������ �����
		this.responserModule.addProblem("The vehicle with id " + id + " doesn't exist");
		this.responserModule.makeRequestUnsuccessful();
	}

	@Override
	public void updateById(Vehicle vehicle, long id) {
		try {
			VehicleCollection vehicleCollection = this.getCollectionFromFile(this.receiverModule.getCurrentRequest().getCollectionName());
			vehicle.setCreationDate(LocalDate.now());
			vehicle.setId(id);
			// ������ ������ �������� ������� �� ����� ��������, ����� ������ ��� ��� ����� � �������������� ������
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
			// ���� �� ���, �� �� ������� ������� � ����� id
			int lim3 = bufferedStack.size();
			for (int i = 0; i < lim3; i++) {
				vehicleCollection.getCollection().push(bufferedStack.pop());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ���� �� ���, �� �� �����, ���� ��������� ������ � ��������� �����-������ �����
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
	 * ����� �� ������ ������� ��������� �����, � ������� �� ���������� ����� ������
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
	 * ����� ��������� ��������� ��������� ��� ��������� ������.
	 * ���� ��������� ��� ����� ������ �� ����������, �� �������,
	 * ��� ���������� ������� ����� ���� � ��������� ����.
	 * @param vehicleCollection ���������, ������� ���� ���������
	 * @param collectionName �������� ���������, �� �������� �������, ���� ���������
	 * @throws Exception, ���� �������� � ����������� (����� ������������, ������������ ������ �� �����)
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
	 * ��������� ����� ������ ������ - ���� ���� ����, �� ����� ��������� �� ����,
	 * ����� ������� ����� ���� � ������ ���������� � ������� ��� ������ ���������
	 * @param collectionName - ���, �� �������� ����� ��������� ���������
	 * @return ���������
	 * @throws Exception, ���� ������ IO ��� JAXB - ������������
	 */
	private VehicleCollection getCollectionFromFile(String collectionName) throws Exception {
		
		File file = new File("./collections/" + collectionName + ".xml");
		if (!file.exists()) {
			VehicleCollection emptyCollection = new VehicleCollection(new Stack<Vehicle>());
			this.saveToFile(emptyCollection, collectionName);
			// ������ ���� ���� � ������ ���������� ����������, ���������� ��� �� � �������
			return this.getCollectionFromFile(collectionName);
		}
		
		JAXBContext context = JAXBContext.newInstance(VehicleCollection.class);
	    return (VehicleCollection) context.createUnmarshaller()
	      .unmarshal(new FileReader("./collections/" + collectionName + ".xml"));
	}
	
	private long getMinAppropriateId(Stack<Vehicle> vehicleStack) {
		if (vehicleStack.size() == 0)
			return 1;
		
		// ������ �� ������� ��� false
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
