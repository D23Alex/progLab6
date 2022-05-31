import java.time.LocalDate;
import java.util.ArrayList;

public class LogicServerRequestFactoryDefault implements ILogicRequestFactory {
	
	private IUserClientCore coreModule;
	
	//TODO: написать все фразы

	@Override
	public ILogicServerRequest createRequest(ArrayList<String> commandAndArguments) 
			throws IllegalArgumentException, IndexOutOfBoundsException {
		String command = commandAndArguments.get(0).toLowerCase();
		try {
			
			//TODO: Реализовать все команды
			if (command.equals("add")) {
				return new LogicServerRequestAdd(this.coreModule.getCurrentCollectionName(),
						this.createVehicleByCommandAndArguments(commandAndArguments, 1));
			}
			if (command.equals("info")) {
				return new LogicServerRequestInfo(this.coreModule.getCurrentCollectionName());
			}
			if (command.equals("show")) {
				return new LogicServerRequestShow(this.coreModule.getCurrentCollectionName());
			}
			if (command.equals("update")) {
				return new LogicServerRequestUpdate(this.coreModule.getCurrentCollectionName(),
						Long.parseLong(commandAndArguments.get(1)),
						this.createVehicleByCommandAndArguments(commandAndArguments, 2));
			}
			if (command.equals("remove_by_id")) {
				return new LogicServerRequestRemove(this.coreModule.getCurrentCollectionName(),
						Long.parseLong(commandAndArguments.get(1)));
			}
			if (command.equals("clear")) {
				return new LogicServerRequestClear(this.coreModule.getCurrentCollectionName());
			}
			if (command.equals("remove_greater")) {
				return new LogicServerRequestRemoveGreater(this.coreModule.getCurrentCollectionName(),
						this.createVehicleByCommandAndArguments(commandAndArguments,2),
						Criteria.getCriteriaByName(commandAndArguments.get(1)));
			}
			if (command.equals("remove_lower")) {
				return new LogicServerRequestRemoveLower(this.coreModule.getCurrentCollectionName(),
						this.createVehicleByCommandAndArguments(commandAndArguments, 2),
						Criteria.getCriteriaByName(commandAndArguments.get(1)));
			}
			if (command.equals("sort")) {
				return new LogicServerRequestSort(this.coreModule.getCurrentCollectionName(),
						Criteria.getCriteriaByName(commandAndArguments.get(1)));
			}
			if (command.equals("max")) {
				return new LogicServerRequestGetMax(this.coreModule.getCurrentCollectionName(),
						Criteria.getCriteriaByName(commandAndArguments.get(1)));
			}
			if (command.equals("group_counting_by_engine_power")) {
				return new LogicServerRequestGroupPower(this.coreModule.getCurrentCollectionName());
			}
			if (command.equals("count_greater_than_engine_power")) {
				return new LogicServerRequestCountGreaterPower(this.coreModule.getCurrentCollectionName(),
						Long.parseLong(commandAndArguments.get(1)));
			}
			
			
			throw new IllegalArgumentException("Нет такой команды");	
				
		} catch (IllegalArgumentException e1) {
			throw e1;
		} catch (IndexOutOfBoundsException e2) {
			// Мало аргументов
			throw e2;
		} catch (Exception e3) {
			//TODO: произошло что-то непонятное, выбросить IllegalargumentException без конкретики
			throw new IllegalArgumentException("У вас какая-то ошибка в аргументах");
			
		}
		
	}
	
	/**
	 * Метод получает аррейлист команды и аргументов.
	 * Первая штука - команда, а последующие - это аргументы для машины
	 * @param vehicleTypeArgPosition позиция, с которой начинаются аргументы для машины
	 * @return созданный объект машины
	 */
	private Vehicle createVehicleByCommandAndArguments(ArrayList<String> commandAndArguments, int vehicleTypeArgPosition) {
		VehicleType vehicleType = VehicleType.getVehicleTypeByName(commandAndArguments.get(vehicleTypeArgPosition + 0));
		String name = commandAndArguments.get(vehicleTypeArgPosition + 1);
		Coordinates coordinates = new Coordinates(Integer.parseInt(commandAndArguments.get(vehicleTypeArgPosition + 2)),
				Float.parseFloat(commandAndArguments.get(vehicleTypeArgPosition + 3)));
		long enginePower = Long.parseLong(commandAndArguments.get(vehicleTypeArgPosition + 4));
		FuelType fuelType = FuelType.getFuelTypeByName(commandAndArguments.get(vehicleTypeArgPosition + 5));
		
		return new Vehicle(vehicleType, -1l, name, coordinates, LocalDate.now(), enginePower, fuelType);
	}

	public IUserClientCore getCoreModule() {
		return coreModule;
	}

	public void setCoreModule(IUserClientCore coreModule) {
		this.coreModule = coreModule;
	}

	

}
