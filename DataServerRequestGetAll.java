
public class DataServerRequestGetAll extends DataServerRequest {

	public DataServerRequestGetAll(String collectionName) {
		super(collectionName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8894360756781826297L;

	@Override
	public void execute(ILogicToDataServerCommandExecuter executer, IRequestResponser responser) {
		responser.addAll(executer.getAll());
	}

}
