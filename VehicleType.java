

import java.util.HashSet;
import java.util.Set;

public enum VehicleType {
	SUBMARINE,
    CHOPPER,
    HOVERBOARD;
	public static Set<String> toStringSet() {
		Set<String> set = new HashSet<String>();
		set.add("SUBMARINE");
		set.add("CHOPPER");
		set.add("HOVERBOARD");
		return set;
	}
	
	public static VehicleType getVehicleTypeByName(String vehicleType) throws IllegalArgumentException {
		if (vehicleType.toUpperCase().equals("SUBMARINE")) {
			return VehicleType.SUBMARINE;
		}
		if (vehicleType.toUpperCase().equals("CHOPPER")) {
			return VehicleType.CHOPPER;
		}
		if (vehicleType.toUpperCase().equals("HOVERBOARD")) {
			return VehicleType.HOVERBOARD;
		}
		throw new IllegalArgumentException();
	}
}
