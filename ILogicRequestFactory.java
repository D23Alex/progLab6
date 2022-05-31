import java.util.ArrayList;

/**
 * Интерфейс для фабрики пользовательских запросов. Паттерн фабричный метод
 * @author Алексей
 *
 */
public interface ILogicRequestFactory {

	/**
	 * Фабричный метод. Метод создаёт запрос пользователя к логическому серверу
	 * по данному набору из команды и всех её аргументов
	 * @param commandAndArguments - набор из команды и аргументов
	 * @return объект-запрос 
	 * @throws IllegalArgumentException, если аргументы неправильные
	 */
	public ILogicServerRequest createRequest(ArrayList<String> commandAndArguments) throws IndexOutOfBoundsException, IllegalArgumentException, Exception;


}
