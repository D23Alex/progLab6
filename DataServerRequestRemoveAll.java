
public class DataServerRequestRemoveAll extends DataServerRequest {

	public DataServerRequestRemoveAll(String collectionName) {
		super(collectionName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8439812578444067258L;

	public void execute(ILogicToDataServerCommandExecuter executer, IRequestResponser responser) {
		executer.removeAll();
	}
	
}
