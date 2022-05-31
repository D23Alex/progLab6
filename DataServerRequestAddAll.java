import java.util.ArrayList;

public class DataServerRequestAddAll extends DataServerRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6150758608313176256L;
	
	private ArrayList<Vehicle> vehiclesToAdd;
	

	public DataServerRequestAddAll(String collectionName, ArrayList<Vehicle> vehiclesToAdd) {
		super(collectionName);
		this.vehiclesToAdd = vehiclesToAdd;
	}

	
	/**
	 * @Override
	 * ѕросим прислать количество добавленных машин
	 */
	public void execute(ILogicToDataServerCommandExecuter executer, IRequestResponser responser) {
		executer.addAll(vehiclesToAdd);
		responser.setAmount(vehiclesToAdd.size());
	}

	public ArrayList<Vehicle> getVehiclesToAdd() {
		return vehiclesToAdd;
	}

	public void setVehiclesToAdd(ArrayList<Vehicle> vehiclesToAdd) {
		this.vehiclesToAdd = vehiclesToAdd;
	}

}
