
public interface ILogicServerRequest extends IRequest {

	/**
	 * ƒанный  метод выступает контейнером дл€ непосредственных обращений
	 * к Ћогическому серверу.  аждый запрос, соответственно,
	 * имеет собственный пор€док
	 * @param server Ћогический сервер, к которому обращаетс€ request
	 */
	public void execute(IUserToServerCommandExecuter commandExecuter, IRequestResponser responser);
	
	
}
