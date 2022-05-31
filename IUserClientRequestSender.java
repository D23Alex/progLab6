import java.io.IOException;

public interface IUserClientRequestSender extends IStructure {

	public void sendRequest(IRequest request) throws IOException;

}
