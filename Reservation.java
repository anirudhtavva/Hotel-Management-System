import java.util.Comparator;
//used when comparing two objects of two different classes

public class Reservation {
	// all private variables
	private String guestName;
	private String phoneNumber;
	private int totPersons;
	private RState status;
	private int ticketId;
	private Day dateDine;
	private Day dateRequest;
	
	// Reservation constructor
	public Reservation(String guestName, String phoneNumber, int totPersons, String sDateDine, int tid)
			throws ExDatePassed {
		this.guestName = guestName;
		this.phoneNumber = phoneNumber;
		this.setTotPersons(totPersons);
		this.ticketId = tid;

		this.setStatus(new RStatePending());

		this.dateDine = new Day(sDateDine);
		this.dateRequest = SystemDate.getInstance().clone();
		if (this.dateDine.toString().equals(this.dateRequest.toString()) == false) {
			if (Day.CompareDineDates(this.dateDine, this.dateRequest) < 0)
				throw new ExDatePassed("Date has already passed!");
		}
	}
	
	// Getter methods to return guest name
	public String getName() {
		return guestName;
	}
	
	// Getter methods to return phoneNumber
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	// Getter methods to return ticketId
	public int getTicketNo() {
		return ticketId;
	}
	
	// Getter methods to return dining date
	public Day getDineDate() {
		return dateDine;
	}
	
	// Getter methods to return request date
	public Day getRequestDate() {
		return dateRequest;
	}

	@Override
	public String toString() {
		// Learn: "-" means left-aligned
		return String.format("%-13s%-11s%-14s%-25s%4d       %s", guestName, phoneNumber, dateRequest,
				dateDine + " (Ticket " + ticketId + ")", getTotPersons(), status.getState());
	}

	public static String getListingHeader() {
		return String.format("%-13s%-11s%-14s%-25s%-11s%s", "Guest Name", "Phone", "Request Date",
				"Dining Date and Ticket", "#Persons", "Status");
	}
	
	//Comparing names
	public static Comparator<Reservation> nameComparator = new Comparator<Reservation>() {
		@Override
		public int compare(Reservation r1, Reservation r2) {
			if (r1.getName().equals(r2.getName()))
				return 0;
			else if (r1.getName().compareTo(r2.getName()) > 0)
				return 1;
			else
				return -1;
		}
	};
	
	//Comparing dining dates
	public static Comparator<Reservation> dayComparator = new Comparator<Reservation>() {
		@Override
		public int compare(Reservation r1, Reservation r2) {
			return compareDays(r1.dateDine, r2.dateDine);
		}
	};
	
	//Comparing phone numbers
	public static Comparator<Reservation> phoneComparator = new Comparator<Reservation>() {
		@Override
		public int compare(Reservation r1, Reservation r2) {
			if (r1.phoneNumber == r2.phoneNumber)
				return 0;
			else if (r1.phoneNumber.compareTo(r2.phoneNumber) > 0)
				return 1;
			else
				return -1;
		}
	};

	private static int compareDays(Day a, Day b) {
		return Day.CompareDineDates(a, b);
	}
	
	// Getter methods to return status
	public RState getStatus() {
		return status;
	}
	
	//Setter method
	public void setStatus(RState status) {
		this.status = status;
	}
	
	// Getter methods to return total persons
	public int getTotPersons() {
		return totPersons;
	}
	
	//Setter method 
	public void setTotPersons(int totPersons) {
		this.totPersons = totPersons;
	}

}