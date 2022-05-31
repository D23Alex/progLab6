import java.util.Stack;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * вспомогательный класс для того, чтобы его обрабатывал JAXB
 * @author Алексей
 *
 */
@XmlRootElement
public class VehicleCollection {
	
private Stack<Vehicle> collection;

	private int dummy = 25;
	
	VehicleCollection() {
		
	}
	
	VehicleCollection(Stack<Vehicle> vehicles) {
		this.collection = vehicles;
	}
	
	public void setCollection(Stack<Vehicle> vehicles) {
		this.collection = vehicles;
	}
	
	@XmlElementWrapper
	public Stack<Vehicle> getCollection() {
		return this.collection;
	}

	@XmlElement
	public int getDummy() {
		return dummy;
	}

	public void setDummy(int dummy) {
		this.dummy = dummy;
	}
}
