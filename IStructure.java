
/**
 * ËÅÃÀÑÈ èç 5 ëàáû
 * This intarface, being implemented by all the structural parts of the app, tells that this part has it's structure, that will be printed as a prefix whenever this part of the app talks to the user
 * @author Àëåêñåé
 *
 */
public interface IStructure {
	
	/**
	 * 
	 * @return the text-based description of this part of the app. By default it's the description of the parent's structure followed by '>' and the name of this part
	 */
	String getStructureDescription();
}
