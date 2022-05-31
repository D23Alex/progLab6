
public class LogicServerRequestSort extends LogicServerRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1124965800999550509L;
	
	private Criteria criteria;
	

	public LogicServerRequestSort(String collectionName, Criteria criteria) {
		super(collectionName);
		this.criteria = criteria;
	}
	

	@Override
	public void execute(IUserToServerCommandExecuter commandExecuter, IRequestResponser responser) {
		commandExecuter.sort(criteria);
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

}
