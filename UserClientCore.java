import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Этот класс необзходимо переименовать.
 * В этом классе содержится основной цикл работы клиента.
 * В этом классе реализована работа пользовательсих скриптов.
 * @author Алексей
 *
 */
public class UserClientCore implements IUserClientCore {
	
	// тестируем
	//private Selector selector;
	
	// интересное замечание про ввод аргументов скриптами.
	// можно ввести все аргументы машины в команде add в 1 строчке,
	// или разделить их на 1-несколько строчек, часть оставляя на той строчке...
	// а нет, нельзя, тогда скрипт возьмёт следующие команды как аргументы в тачку
	
	private String appName = "Vehicle App";
	
	private String currentCollectionName;
	
	private IUserClientReceiver receiverModule;
	private ILogicRequestFactory logicRequestFactory;
	private IUserClientRequestSender requestSenderModule;
	private IUserClientInputModule userInputModule;
	
	private Stack<IScript> scripts;
	
	private static final Set<String> serverCommands = createServerCommandsMap();
	private static Set<String> createServerCommandsMap() {
		Set<String> result = new HashSet<>();
		result.add("add");
		result.add("info");
		result.add("update");
		result.add("sort");
		result.add("show");
		result.add("remove_by_id");
		result.add("clear");
		result.add("remove_greater");
		result.add("remove_lower");
		result.add("max");
		result.add("group_counting_by_engine_power");
		result.add("count_greater_than_engine_power");
		return Collections.unmodifiableSet(result);
	}
	
	private static final Map<String, String> commandDescriptions = createCommandDescriptionsMap();
    private static Map<String, String> createCommandDescriptionsMap() {
        Map<String, String> result = new HashMap<>();
        result.put("add", "Adding a new Vehicle to your Collection");
        result.put("info", "Getting the main information about your Collection");
        result.put("update" , "Updating the vehicle with the given id");
        result.put("sort" , "Sorting the Vehicles by the given key");
        result.put("show" , "Displaying the collection");
        result.put("remove_by_id" , "Removeing the vehicle with the given id");
        result.put("clear" , "Removing all of the Vehicles from your collection");
        result.put("remove_greater" , "Removing vehicles greater than given");
        result.put("remove_lower" , "Removing vehicles lower than given");
        result.put("max" , "Getting the greatest Element by the given key");
        result.put("group_counting_by_engine_power", "Grouping counting by engine power");
		result.put("count_greater_than_engine_power" , "Counting the amout of Vehicles with greater "
				+ "engine_power that given");
        result.put("use", "Using a Vehicle Collection by the given name as a current collection");
        result.put("execute_script", "Executing the script in the given file");
        result.put("exit", "Quitting the Vehicle App");
		return Collections.unmodifiableMap(result);
    }
    
	private SocketAddress socketAddress;
	
	private DatagramChannel datagramChannel;
	
	
	public UserClientCore(InetAddress serverAddress, int serverPort) throws IOException {
		this.socketAddress = new InetSocketAddress(serverAddress, serverPort);
		this.datagramChannel = DatagramChannel.open();
		this.scripts = new Stack<>();
		/**
		 * Пусть под именем default хранится какая-нибудь общая мусорка,
		 * хотя даже её использовать лучше не надо, не разрешать вводить команды,
		 * пока не получим имя рабочей коллекции
		 */
		this.currentCollectionName = "default";
		
		this.datagramChannel.configureBlocking(false);
	}
	
