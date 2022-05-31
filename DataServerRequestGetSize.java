
public class DataServerRequestGetSize extends DataServerRequest {

	public DataServerRequestGetSize(String collectionName) {
		super(collectionName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 792818688500636183L;

	@Override
	public void execute(ILogicToDataServerCommandExecuter executer, IRequestResponser responser) {
		responser.setAmount(executer.getCollectionSize());
	}

	

}
