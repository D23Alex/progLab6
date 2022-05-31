
/**
 * ƒоговорились выводить не просто количество, а даже и сами элементы
 * @author јлексей
 *
 */
public class LogicServerRequestCountGreaterPower extends LogicServerRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1957066279515618973L;
	
	private long enginePowerToCompare;
	
	
	public LogicServerRequestCountGreaterPower(String collectionName, long enginePowerToCompare) {
		super(collectionName);
		this.enginePowerToCompare = enginePowerToCompare;
	}
	

	/**
	 * @Override
	 * »терируем через всю коллекцию и , сравнива€ engine_power с заданным значением,
	 * просим прислать таких, то побеждают в сравнении
	 */
	public void execute(IUserToServerCommandExecuter commandExecuter, IRequestResponser responser) {
		for (Vehicle currentVehicle : commandExecuter.getAll()) {
			if (currentVehicle.getEnginePower() > this.getEnginePowerToCompare()) {
				responser.add(currentVehicle);
			}
		}

	}

	public long getEnginePowerToCompare() {
		return enginePowerToCompare;
	}

	public void setEnginePowerToCompare(long enginePowerToCompare) {
		this.enginePowerToCompare = enginePowerToCompare;
	}

}
