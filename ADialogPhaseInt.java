

/**
 * ËÅÃÀÑÈ ÈÇ 5 ëàáû
 * @author Àëåêñåé
 * @version 0.1
 *
 */
public class ADialogPhaseInt extends ADialogPhase {
	private int minValue;
	private int maxValue;
	

	ADialogPhaseInt(int minValue, int maxValue, String message, IStructure parent, String ... reservedWords) {
		super(message, parent, reservedWords);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public boolean isValid(String answer) {
		try {
			int answerInt = Integer.parseInt(answer);
			if (answerInt >= this.minValue && answerInt <= this.maxValue)
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
