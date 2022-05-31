import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Stack;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Test {

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		
		//LogicServerRequestAdd req = new LogicServerRequestAdd("default", new Vehicle());
		
		
		Vehicle vehicle1 = new Vehicle(VehicleType.CHOPPER, 1l, "ChopperName1", new Coordinates(10, 20), LocalDate.now(), 100, FuelType.ALCOHOL);
		Vehicle vehicle2 = new Vehicle(VehicleType.CHOPPER, 2l, "ChopperName2", new Coordinates(3, 4), LocalDate.now(), 50, FuelType.ALCOHOL);
		Stack<Vehicle> vehicles = new Stack<>();
		vehicles.push(vehicle1);
		vehicles.push(vehicle2);
		VehicleCollection vehicleCollection = new VehicleCollection(vehicles);
		String collectionName = "default";
		JAXBContext context;
		context = JAXBContext.newInstance(VehicleCollection.class);
		Marshaller mar= context.createMarshaller();
	    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    OutputStream os = new FileOutputStream("./collections/" + collectionName + ".xml");
	    mar.marshal(vehicleCollection, os);
	    System.out.println("DONE");
		
	}

}
