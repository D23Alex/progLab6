import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SerializationUtils;

public class UserClientReceiver implements IUserClientReceiver {
	
	private IUserClientCore coreModule;

	@Override
	public IResponse receiveResponse() throws IOException, NoServerResponseException {
		byte[] arr = new byte[100000];
		ByteBuffer buffer = ByteBuffer.wrap(arr);
		
		IResponse toReturn = null;
		//TODO: тут можно сделать счётчик, и если он достиг n, то считаем, 
		// что нам слишком долго отвечают и, например, выбрасываем ексепшн
		
		int stepCounter = 0;
		int stepLimit = 10;
		
		while (true) {
			if (stepCounter >= stepLimit) {
				throw new NoServerResponseException();
			}
			try {
				this.coreModule.getDatagramChannel().receive(buffer);
				toReturn = (IResponse) SerializationUtils.deserialize(buffer.array());
				break;
			} catch (Exception e) {
				stepCounter++;
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e1) {
					continue;
				}
				continue;
			}
		}
		
		return toReturn;
	}

	@Override
	public String getResponseStringDescription(String requestDescription, IResponse response) {
		String ans = this.getStructureDescription() + " > " + requestDescription + " | Request ";
		if (response.isRequestSuccessful()) {
			ans += "successful:";
			if (!response.getVehicles().isEmpty()) {
				ans += "\nThe vehicles:";
				for (Vehicle vehicle : response.getVehicles()) {
					ans += "\n\n" + vehicle;
				}
				ans += "\n";
			}
			
			if (response.getVehicle() != null) {
				ans += "\nThe vehicle:\n\n" + response.getVehicle() + "\n";
			}
			
			if (response.getAmount() >= 0) {
				ans += "\nThe amount of vehicles: " + response.getAmount();
			}
			
			if (response.getId() >= 0) {
				ans += "\nID of the vehicle: " + response.getId();
			}
		}
		else {
			ans += "failed\n";
			if (!response.getProblems().isEmpty()) {
				ans += "Problems occured:";
				for (String problem : response.getProblems()) {
					ans += "\n" + problem;
				}
			}
		}
		
		
		
		return ans;
	}

	public IUserClientCore getCoreModule() {
		return coreModule;
	}

	public void setCoreModule(IUserClientCore coreModule) {
		this.coreModule = coreModule;
	}

	@Override
	public String getStructureDescription() {
		return this.coreModule.getStructureDescription() + " > Server answer receiver module";
	}

}
