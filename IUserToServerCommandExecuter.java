import java.util.ArrayList;

/**
 * ������ �������������
 * @author �������
 *
 */
public interface IUserToServerCommandExecuter {
	
	public Vehicle getById(long id);
	
	public Vehicle getMin(Criteria criteria);
	
	public Vehicle getMax(Criteria criteria);
	
	public ArrayList<Vehicle> getAll();
	
	/**
	 * ����� ��������� � ��������� ��������� ������. ���������� ����������� ������.
	 * ����������� ����������� ������ �������� ��� ����,
	 * ����� ����� ���� ������ ������������� ����������� ����, �������� ���� � ����
	 * @param vehicle - ������, ������� ���� ��������
	 * @return ����������� ������
	 */
	public Vehicle add(Vehicle vehicle);
	
	public void updateById(long id, Vehicle vehicle);
	
	public void removeById(long id);
	
	public void removeAll();
	
	/**
	 * ������� �� ��������� ��� ��������, ����������� ������ �� ��������� ��������
	 * @param vehicle - ������, � ������� ���������� ���������
	 * @param criteria - ��������, �� �������� ���������� ���������
	 */
	public void removeGreater(Vehicle vehicle, Criteria criteria);
	
	/**
	 * ������� �� ��������� ��� ��������, ������� ������� �� ��������� ��������
	 * @param vehicle - ������, � ������� ���������� ���������
	 * @param criteria - ��������, �� �������� ���������� ���������
	 */
	public void removeLower(Vehicle vehicle, Criteria criteria);
	
	public void sort(Criteria criteria);
	
	public int getCollectionSize();
	
	/**
	 * ������ ���������� �����������, ������� ������ ��������� �� ��������� ��������
	 * @param vehicle - ������ ����������
	 * @param criteria - ������ �������� ��� ���������
	 * @return �����  - ���������� �����������, ������� ������ ��������� �� ��������� ��������
	 */
	public int getAmountLower(Vehicle vehicle, Criteria criteria);
}
