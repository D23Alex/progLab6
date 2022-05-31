
public interface IDataServerRequest extends IRequest{

	
	public void execute(ILogicToDataServerCommandExecuter executer, IRequestResponser responser);
}
