
public class LogicServerRequestAdd extends LogicServerRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5579901731683874557L;
	
	/**
	 * ����������� ������
	 */
	private Vehicle vehicle;
	
	

	public LogicServerRequestAdd(String collectionName, Vehicle vehicle) {
		super(collectionName);
		this.vehicle = vehicle;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * @Override
	 * � ���� ������ �� ��������� ������ � ��������� � ������ ����� ���������� �������� � ���
	 */
	public void execute(IUserToServerCommandExecuter commandExecuter, IRequestResponser responser) {
		Vehicle vehicleAdded = commandExecuter.add(this.vehicle);
		responser.setVehicle(vehicleAdded);
	}



}
