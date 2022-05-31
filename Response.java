import java.util.ArrayList;
import java.util.HashSet;

/**
 * Респонсы могут нести в себе: 1 машину, аррейлист машин, айди машины, количество.
 * Всегда несут boolean статус запроса - успешно или нет
 * @author Алексей
 *
 */
public class Response implements IResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3504368693308052973L;

	/**
	 * Булеановое значенsие того, был ли успешен запрос,
	 * ответом на который является данный респонс
	 */
	private boolean requestSuccessful;
	
	/**
	 * Строковое описание, что и почему не было добавлено
	 */
	private HashSet<String> problems;
	
	private Vehicle vehicle;
	
	private ArrayList<Vehicle> vehicles;
	
	private int amount;
	
	private long id;
	
	public Response() {
		this.requestSuccessful = true;
		this.problems = new HashSet<String>();
		//TODO: убрать нал
		this.vehicle = null;
		this.vehicles = new ArrayList<Vehicle>();
		this.amount = -1;
		this.id = -1;
	}
	
	
	
	public Response(boolean requestSuccessful, HashSet<String> problems, Vehicle vehicle, ArrayList<Vehicle> vehicles,
			int amount, long id) {
		this.requestSuccessful = requestSuccessful;
		this.problems = problems;
		this.vehicle = vehicle;
		this.vehicles = vehicles;
		this.amount = amount;
		this.id = id;
	}
	
	@Override
	public void addVehicleToList(Vehicle vehicle) {
		this.vehicles.add(vehicle);
	}

	@Override
	public void addProblem(String problem) {
		this.problems.add(problem);
	}

	public boolean isRequestSuccessful() {
		return this.requestSuccessful;
	}

	public void setRequestSuccessful(boolean requestSuccessful) {
		this.requestSuccessful = requestSuccessful;
	}

	public HashSet<String> getProblems() {
		return problems;
	}

	public void setProblems(HashSet<String> problems) {
		this.problems = problems;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
	

}
