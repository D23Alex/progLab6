
public class LogicServerRequestClear extends LogicServerRequest {

	public LogicServerRequestClear(String collectionName) {
		super(collectionName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6903204956564726032L;

	/**
	 * @Override
	 * ����������� � �������� ������ ���������� �������� ���������
	 */
	public void execute(IUserToServerCommandExecuter commandExecuter, IRequestResponser responser) {
		responser.setAmount(commandExecuter.getCollectionSize());
		commandExecuter.removeAll();
	}

}
