import java.io.IOException;
import java.util.ArrayList;

/**
* Модуль, который занимается отправкой ответа.
* Другие модули могут добавлять что-то в формируемый ответ.
* Этот модуль лишь отправляет его куда надо.
* Модуль подходит как для логического сервера, так и для сервера БД.
* @author Алексей
*
*/
public interface IServerResponser {
	
	public void resetResponse();

	/**
	 * Метод посылает клиенту ответ от сервера.
	 * Куда слать узнаём из стейта, аргументов нет
	 * @throws IOException 
	 */
	public void sendResponse() throws IOException;
	
	public void add(Vehicle vehicle);
	
	public void addAll(ArrayList<Vehicle> vehicles);
	
	public void addProblem(String problem);
	
	public void setId(long id);
	
	public void setAmount(int amount);
	
	/**
	 * Определить успешность запроса, на который отвечаем, как "не успешный"
	 */
	public void makeRequestUnsuccessful();

}
