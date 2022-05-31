
public class DataServerRequestRemoveById extends DataServerRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4713638507001820841L;
	
	private long id;
	
	
	public DataServerRequestRemoveById(String collectionName, long id) {
		super(collectionName);
		this.id = id;
	}

	
	@Override
	public void execute(ILogicToDataServerCommandExecuter executer, IRequestResponser responser) {
		executer.removeById(id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

}
