
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**
 * ЛЕГАСИ-макарон ИЗ 5 лабы
 * @author Алексей
 * @version 0.1
 *
 */
public abstract class ADialogPhase {
	private String message;
	// это поле отвечает за то, будет ли показана информация о корректном вводе при приглашении к вводу.
	private boolean giveInputInfo;
	
	// храним ссылку на родителя - это app, session или action
	private IStructure parent;
	private Set<String> reservedWords;
	
	ADialogPhase(String message, IStructure parent, String ... reservedWords) {
		this.parent = parent;
		this.message = message;
		this.reservedWords = new HashSet<String>();
		// добавляем служебные слова - они будут большими буквами.
		for (String currentWord : reservedWords) {
			this.reservedWords.add(currentWord.toUpperCase());
		}
		this.giveInputInfo = false;
	}
	
	public boolean getGiveInputInfo() {
		return this.giveInputInfo;
	}
	
	public void setGiveInputInfo(boolean tf) {
		this.giveInputInfo = tf;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Gets the answer from a user
	 * @return the user answer
	 */
	@SuppressWarnings("resource")
	public String getAnswer() {
		String answer;
		// TODO: надо закрывать сканер же?
		Scanner scanner = new Scanner(System.in);
		while (true) {
			// если не стоит флаг "покажи информацию о вводе", то по умолчанию в первый раз спрашиваем без объяснений, потом меняем флаг и начинаем спрашивать с объяснениями
			// поведение по умолчанию можно изменить. И мы изменим его например, когда sспрашиваем Енам, чтобы сразу с подсказкой
			if (!this.getGiveInputInfo()) {
				System.out.print(this.parent.getStructureDescription() + " | " + this.getMessage() + ": ");
				this.setGiveInputInfo(true);
			}
			else
				System.out.print(this.parent.getStructureDescription() + " | " + this.getMessageWithInputInfo() + ": ");
			try {
				answer = scanner.nextLine();
				while (answer.length() < 1) {
					answer = scanner.nextLine();
				}
			} catch (NoSuchElementException e) {
				scanner.close();
				return null;
			}
			
			/*
			// спрашиваем, пока не получим правильный ответ или BACK TODO: спросить
			while (true) {
				try {
					answer = scanner.nextLine();
					if (answer.length() < 1) {
						continue askingCycle;
					}
					break;
				} catch (NoSuchElementException e) {
					scanner.close();
					return null;
				}
			}
			*/
			// ввели служебное слово - выходим. Слова - большие буквы и это делаем большими буквами, чтобы регистр не мешал
			if (this.reservedWords.contains(answer.toUpperCase())) {
				return answer.toUpperCase();	
			}
			// ввели не служебное слово - проверить!
			if (isValid(answer)) {
				return answer;
			}
			else
				System.out.println(this.parent.getStructureDescription() + " > Input error | " + this.getFailMessage(answer));
		}
	}
	
	// проверяем введённую строку
	public abstract boolean isValid(String answer);
	
	// возвращает message + информацию о вводе (например, какие значения допустимы)
	public abstract String getMessageWithInputInfo();
	
	// мы даём этому методу введённую неправильную строку, а он должен объяснить, что не так. Само сообщение без описания структуры
	public abstract String getFailMessage(String answer);
}
