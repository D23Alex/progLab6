import java.io.IOException;
import java.net.DatagramPacket;

import org.apache.commons.lang3.SerializationUtils;

public class LogicServerResponser extends Responser {
	
	private ILogicServerReceiver receiverModule;
	

	@Override
	public void sendResponse() throws IOException {
		byte[] arr = SerializationUtils.serialize(this.getCurrentResponse());
		DatagramPacket packetToSend = new DatagramPacket(arr, arr.length, this.receiverModule.getCurrentClientAddress(), this.receiverModule.getCurrentClientPort());
		this.getCoreModule().getDatagramSocket().send(packetToSend);
	}


	public ILogicServerReceiver getReceiverModule() {
		return receiverModule;
	}


	public void setReceiverModule(ILogicServerReceiver receiverModule) {
		this.receiverModule = receiverModule;
	}

}
