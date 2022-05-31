
public class LogicServerRequestGetMax extends LogicServerRequest{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8113796422969917137L;
	
	private Criteria criteria;

	
	public LogicServerRequestGetMax(String collectionName, Criteria criteria) {
		super(collectionName);
		this.criteria = criteria;
	}

	
	/**
	 * @Override
	 * Просим прислать наибольший элемент по заданному критерию
	 */
	public void execute(IUserToServerCommandExecuter commandExecuter, IRequestResponser responser) {
		responser.setVehicle(commandExecuter.getMax(this.criteria));
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

}
