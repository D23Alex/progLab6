import java.util.ArrayList;

/**
 * Этот интерфейс служит для того, чтобы любые запросы обращались
 * по нему к серверам для формирования ответов таким образом,
 * каким угодно запрашивающему, но в пределах возможностей отвечающего. 
 * Это хорошее решение для всех типов запросов.
 * И для запросов к серверу с данными, и для запросов к серверам с логикой.
 * Поскольку структура ответа для вссех едина, то формировать ответ мы будем одинаково.
 * @author Алексей
 *
 */
public interface IRequestResponser {
	
	/**
	 * Запросить прислать этот объект машины в ответе
	 * @param vehicle - объект, который просим прислать нам в ответе
	 */
	public void add(Vehicle vehicle);
	
	/**
	 * Попросить прислать все эти объекты машин в ответе в указанном порядке
	 * @param vehicles - то, что нужно прислать
	 */
	public void addAll(ArrayList<Vehicle> vehicles);
	
	public void setAmount(int amount);
	
	public void setId(long id);
	
	public void setVehicle(Vehicle vehicle);
	
	public void addProblem(String problem);
	
	/**
	 * Определить успешность запроса, на который отвечаем, как "не успешный"
	 */
	public void makeRequestUnsuccessful();
}
