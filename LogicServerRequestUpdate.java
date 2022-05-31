
public class LogicServerRequestUpdate extends LogicServerRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3683883742011326251L;

	private long vehicleToUpdateId;
	
	private Vehicle newVehicle;

	
	public LogicServerRequestUpdate(String collectionName, long vehicleToUpdateId, Vehicle newVehicle) {
		super(collectionName);
		this.vehicleToUpdateId = vehicleToUpdateId;
		this.newVehicle = newVehicle;
	}

	
	/**
	 * @Override
	 * В качестве ответа просим вернуть ID 
	 */
	public void execute(IUserToServerCommandExecuter commandExecuter, IRequestResponser responser) {
		commandExecuter.updateById(vehicleToUpdateId, newVehicle);
		responser.setId(vehicleToUpdateId);
	}

	public long getVehicleToUpdateId() {
		return vehicleToUpdateId;
	}

	public void setVehicleToUpdateId(long vehicleToUpdateId) {
		this.vehicleToUpdateId = vehicleToUpdateId;
	}

	public Vehicle getNewVehicle() {
		return newVehicle;
	}

	public void setNewVehicle(Vehicle newVehicle) {
		this.newVehicle = newVehicle;
	}

}
