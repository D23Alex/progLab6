

/**
 * ЛЕГАСИ ИЗ 5 лабы
 * @author Алексей
 * @version 0.1
 *
 */
public class ADialogPhaseFloat extends ADialogPhase {
	private float minValue;
	private float maxValue;
	private int maxCharacters;

	ADialogPhaseFloat(float minValue, float maxValue, int maxCharacters, String message, IStructure parent, String ... reservedWords) {
		super(message, parent, reservedWords);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.maxCharacters = maxCharacters;
	}

	@Override
	public boolean isValid(String answer) {
		// пробуем перевести во float и смотрим что будет
		// TODO: можно ли сломать числом 0,00...01?
		if (answer.length() > maxCharacters)
			return false;
		try {
			float answerFloat = Float.parseFloat(answer);
			if (answerFloat >= this.minValue && answerFloat <= this.maxValue)
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
		
	}

	@Override
	public String getMessageWithInputInfo() {
		return this.getMessage() + " - you must enter a decimal in range [" + this.minValue + "; " + this.maxValue + "] and use less than " + this.maxCharacters + " characters";
	}

	@Override
	public String getFailMessage(String answer) {
		return "You must enter a decimal in range [" + this.minValue + "; " + this.maxValue + "] and use less than " + this.maxCharacters + " characters using dot as separator";
	}

}