	/**
	 * Основной цикл
	 */
	public void run() {
		ArrayList<String> commandAndArguments;
		IScript newScript = null;
		while (true) {
				try {
					commandAndArguments = this.getCommandAndArguments();
				} catch (InputDeniedByUserException e1) {
					System.out.println(this.getStructureDescription() + " > User command input | "
							+ "Input calcelled by user");

					continue;
				} catch (Exception e2) {
					System.out.println(this.getStructureDescription() + " > Command input | Input calcelled - " + e2.getMessage());

					continue;
				}
			if (commandAndArguments.get(0).equals("exit")) {
				//TODO: выполняем выход
				System.out.println(this.getStructureDescription() + " | Bye, Have a good one!");
				return;
			}
			else if (commandAndArguments.get(0).equals("help")) {
				// КОМАНДА ВЫПОЛНЯЕТСЯ НА КЛИЕНТЕ
				System.out.println(this.getStructureDescription() + " > Help | Here are some of the commands for you to take advantage of:");
				for (String key : commandDescriptions.keySet()) {
			        System.out.println("| Command '" + key + "' >>> " + commandDescriptions.get(key));
			    }
			}
			else if (commandAndArguments.get(0).equals("use")) {
				if (commandAndArguments.size() < 2) {
					System.out.println(this.getStructureDescription() + " > Command input > Using a collection | "
							+ "You must specify the name of the collection that you want to use - e.g. "
							+ "'use collectionName'");
					continue;
				}
				if (!this.isValidCollectionName(commandAndArguments.get(1))) {
					System.out.println(this.getStructureDescription() + " > Command input > Using a collection | "
							+ "The name of the collection you gave is invalid. It may only contain letters A-Z & a-z");
					continue;
				}
				this.currentCollectionName = commandAndArguments.get(1);
				System.out.println(this.getStructureDescription() + " > Command input > Using a collection | "
						+ "You are now using the collection '" + commandAndArguments.get(1) + "'");
			}
			else if (commandAndArguments.get(0).equals("load")) {
				// ОТ ЭТОГО ОТКАЗАЛИСЬ, РЕАЛИЗОВЫВАЕМ НОВУЮ СИСТЕМУ ЗАГРУЗКИ И СМЕНЫ ФАЙЛОВ КОЛЛЕКЦИЙ - НА СЕРВЕРЕ
				// КОМАНДА ВЫПОЛНЯЕТСЯ Частично НА КЛИЕНТЕ, а результат выполнения её на клиенте посылается серверу
				if (commandAndArguments.size() > 1) {
					try {
						String collectionAsXml = this.userInputModule.getTextFromFile(commandAndArguments.get(1));
						commandAndArguments.clear();
						commandAndArguments.add("load");
						commandAndArguments.add(collectionAsXml);
						executeServerCommand(commandAndArguments);
					} catch (FileNotFoundException e) {
						System.out.println("Указанного файла не существует");
						continue;
					} catch (IOException e) {
						System.out.println("Чтение из указанного файла невозможно, проверьте его целостность, "
								+ "доступность на чтение и так дальше");
						continue;
					}
				} else {
					System.out.println("Укажите файл откуда загружать");
				}
				
			}
			else if (commandAndArguments.get(0).equals("execute_script")) {
				// КОМАНДА ВЫПОЛНЯЕТСЯ НА КЛИЕНТЕ
				if (commandAndArguments.size() > 1) {
					try {
						newScript = this.userInputModule.getScript(commandAndArguments.get(1));
					} catch (IOException e) {
						System.out.println(this.getStructureDescription() + " > File access error | This file is not available for reading");
						continue;
					}
					
					// проитерируем все скрипты, если найдём один и тот же файл, то попытка сделать LOOP, запрещаем
					boolean hasLoops = false;
					for (IScript currentScript : this.scripts) {
						if (currentScript.getFile().getAbsolutePath().equals(newScript.getFile().getAbsolutePath())) {
							hasLoops = true;
							break;
						}
					}
					
					if (hasLoops) {
						System.out.println(this.getStructureDescription() + " > Executing a script > Script Error |"
								+ " Your script contains loops, so it could not be fully executed");
						this.scripts.clear();
						continue;
					}
					
					this.scripts.push(newScript);
				}
				else {
					System.out.println(this.getStructureDescription() + " > Executing a script | You must specify the "
							+ "name of the script that you want to execute, e.g. 'execute_script ./filename.txt'");
				}
				
			}
			else {
				// КОМАНДА ЯВЛЯЕТСЯ ЗАПРОСОМ НА СЕРВЕРЕ
				executeServerCommand(commandAndArguments);
			}
		}
	}

	/**
	 * Метод спрашивает у пользователя набор комманда + аргументы,
	 * либо берёт всё необходимое из скрипта
	 * @return Список, 0 элементом которого является команды,
	 * а последующие элементы - аргументы(опционалньно)
	 * @throws InputDeniedByUserException 
	 */
	private ArrayList<String> getCommandAndArguments() throws InputDeniedByUserException, Exception {
		ArrayList<String> toReturn = new ArrayList<>();
		if (!this.scripts.isEmpty()) {
			IScript currentScript = this.scripts.peek();
			// Получаем следующую строку скрипта, это команда, возможно, с аргументами через пробел
			String currentLine = currentScript.getNextLine();
			if (currentLine.length() == 0) {
				System.out.println(this.getStructureDescription() + " > Executing the script | Execution failed because your script contains blank lines");
			}
			
			String[] currentLineWithArgs = currentLine.split(" ");
			for (int i = 0; i < currentLineWithArgs.length; i++) {
				toReturn.add(currentLineWithArgs[i]);
			}
			
			if (currentLineWithArgs[0].equals("add") || currentLineWithArgs[0].equals("update") ||
					currentLineWithArgs[0].equals("remove_greater") || currentLineWithArgs[0].equals("remove_lower")) {
				// необходимо считать из скрипта аргументы - про машину: сначала имя, потом
				String[] vehicleArgs;
				try {
					vehicleArgs = this.readVehicleFromScript();
				} catch (Exception e) {
					// Аргументы в скрипте не валидные - сообщит)ь пользователю, прервать скрипт.
					System.out.println(this.getStructureDescription() + " > Executing the script | Failed because of invalid arguments - " + e.getMessage());
					System.out.println(this.getStructureDescription() + " > Executing the script | Execution stopped with en error ");
					this.scripts.clear();
					throw e;
				}
				for (int i = 0; i < vehicleArgs.length; i++) {
					toReturn.add(vehicleArgs[i]);
				}
				
				
			}
			
			if (currentScript.isFinished()) {
				this.scripts.pop();
			}
			
			return toReturn;
		}
		
		// Если мы здесь, то пользовательский ввод
		return this.userInputModule.getCommandAndArgs();
	}
	
