
public class DataServerRequestAdd extends DataServerRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3230186949674441009L;
	
	private Vehicle vehicleToAdd;
	

	public DataServerRequestAdd(String collectionName, Vehicle vehicleToAdd) {
		super(collectionName);
		this.vehicleToAdd = vehicleToAdd;
	}
	
	/**
	 * @Override
	 * Просим прислать добавленный экземпляр
	 */
	public void execute(ILogicToDataServerCommandExecuter executer, IRequestResponser responser) {
		responser.setVehicle(executer.add(vehicleToAdd));
	}

	public Vehicle getVehicleToAdd() {
		return vehicleToAdd;
	}

	public void setVehicleToAdd(Vehicle vehicleToAdd) {
		this.vehicleToAdd = vehicleToAdd;
	}

}
