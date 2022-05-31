import java.util.ArrayList;

/**
 * СРОЧНО ПЕРЕИМЕНОВАТЬ
 * @author Алексей
 *
 */
public interface IUserToServerCommandExecuter {
	
	public Vehicle getById(long id);
	
	public Vehicle getMin(Criteria criteria);
	
	public Vehicle getMax(Criteria criteria);
	
	public ArrayList<Vehicle> getAll();
	
	/**
	 * Метод добавляет в коллекцию указанную машину. Возвращает добавленный объект.
	 * Возвращение добавленной машины делается для того,
	 * чтобы можно было узнать автоматически формируемые поля, например дату и айди
	 * @param vehicle - машина, которую надо добавить
	 * @return добавленную машину
	 */
	public Vehicle add(Vehicle vehicle);
	
	public void updateById(long id, Vehicle vehicle);
	
	public void removeById(long id);
	
	public void removeAll();
	
	/**
	 * Удалить из хранилища все элементы, превышающие данный по заданному критерию
	 * @param vehicle - машина, с которой происходит сравнение
	 * @param criteria - критерий, по которому происходит сравнение
	 */
	public void removeGreater(Vehicle vehicle, Criteria criteria);
	
	/**
	 * Удалить из хранилища все элементы, меньшие данного по заданному критерию
	 * @param vehicle - машина, с которой происходит сравнение
	 * @param criteria - критерий, по которому происходит сравнение
	 */
	public void removeLower(Vehicle vehicle, Criteria criteria);
	
	public void sort(Criteria criteria);
	
	public int getCollectionSize();
	
	/**
	 * Узнать количество автомобилей, которые меньше заданного по заданному критерию
	 * @param vehicle - данный автомобиль
	 * @param criteria - данный критерий для сравнения
	 * @return число  - количество автомобилей, которые меньше заданного по заданному критерию
	 */
	public int getAmountLower(Vehicle vehicle, Criteria criteria);
}