	/**
	 * Метод считывает аргументы машины из скрипта и выбрасывает ошибку, если что не так
	 * Для валидации используются методы isValid подклассов ADialogPhase
	 */
	private String[] readVehicleFromScript() throws Exception {
		String[] arguments = new String[6];
		IScript currentScript = this.scripts.peek();
		String currentArg;
		for (int i = 0; i < 6; i++) {
			if (currentScript.isFinished()) {
				throw new Exception("not enough arguments to create a new Vehicle (6 required)");
			}
			currentArg = currentScript.getNextLine();
			arguments[i] = currentArg;
		}
		
		// аргументы получены и их достаточно, теперь валидация
		
		ArrayList<ADialogPhase> validationPhases = new ArrayList<>();
		validationPhases.add(new ADialogPhaseStringOneOfGiven(VehicleType.toStringSet(), "Please choose the type of your new vehicle", this, "BACK"));
		validationPhases.add(new ADialogPhaseString(3, 20, "Choose a name", this, "BACK"));
		validationPhases.add(new ADialogPhaseInt(-100000000, 820, "Enter the x coordinate", this, "BACK"));
		validationPhases.add(new ADialogPhaseFloat(-538, 100000000, 10, "Enter the y coordinate", this, "BACK"));
		validationPhases.add(new ADialogPhaseInt(1, 100000000, "Enter the engine_power", this, "BACK"));
		validationPhases.add(new ADialogPhaseStringOneOfGiven(FuelType.toStringSet(), "Choose the fuel type", this, "BACK"));
		
		for (int i = 0; i < 6; i++) {
			if (!validationPhases.get(i).isValid(arguments[i])) {
				throw new Exception(validationPhases.get(i).getFailMessage(arguments[i]) + ", your script contains '" + arguments[i] + "'");
			}
		}
		
		// если мы тут, то все аргументы прошли валидацию можно отправлять их
		
		return arguments;
	}
	
	/**
	 * Метод вызывается над командой и её аргументами в случае,
	 * если выполняемая в данный момент команда является серверной.
	 * В нём весь цикл взаимодействия с сервером - формирование запроса,
	 * отправление запроса, получение ответа, обработка ответа
	 */
	private void executeServerCommand(ArrayList<String> commandAndArguments) {
		// КОМАНДА ЯВЛЯЕТСЯ ЗАПРОСОМ НА СЕРВЕРЕ
		String responseAsString;
		// бегите, глупцы
		IRequest request;
		IResponse response;
		if (UserClientCore.serverCommands.contains(commandAndArguments.get(0))) {
			try {
				// Создаём запрос по уже проведённому пользовательскому вводу, или из скрипта
				request = this.logicRequestFactory.createRequest(commandAndArguments);
			} catch (IndexOutOfBoundsException e) {
				System.out.println(this.getStructureDescription() + " > Command input | Your command could not be "
						+ "executed due the lack of arguments - use more arguments or something idk. Use 'help' for help");
				// если мы в скрипте, то сбросить скрипт
				this.scripts.clear();
				return;
			}	
			catch (IllegalArgumentException e) {
				// TODO: Аргументы некорректны
				System.out.println(this.getStructureDescription() + " > Command input | Your command could not be "
						+ "executed due to invalid arguments. Use 'help' for help");
				
				// если мы в скрипте, то сбросить скрипт
				this.scripts.clear();
				return;
			} catch (Exception e) {
				// Аргументы некорректны
				System.out.println(this.getStructureDescription() + " > Command input | Your command could not be "
						+ "executed due to invalid arguments. Use 'help' for help");
				
				// если мы в скрипте, то сбросить скрипт
				this.scripts.clear();
				return;
			}
			try {
				this.requestSenderModule.sendRequest(request);
			} catch (IOException e) {
				System.out.println(this.getStructureDescription() + " > Sending the request | Your request is invalid, please try again with valid arguments");				
				return;
			}
			
			try {
				response = this.receiverModule.receiveResponse();
			} catch (IOException e) {
				//TODO: не дошло
				
				System.out.println(this.getStructureDescription() + "Sorry, your request could not be executed, "
						+ "you have connection issues. Check your Wi-Fi or sometihng");
				return;
			} catch (NoServerResponseException e) {
				// Слишком долго нет ответа от сервера
				// Если 1 раз не дождались, то предполагаем, что может быть произошла колизия, пробуем ещё 1 раз,
				// если снова не получилось, значит действительно проблема
				try {
					System.out.println(this.getStructureDescription() + " > Time out | Retrying.");
					this.requestSenderModule.sendRequest(request);
					response = this.receiverModule.receiveResponse();
				} catch (Exception e2) {
					System.out.println(this.getStructureDescription() + " > Time out | Sorry, our server is down at the moment."
							+ " We are trying our best right now to fix it. Try again later.");
					return;
				}
				
			}
			responseAsString = receiverModule.getResponseStringDescription(commandDescriptions.get(commandAndArguments.get(0)),  response);
			
			// Презентовать строку-ответ пользователю
			System.out.println(responseAsString);
		}
		else {
			// Такой команды не существует
			int shortest = 999999999;
			String probableCommand = "add";
			int lev;
			for (String currentCommand: serverCommands) {
				lev = levenstain(currentCommand, commandAndArguments.get(0));
				if (lev < shortest) {
					probableCommand = currentCommand;
					shortest = lev;
				}
			}
			
			System.out.println("There's no such command as '" + commandAndArguments.get(0) +
					"'. Did you mean '" + probableCommand + "'?");
			
			return;
		}
	}
	

