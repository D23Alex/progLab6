

/**
 * ЛЕГАСИ ИЗ 5 лабы
 * @author Алексей
 * @version 0.1
 *
 */
public class ADialogPhaseString extends ADialogPhase {
	private int minLength;
	private int maxLength;
	
	
	ADialogPhaseString(int minLength, int maxLength, String message, IStructure parent, String ... reservedWords) {
		super(message, parent, reservedWords);
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	@Override
	public boolean isValid(String answer) {
		// строка считается верной если количество символов входит в указанный диапазон
		return (answer.length() >= this.minLength && answer.length() <= this.maxLength) ;
	}

	@Override
	public String getMessageWithInputInfo() {
		return this.getMessage() + " - your input must contain " + this.minLength + " to " + this.maxLength + " characters";
	}

	@Override
	public String getFailMessage(String answer) {
		if (answer.isEmpty())
			return "You must enter a line between" + this.minLength + " and " + this.maxLength + " characters - you entered an empty string";
		return "You must a line between" + this.minLength + " and " + this.maxLength + " characters - you entered '" + answer + "'";
	}

}
