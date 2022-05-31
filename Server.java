import java.io.IOException;

/**
 * Ќаследники этого абстрактного класса - фасады серверов.
 * «десь лишь описываютс€ обща€ структура, общее поведение
 * @author јлексей
 *
 */
public abstract class Server implements IServer {
	
	/**
	 * ќсновной модуль фасада, который руководит остальными.
	 */
	private IServerMain coreModule;
	
	/**
	 * ћодуль, который занимаетс€ отправкой ответа.
	 * ƒругие модули могут добавл€ть что-то в формируемый ответ.
	 * Ётот модуль лишь отправл€ет его куда надо
	 */
	private IServerResponser responderModule;
	
	
	public Server(IServerMain coreModule, IServerResponser responderModule) {
		super();
		this.coreModule = coreModule;
		this.responderModule = responderModule;
	}

	@Override
	public void run() throws IOException {
		this.coreModule.run();
		
	}
	
	@Override
	public void stop() {
		this.coreModule.stop();
	}

	public IServerMain getCoreModule() {
		return coreModule;
	}

	public void setCoreModule(IServerMain coreModule) {
		this.coreModule = coreModule;
	}

	public IServerResponser getResponderModule() {
		return responderModule;
	}

	public void setResponderModule(IServerResponser responderModule) {
		this.responderModule = responderModule;
	}
}
