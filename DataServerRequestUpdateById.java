
public class DataServerRequestUpdateById extends DataServerRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5263584154542714696L;

	private long vehicleToUpdateId;
	
	private Vehicle newVehicle;
	
	
	public DataServerRequestUpdateById(String collectionName, long vehicleToUpdateId, Vehicle newVehicle) {
		super(collectionName);
		this.vehicleToUpdateId = vehicleToUpdateId;
		this.newVehicle = newVehicle;
	}
	

	@Override
	public void execute(ILogicToDataServerCommandExecuter executer, IRequestResponser responser) {
		executer.updateById(newVehicle, vehicleToUpdateId);
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
