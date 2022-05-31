import java.util.Comparator;

public enum Criteria {
	NAME {
		@Override
		public Comparator<Vehicle> getComparator() {
			return new Comparator<Vehicle>() {

				@Override
				public int compare(Vehicle o1, Vehicle o2) {
					return o1.getName().compareTo(o2.getName());
				}
			};
		}
	},
	ID {
		@Override
		public Comparator<Vehicle> getComparator() {
			return new Comparator<Vehicle>() {

				@Override
				public int compare(Vehicle o1, Vehicle o2) {
					return (int) (o1.getId() - o2.getId());
				}
			};
		}
	},
	X {
		@Override
		public Comparator<Vehicle> getComparator() {
			return new Comparator<Vehicle>() {

				@Override
				public int compare(Vehicle o1, Vehicle o2) {
					return o1.getCoordinates().getX() - o2.getCoordinates().getX();
				}
			};
		}
	},
	Y {
		@Override
		public Comparator<Vehicle> getComparator() {
			return new Comparator<Vehicle>() {
				@Override
				public int compare(Vehicle o1, Vehicle o2) {
					if (o1.getCoordinates().getY() > o2.getCoordinates().getY()) {
						return 1;
					}
					if (o1.getCoordinates().getY() == o2.getCoordinates().getY()) {
						return 0;
					}
						return -1;
				}
			};
		}
	},
	DATE {
		@Override
		public Comparator<Vehicle> getComparator() {
			return new Comparator<Vehicle>() {

				@Override
				public int compare(Vehicle o1, Vehicle o2) {
					return o1.getCreationDate().compareTo(o2.getCreationDate());
				}
			};
		}
	};
	public abstract Comparator<Vehicle> getComparator();
	
	public static Criteria getCriteriaByName(String name) throws IllegalArgumentException {
		if (name.toUpperCase().equals("NAME")) {
			return Criteria.NAME;
		}
		if (name.toUpperCase().equals("ID")) {
			return Criteria.ID;
		}
		if (name.toUpperCase().equals("X")) {
			return Criteria.X;
		}
		if (name.toUpperCase().equals("Y")) {
			return Criteria.Y;
		}
		if (name.toUpperCase().equals("DATE")) {
			return Criteria.DATE;
		}
		throw new IllegalArgumentException();
	}
}