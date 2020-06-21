import java.util.Comparator;

public class SortSupport {
	//To compare the table by the number of the table
	public static Comparator<String> tableNoComparator = new Comparator<String>() {
		@Override
		public int compare(String r1, String r2) {
			int i1 = Integer.parseInt(r1.substring(1, 3));
			int i2 = Integer.parseInt(r2.substring(1, 3));
			// System.out.println( r1 +" "+r1.substring(1,3) +" , " +r2+ " "
			// +r2.substring(1,3));
			if (i1 == i2)
				return 0;
			else if (i1 > i2)
				return 1;
			else
				return -1;
		}
	};

	//To compare the name of the table by name
	public static Comparator<String> tableNameComparator = new Comparator<String>() {
		@Override
		public int compare(String r1, String r2) {
			char i1 = r1.charAt(0);
			char i2 = r2.charAt(0);
			//Since asked to sort in the order of number of seats in a table, increase the ascii value of F 
			//making it greater than H so that the sorting can be done properly.
			if (i1 == 'F')
				i1 = (char) (i1 + 3);
			if (i2 == 'F')
				i2 = (char) (i2 + 3);

			if (i1 == i2)
				return 0;
			else if (i2 > i1)
				return 1;
			return -1;
		}
	};

	public static Comparator<Reservation> reservationSorter = new Comparator<Reservation>() {
		@Override
		public int compare(Reservation o1, Reservation o2) {

			int nameComparison = o1.getName().compareTo(o2.getName());
			if(nameComparison == 0) {
				int numberComparison = o1.getPhoneNumber().compareTo(o2.getPhoneNumber());
				if(numberComparison==0) {
					return Day.CompareDineDates(o1.getDineDate(), o2.getDineDate());
				} else
					return numberComparison;
			} else
				return nameComparison;
		}
	};
}
