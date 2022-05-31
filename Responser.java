import java.util.ArrayList;

public abstract class Responser implements IServerResponser, IRequestResponser {

	private IServerMain coreModule;
	
	private IResponse currentResponse;
	
	@Override
	public void makeRequestUnsuccessful() {
		this.currentResponse.setRequestSuccessful(false);
	}


	@Override
	public void resetResponse() {
		this.currentResponse = new Response();
		
	}
	
	@Override
	public void setVehicle(Vehicle vehicle) {
		this.currentResponse.setVehicle(vehicle);
	}

	@Override
	public void add(Vehicle vehicle) {
		this.currentResponse.addVehicleToList(vehicle);
		
	}

	@Override
	public void addAll(ArrayList<Vehicle> vehicles) {
		if (vehicles == null) {
			return;
		}
		for (Vehicle vehicle : vehicles) {
			this.currentResponse.addVehicleToList(vehicle);
		}
		
	}

	@Override
	public void addProblem(String problem) {
		this.currentResponse.addProblem(problem);
	}

	@Override
	public void setId(long id) {
		this.currentResponse.setId(id);
	}

	@Override
	public void setAmount(int amount) {
		this.currentResponse.setAmount(amount);
	}

	public IResponse getCurrentResponse() {
		return currentResponse;
	}

	public void setCurrentResponse(IResponse currentResponse) {
		this.currentResponse = currentResponse;
	}

	public IServerMain getCoreModule() {
		return coreModule;
	}

	public void setCoreModule(IServerMain coreModule) {
		this.coreModule = coreModule;
	}

}
