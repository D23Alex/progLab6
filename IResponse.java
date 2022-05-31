import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * ќтвет от сервера на запрос. –ешено ответы как от логического сервера,
 * так и от сервера с данными реализовать 1 классом, так как трубовани€ не отличаютс€
 * @author јлексей
 *
 */
public interface IResponse extends Serializable {
	
	/**
	 * ћетод сообщает, €вл€етс€ ли этот ответ ответом на успешный
	 * запрос или ответом на неуспешный запрос
	 * @return true, если запрос, ответ на который реализует этот интерфейс,
	 * был выполнен успешно, иначе falses
	 */
	public boolean isRequestSuccessful();
	
	public void setRequestSuccessful(boolean requestSuccessful);

	public HashSet<String> getProblems();
	
	public void addProblem(String problem);
	
	public long getId();
	
	public int getAmount();
	
	public Vehicle getVehicle();
	
	public ArrayList<Vehicle> getVehicles();
	
	/**
	 * ћетод добавл€ет указанный объект к возвращаемому ArrayList в Respons'е
	 * @param vehicle то что надо добавить
	 */
	public void addVehicleToList(Vehicle vehicle);
	
	/**
	 * выставл€ет данный аргумент как значение Vehicle в Respond'е
	 * @param vehicle - машина, которую надо выставить
	 */
	public void setVehicle(Vehicle vehicle);
	
	public void setId(long id);
	
	public void setAmount(int amount);
	
	
	
}
