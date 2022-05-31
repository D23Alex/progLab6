

/**
 * ËÅÃÀÑÈ ÈÇ 5 ëàáû
 * @author Àëåêñåé
 * @version 0.1
 *
 */
public class ADialogPhaseLong extends ADialogPhase {

	private long minValue;
	private long maxValue;
	

	ADialogPhaseLong(long minValue, long maxValue, String message, IStructure parent, String ... reservedWords) {
		super(message, parent, reservedWords);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public boolean isValid(String answer) {
		try {
			long answerLong = Long.parseLong(answer);
			if (answerLong >= this.minValue && answerLong <= this.maxValue)
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public String getMessageWithInputInfo() {
		return this.getMessage() + " - you must enter an integer in range [" + this.minValue + "; " + this.maxValue + "]";
	}

	@Override
	public String getFailMessage(String answer) {
		return "You must enter a decimal in range [" + this.minValue + "; " + this.maxValue + "]";
	}

}