	// алгоритм расстояния Левенштейна
	private static int levenstain(String str1, String str2) {
        // Массивы должны быть одинаковой длины, т.к. отражают две строки (или столбца) одной и
		// той же таблицы (см. алгоритм расстояния Левенштейна)
        int[] Di_1 = new int[str2.length() + 1];
        int[] Di = new int[str2.length() + 1];

        for (int j = 0; j <= str2.length(); j++) {
            Di[j] = j; // (i == 0)
        }

        for (int i = 1; i <= str1.length(); i++) {
            System.arraycopy(Di, 0, Di_1, 0, Di_1.length);

            Di[0] = i; // (j == 0)
            for (int j = 1; j <= str2.length(); j++) {
                int cost = (str1.charAt(i - 1) != str2.charAt(j - 1)) ? 1 : 0;
                Di[j] = min(
                        Di_1[j] + 1,
                        Di[j - 1] + 1,
                        Di_1[j - 1] + cost
                );
            }
        }

        return Di[Di.length - 1];
    }
	
	private boolean isValidCollectionName(String name) {
		return name.matches("^[a-zA-Z]+$");
	}

    private static int min(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }


	public IUserClientReceiver getReceiverModule() {
		return receiverModule;
	}

	public void setReceiverModule(IUserClientReceiver receiverModule) {
		this.receiverModule = receiverModule;
	}

	public ILogicRequestFactory getLogicRequestFactory() {
		return logicRequestFactory;
	}

	public void setLogicRequestFactory(ILogicRequestFactory logicRequestFactory) {
		this.logicRequestFactory = logicRequestFactory;
	}
	
	public IUserClientInputModule getUserInputModule() {
		return userInputModule;
	}

	public void setUserInputModule(IUserClientInputModule userInputModule) {
		this.userInputModule = userInputModule;
	}

	public Stack<IScript> getScripts() {
		return scripts;
	}

	public void setScripts(Stack<IScript> scripts) {
		this.scripts = scripts;
	}

	public SocketAddress getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	public DatagramChannel getDatagramChannel() {
		return datagramChannel;
	}

	public void setDatagramChannel(DatagramChannel datagramChannel) {
		this.datagramChannel = datagramChannel;
	}


	public IUserClientRequestSender getRequestSenderModule() {
		return requestSenderModule;
	}


	public void setRequestSenderModule(IUserClientRequestSender requestSenderModule) {
		this.requestSenderModule = requestSenderModule;
	}

	@Override
	public String getStructureDescription() {
		return this.appName + " > Client";
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getCurrentCollectionName() {
		return currentCollectionName;
	}

	public void setCurrentCollectionName(String currentCollectionName) {
		this.currentCollectionName = currentCollectionName;
	}

	//public Selector getSelector() {
		//return this.selector;
	//}

	//public void setSelector(Selector selector) {
	//	this.selector = selector;
//	}
	
}
