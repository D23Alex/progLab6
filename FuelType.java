

import java.util.HashSet;
import java.util.Set;

public enum FuelType {
	ELECTRICITY,
    ALCOHOL,
    NUCLEAR;
	public static Set<String> toStringSet() {
		Set<String> set = new HashSet<String>();
		set.add("ELECTRICITY");
		set.add("ALCOHOL");
		set.add("NUCLEAR");
		return set;
	}
	
	public static FuelType getFuelTypeByName(String fuelType) throws IllegalArgumentException {
		if (fuelType.toUpperCase().equals("ELECTRICITY")) {
			return FuelType.ELECTRICITY;
		}
		if (fuelType.toUpperCase().equals("ALCOHOL")) {
			return FuelType.ALCOHOL;
		}
		if (fuelType.toUpperCase().equals("NUCLEAR")) {
			return FuelType.NUCLEAR;
		}
		throw new IllegalArgumentException();
	}
}
