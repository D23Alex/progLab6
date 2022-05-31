import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class UserClientInputModuleMock implements IUserClientInputModule {

	@Override
	public String getStructureDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IScript getScript(String fileName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getCommandAndArgs() throws InputDeniedByUserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTextFromFile(String fileName) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
