import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * ПЕРЕИМЕНОВАТЬ
 * В этом классе находится логика исполнения команд от пользователя логическим сервером.
 * Некоторые команды выполняются одним лишь запросом к БД(через клиентский модуль),
 * в то время как другие требуют дополнительной логики,
 * например, сортировка по определённому критерию, выборка и т.п.
 * @author Алексей
 *
 */
public class LogicServerCommandExecuter implements ILogicServerCommandExecuter, IUserToServerCommandExecuter {

	private ILogicToDataServerCommandExecuter databaseClientModule;
	
	private ILogicServerReceiver receiverModule;
	
	private IRequestResponser responserModule;
	
	
	@Override
	public void executeRequest(ILogicServerRequest currentRequest) {
		currentRequest.execute(this, this.responserModule);
	}

	@Override
	public Vehicle getById(long id) {
		return this.databaseClientModule.getById(id);
	}

	@Override
	public Vehicle getMin(Criteria criteria) {
		if (this.databaseClientModule.getAll().size() == 0) {
			//TODO: ВЫбросить что-то
			this.responserModule.makeRequestUnsuccessful();
			this.responserModule.addProblem("Your collection is empty so there is no max element");
			return null;
		}
		
		return this.databaseClientModule.getAll().stream().min(criteria.getComparator()).get();
	}

	@Override
	public Vehicle getMax(Criteria criteria) {
		if (this.databaseClientModule.getAll().size() == 0) {
			//TODO: ВЫбросить что-то
			this.responserModule.makeRequestUnsuccessful();
			this.responserModule.addProblem("Your collection is empty so there is no max element");
			return null;
		}
		
		//STREAM API
		return this.databaseClientModule.getAll().stream().max(criteria.getComparator()).get();
	}

	@Override
	public ArrayList<Vehicle> getAll() {
		return this.databaseClientModule.getAll();
	}

	@Override
	public Vehicle add(Vehicle vehicle) {
		return this.databaseClientModule.add(vehicle);	
	}

	@Override
	public void updateById(long id, Vehicle vehicle) {
		this.databaseClientModule.updateById(vehicle, id);
	}

	@Override
	public void removeById(long id) {
		this.databaseClientModule.removeById(id);
	}

	@Override
	public void removeAll() {
		this.databaseClientModule.removeAll();
	}

	@Override
	public void removeGreater(Vehicle vehicle, Criteria criteria) {
		// тут функциональщина не подходит так как нам именно нужно изменять Стейт
		ArrayList<Vehicle> vehicles = this.databaseClientModule.getAll();
		ArrayList<Vehicle> vehiclesToRemove = new ArrayList<>();
		for (Vehicle currentVehicle : vehicles) {
			if (criteria.getComparator().compare(currentVehicle, vehicle) > 0) {
				vehiclesToRemove.add(currentVehicle);
			}
		}
		for (Vehicle currentVehicle : vehiclesToRemove) {
			vehicles.remove(currentVehicle);
		}
		
		
	}

	@Override
	public void removeLower(Vehicle vehicle, Criteria criteria) {
		// Работаем со стейтом, не получится Stream API
		ArrayList<Vehicle> vehicles = this.databaseClientModule.getAll();
		ArrayList<Vehicle> vehiclesToRemove = new ArrayList<>();
		for (Vehicle currentVehicle : vehicles) {
			if (criteria.getComparator().compare(currentVehicle, vehicle) < 0) {
				vehiclesToRemove.add(currentVehicle);
			}
		}
		for (Vehicle currentVehicle : vehiclesToRemove) {
			vehicles.remove(currentVehicle);
		}
	}

	@Override
	public void sort(Criteria criteria) {
		ArrayList<Vehicle> vehicles = this.databaseClientModule.getAll();
		vehicles.sort(criteria.getComparator());
		// TODO: эксперементальный код
		databaseClientModule.removeAll();
		databaseClientModule.addAll(vehicles);
	}

	@Override
	public int getCollectionSize() {
		return this.databaseClientModule.getCollectionSize();
	}

	@Override
	public int getAmountLower(Vehicle vehicle, Criteria criteria) {
		return this.databaseClientModule.getAll().stream().filter(veh -> criteria.getComparator().compare(veh, vehicle) < 0).collect(Collectors.toList()).size();
	}

	public ILogicToDataServerCommandExecuter getDatabaseClientModule() {
		return databaseClientModule;
	}

	public void setDatabaseClientModule(ILogicToDataServerCommandExecuter databaseClientModule) {
		this.databaseClientModule = databaseClientModule;
	}

	public ILogicServerReceiver getReceiverModule() {
		return receiverModule;
	}

	public void setReceiverModule(ILogicServerReceiver receiverModule) {
		this.receiverModule = receiverModule;
	}

	public IRequestResponser getResponserModule() {
		return responserModule;
	}

	public void setResponserModule(IRequestResponser responserModule) {
		this.responserModule = responserModule;
	}
	
}
