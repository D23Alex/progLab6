
/**
 * ЛЕГАСИ ИЗ 5 лабы
 * @author Алексей
 * @version 0.1
 *
 */
import java.util.Set;

// получаем на вход ещё и множество допустимых ответов

public class ADialogPhaseStringOneOfGiven extends ADialogPhase {
	
	private Set<String> allowedAnswers;

	ADialogPhaseStringOneOfGiven(Set<String> allowedAnswers, String message, IStructure parent, String ... reservedWords) {
		super(message, parent, reservedWords);
		// переведём все варианты в верхний регистр
		for (String currentString : allowedAnswers) {
			currentString.toUpperCase();
		}
		this.allowedAnswers = allowedAnswers;
		// говорим "показывай подсказки при вводе СРАЗУ"
		this.setGiveInputInfo(true);
	}

	@Override
	public boolean isValid(String answer) {
		// верной считается строка, которая содержится в множестве допустимых вариантов
		return (this.allowedAnswers.contains(answer.toUpperCase()));
	}

	@Override
	public String getMessageWithInputInfo() {
		int counter = 0;
		String enteringMessage = this.getMessage() + " (";
		for (String currentWord : this.allowedAnswers) {
			enteringMessage += currentWord;
			counter ++;
			if (counter < allowedAnswers.size()) {
				enteringMessage += " / ";
			}
		}
		return enteringMessage + ")";
	}

	@Override
	public String getFailMessage(String answer) {
		int counter = 0;
		String failMessage = "You must enter one of the following words: (";
		for (String currentWord : this.allowedAnswers) {
			failMessage += currentWord;
			counter ++;
			if (counter < allowedAnswers.size()) {
				failMessage += " / ";
			}
		}
		failMessage += ") - you entered '" + answer + "'";
		
		return failMessage;
	}

}
