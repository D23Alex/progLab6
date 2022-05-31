import java.util.ArrayList;

public class LogicServerRequestShow extends LogicServerRequest {

	public LogicServerRequestShow(String collectionName) {
		super(collectionName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1647801063883491897L;

	/**
	 * @Override
	 * ¬ качестве ответа просим прислать все машины
	 */
	public void execute(IUserToServerCommandExecuter commandExecuter, IRequestResponser responser) {
		
		ArrayList<Vehicle> vehicles = commandExecuter.getAll();
		
		if (vehicles.size() < 1) {
			responser.setAmount(0);
		}
		else {
			responser.addAll(vehicles);
		}
	}

}
