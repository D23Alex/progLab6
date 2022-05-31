
public class DataServerRequestGetById extends DataServerRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9172731765040355186L;
	
	private long id;
	
	
	public DataServerRequestGetById(String collectionName, long id) {
		super(collectionName);
		this.id = id;
	}


	@Override
	public void execute(ILogicToDataServerCommandExecuter executer, IRequestResponser responser) {
		responser.setVehicle(executer.getById(id));
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

}
