import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.lang3.SerializationUtils;

public class UserClientRequestSender implements IUserClientRequestSender {
	private IUserClientCore coreModule;

	@Override
	public void sendRequest(IRequest request) throws IOException {
		byte[] serializedRequest = SerializationUtils.serialize(request);
		ByteBuffer buffer = ByteBuffer.wrap(serializedRequest);
		
		this.coreModule.getDatagramChannel().send(buffer, this.coreModule.getSocketAddress());
	}

	public IUserClientCore getCoreModule() {
		return coreModule;
	}

	public void setCoreModule(IUserClientCore coreModule) {
		this.coreModule = coreModule;
	}

	@Override
	public String getStructureDescription() {
		return this.coreModule.getStructureDescription() + " > requestSender module";
	}

}
