import java.io.IOException;

public interface IUserClientReceiver extends IStructure {

	public IResponse receiveResponse() throws IOException, NoServerResponseException;

	public String getResponseStringDescription(String requestDescription, IResponse response);

}
