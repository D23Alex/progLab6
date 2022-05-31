import java.util.ArrayList;

/**
 * Интерфейс для взаимодействия с чем-либо, что является БД или служит для работы с БД
 * @author Алексей
 *
 */
public interface ILogicToDataServerCommandExecuter {
	
	public Vehicle getById(long id);
	
	public void removeById(long id);
	
	public void updateById(Vehicle vehicle, long id);
	
	public Vehicle add(Vehicle vehicle);
	
	public void removeAll();
	
	public void addAll(ArrayList<Vehicle> vehicles);
	
	public int getCollectionSize();
	
	public ArrayList<Vehicle> getAll();
}
