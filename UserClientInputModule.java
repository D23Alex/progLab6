import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class UserClientInputModule implements IUserClientInputModule {
	private IUserClientCore coreModule;

	@Override
	public IScript getScript(String fileName) throws FileNotFoundException, IOException {
		return new Script(this.readByLine(fileName), new File(fileName));
	}

	@Override
	public ArrayList<String> getCommandAndArgs() throws InputDeniedByUserException {
		ArrayList<String> toReturn = new ArrayList<>();
		
		String[] commandAndArgs = (new ADialogPhaseString(1, 200, "Enter a command", this).getAnswer().split(" "));
		
		if (commandAndArgs[0].toLowerCase().equals("add") || commandAndArgs[0].toLowerCase().equals("update") 
				||commandAndArgs[0].toLowerCase().equals("remove_greater") || commandAndArgs[0].toLowerCase().equals("remove_lower")) {
			
			String[] vehicleArgs = this.askVehicleArgs();
			toReturn.add(commandAndArgs[0]);
			if (commandAndArgs[0].toLowerCase().equals("update") || commandAndArgs.length > 1) {
				toReturn.add(commandAndArgs[1]);
			}
			for (int i = 0; i< vehicleArgs.length; i++) {
				toReturn.add(vehicleArgs[i]);
			}
			
			return toReturn;
		}
		
		for (int i = 0; i< commandAndArgs.length; i++) {
			toReturn.add(commandAndArgs[i]);
		}

		return toReturn;
	}

	/**
	 * Легаси код из 5 (почти)
	 * @return массив строковых парметров машин
	 * @throws InputDeniedByUserException, если пользовтель ввёл BACK
	 */
	private String[] askVehicleArgs() throws InputDeniedByUserException {
		String[] vehicleArgs = new String[6];
		
		// получаем тип
		vehicleArgs[0] = new ADialogPhaseStringOneOfGiven(VehicleType.toStringSet(), "Please choose the type of your new vehicle", this, "BACK").getAnswer();
		if (vehicleArgs[0].toUpperCase().equals("BACK")) {
			throw new InputDeniedByUserException();
		}
		
		// имя
		vehicleArgs[1] = new ADialogPhaseString(3, 20, "Choose a name", this, "BACK").getAnswer();
		if (vehicleArgs[1].toUpperCase().equals("BACK")) {
			throw new InputDeniedByUserException();
		}
				
		// получаем координаты
		vehicleArgs[2] = new ADialogPhaseInt(-100000000, 820, "Enter the x coordinate", this, "BACK").getAnswer();
		if (vehicleArgs[2].toUpperCase().equals("BACK")) {
			throw new InputDeniedByUserException();
		}
		
		vehicleArgs[3] = new ADialogPhaseFloat(-538, 100000000, 10, "Enter the y coordinate", this, "BACK").getAnswer();
		if (vehicleArgs[3].toUpperCase().equals("BACK")) {
			throw new InputDeniedByUserException();
		}
		
		// получаем количество бензина
		vehicleArgs[4] = new ADialogPhaseInt(1, 100000000, "Enter the engine_power", this, "BACK").getAnswer();
		if (vehicleArgs[4].toUpperCase().equals("BACK")) {
			throw new InputDeniedByUserException();
		}
				
		// получаем тип бензина
		vehicleArgs[5] = new ADialogPhaseStringOneOfGiven(FuelType.toStringSet(), "Choose the fuel type", this, "BACK").getAnswer();
		if (vehicleArgs[5].toUpperCase().equals("BACK")) {
			throw new InputDeniedByUserException();
		}
				
		
		return vehicleArgs;
	}

	@Override
	public String getTextFromFile(String fileName) throws FileNotFoundException, IOException {
		return String.join("", this.readByLine(fileName));
	}
	
	/**
	 * ЛЕГАСИ
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ArrayList<String> readByLine(String fileName) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		ArrayList<String> lines = new ArrayList<String>();
		String line = bufferedReader.readLine();
		while (line != null){
			lines.add(line);
		    line = bufferedReader.readLine();
		}
		bufferedReader.close();
		return lines;
	}

	@Override
	public String getStructureDescription() {
		return this.coreModule.getStructureDescription() + " > User input module";
	}

	public IUserClientCore getCoreModule() {
		return coreModule;
	}

	public void setCoreModule(IUserClientCore coreModule) {
		this.coreModule = coreModule;
	}

}
