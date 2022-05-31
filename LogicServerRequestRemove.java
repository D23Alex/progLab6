
public class LogicServerRequestRemove extends LogicServerRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1014300371689643181L;
	
	private long vehicleToRemoveId;

	
	public LogicServerRequestRemove(String collectionName, long vehicleToRemoveId) {
		super(collectionName);
		this.vehicleToRemoveId = vehicleToRemoveId;
	}

	
	/**
	 * Просим прислать Id удалённого по Id элемента O_o
	 * @Override
	 */
	public void execute(IUserToServerCommandExecuter commandExecuter, IRequestResponser responser) {
		commandExecuter.removeById(vehicleToRemoveId);
		responser.setId(vehicleToRemoveId);

	}

	public long getVehicleToRemoveId() {
		return vehicleToRemoveId;
	}

	public void setVehicleToRemoveId(long vehicleToRemoveId) {
		this.vehicleToRemoveId = vehicleToRemoveId;
	}

}
