import java.io.Serializable;

/**
 * ...
 * @author Алексей
 *
 */
public abstract class Request implements IRequest, Serializable {
	
	private String collectionName;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7062951802215731357L;
	
	
	public Request(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	
	
}
