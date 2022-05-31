import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface IUserClientInputModule extends IStructure {

	/**
	 * Метод по заданному имени файла читает этот файл построчно
	 * и возвращает объект-скрипт на основе этого
	 * @param fileName Имя файла
	 * @return объект-скрипт
	 * @throws IOException, содержаищй в месседже описание проблемы,
	 * если не удалось прочитать файл или он какой-то неправильный
	 */
	public IScript getScript(String fileName) throws IOException;

	/**
	 * Метод получает команду и все необходимые аргументы у пользователя.
	 * Не отстанет, пока не будут введены все аргументы или BACK
	 * @return аррей-лист, 0 Элемент - это команда, дальше по-очереди идут аргументы
	 * @throws InputDeniedByUserException, если пользователь пожелал выполнить
	 * отмену ввода и прервал его командой "BACK"
	 */
	public ArrayList<String> getCommandAndArgs() throws InputDeniedByUserException;

	/**
	 * Метод добывает текст из файла
	 * @param fileName - имя файла
	 * @return строку - содержание файла
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String getTextFromFile(String fileName) throws FileNotFoundException, IOException;

}
