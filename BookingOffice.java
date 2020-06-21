import java.util.*;
import java.util.Map.Entry;

public class BookingOffice {
	private ArrayList<Reservation> allReservations;
	private static int genTicNo = 0;
	private static BookingOffice instance = new BookingOffice();
	
	//Constructor
	private BookingOffice() {
		allReservations = new ArrayList<Reservation>();
	}

	public static BookingOffice getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}
	
	// Adding all the Reservations
	public Reservation addReservation(String name, String number, int i, String date)
			throws ExDatePassed, ExBookingExist {
		int found = 0;
		int tickId = 1;
		Reservation newRes = null;

		for (Reservation r : allReservations) {
			if ((r.getDineDate().toString().trim().compareTo(date.trim())) == 0) {
				tickId++;
				found = 1;
			}
		}
		
		// To check if the booking for the same person for dining date exits
		if (found == 0) {
			BookingOffice.genTicNo = 1;
			Reservation r = new Reservation(name, number, i, date, BookingOffice.genTicNo);
			if (hasReservation(r))
				// Throws the ExBookingExist exception
				throw new ExBookingExist("Booking by the same person for the dining date already exists!");
			newRes = r;
			allReservations.add(r);
		} else {
			Reservation r = new Reservation(name, number, i, date, tickId);
			if (hasReservation(r))
				throw new ExBookingExist("Booking by the same person for the dining date already exists!");
			allReservations.add(r);
			newRes = r;
		}
		// Sorting the dining dates and names 
		Collections.sort(allReservations, Reservation.dayComparator);
		Collections.sort(allReservations, Reservation.nameComparator);
		// Collections.sort(allReservations, Reservation.phoneComparator);

		// Collections.sort(allReservations); // allReservations
		return newRes; // Why return? Ans: You'll see that it is useful in CmdRequest.java in Q2.
	}
	
	// Removing a reservation from the list
	public void removeReservation(Reservation r) {
		// System.out.println(r.getName() + " , " + r.getTicketNo());
		allReservations.remove(r);
	}
	
	//To remove the assigned tables
	public void removeAssignedTables(Reservation r) {
		int index = allReservations.indexOf(r);
		r.setStatus(new RStatePending());
		allReservations.set(index, r);
	}
	
	//To add the assigned tables
	public void addAssignedTables(Reservation r, String allocatedTables) {
		int index = allReservations.indexOf(r);
		String[] starr = allocatedTables.split(":");
		for (String st : starr) {
			try {
				saveTables(index, st, r);
			} catch (ExTableNotFound e) {
				// Do not do anything as only valid tables will be provided
			}
		}
	}
	
	// Adding a reservation to the list
	public void addReservation(Reservation r) {
		allReservations.add(r);
		// Collections.sort(allReservations);
		Collections.sort(allReservations, Reservation.nameComparator);
	}
	
	// List all the bookings done by the people
	public void listReservations() {
		System.out.println(Reservation.getListingHeader()); // Reservation
		allReservations.sort(SortSupport.reservationSorter);
		for (Reservation r : allReservations) {

			if (r.getStatus() instanceof RStateCancel) {
				continue;
			}

			System.out.println(r.toString().trim()); // r or r.toString()
		}
	}
	
	//To display the list of allocated tables
	public void listAllocatedTables(String date) throws ExBookingExist, ExTableAllocated {
		Map<Integer, String> dicAlloctables = new HashMap<Integer, String>();
		int totalPendingRequest = 0;
		int totalNumberOfPersons = 0;
		String aTables = "";

		for (Reservation r : allReservations) {

			if (r.getDineDate().toString().contentEquals(date)) {

				if (r.getStatus() instanceof RStateTableAllocated) {
					RStateTableAllocated rsta = (RStateTableAllocated) r.getStatus();
					aTables = rsta.getAllocatedTableNames();
					dicAlloctables.put(r.getTicketNo(), aTables);
				} else if (r.getStatus() instanceof RStatePending) {
					totalPendingRequest++; 
					totalNumberOfPersons = totalNumberOfPersons + r.getTotPersons();
				}
			}
		}

		if (dicAlloctables.size() > 0) {
			System.out.println("Allocated tables: ");
			ArrayList<String> allocatedTables = new ArrayList<String>();
			for (Entry<Integer, String> e : dicAlloctables.entrySet()) {
				String arrTblNames[] = e.getValue().split(":");

				for (String st : arrTblNames) {
					if (st.trim().length() > 0) {
						allocatedTables.add(st + " (Ticket " + e.getKey() + ")");
					}
				}
				Collections.sort(allocatedTables);
				Collections.sort(allocatedTables, SortSupport.tableNameComparator);
			}

			for (String t : allocatedTables) {
				System.out.println(t);
			}
			System.out.println("\nAvailable tables: ");
			DisplayAvailableTables(dicAlloctables);
			System.out.println("\nTotal number of pending requests = " + totalPendingRequest
					+ " (Total number of persons = " + totalNumberOfPersons + ")");

		} else {
			System.out.println("Allocated tables: ");
			System.out.println("[None]");
			System.out.println("\nAvailable tables: ");
			DisplayAvailableTables(dicAlloctables);
			System.out.println("\nTotal number of pending requests = " + totalPendingRequest
					+ " (Total number of persons = " + totalNumberOfPersons + ")");
		}

	}
	
	//To display the list of available tables
	private void DisplayAvailableTables(Map<Integer, String> dicAlloctables) {
		TableList tList = TableList.getInstance();
		String arrTables[] = tList.GetListOfTables().split(":");
		String result = "";
		ArrayList<String> atList = new ArrayList<String>(); // Allocated Tables

		for (Entry<Integer, String> e : dicAlloctables.entrySet()) {
			for (String t : e.getValue().split(":")) {
				if (t.trim().length() > 0) {
					atList.add(t);
				}
			}
		}

		for (String t : arrTables) {
			if (t.trim().length() > 0) {
				if (atList.indexOf(t) <= -1) {
					result = result + " " + t;
				}
			}
		}
		System.out.println(result.trim());
	}

	public String SuggestTables(String date, int ticketNumber) throws ExBookingNotFound, ExNotEnoughSeats, ExTableAllocated {

		String aTables = "";

		SortedSet<String> unallocatedTables = new TreeSet<>();
		TableList tList = TableList.getInstance();
		List<String> allTables = Arrays.asList(tList.GetListOfTables().split(":"));
		unallocatedTables.addAll(allTables);
		Reservation reservation = null;
		for (Reservation r : allReservations) {
			if (r.getDineDate().toString().contentEquals(date)) {
				if (r.getStatus() instanceof RStateTableAllocated) {
					RStateTableAllocated rsta = (RStateTableAllocated) r.getStatus();
					aTables = rsta.getAllocatedTableNames();
					List<String> allocatedTables = Arrays.asList(aTables.split(":"));
					unallocatedTables.removeAll(allocatedTables);
				}
				if(r.getTicketNo() == ticketNumber) {
					reservation = r;
				}
			}
		}
		if(reservation == null) {
			throw new ExBookingNotFound("Booking not found!");
		}

		if(reservation.getStatus() instanceof RStateTableAllocated) {
			throw new ExTableAllocated("Table(s) already assigned for this booking!");
		}

		if(reservation.getStatus() instanceof RStateCancel) {
			throw new ExBookingNotFound("Booking not found!");
		}

		String tables;
		try {
			tables = findEmptyTableForPeople(reservation.getTotPersons(), unallocatedTables);
		} catch (ExNotEnoughSeats ex) {
			throw new ExNotEnoughSeats("Suggestion for " + reservation.getTotPersons() + " persons: " + ex.getMessage());
		}

		if(tables == null || tables.isEmpty()) {
			throw new ExNotEnoughSeats("Not enough seats for the booking!");
		}
		System.out.print("Suggestion for " + reservation.getTotPersons() + " persons: ");
		return tables;
	}

	//To suggest tables from list of available tables
	private String findEmptyTableForPeople(int numberOfPeople, SortedSet<String> remainingTables) throws ExNotEnoughSeats {

		int hTablesRemaining=0, fTablesRemaining=0, tTablesRemaining=0;
		boolean skippedH = false, skippedF = false, skippedT = false;
		int numberOFRemainingTables = remainingTables.size();
		do {
			if (numberOfPeople > 4 || skippedH) {

				skippedH = false;
				//suggest 8 seater table
				for (String table : remainingTables) {
					if (table.startsWith("H")) {

						if (numberOfPeople <= 8)
							return table + " ";
						else {
							remainingTables.remove(table);
							return table + " " + findEmptyTableForPeople(numberOfPeople - 8, remainingTables);
						}
					}
					hTablesRemaining++;
					numberOFRemainingTables--;
				}
			} else {
				skippedH = true;
			}
			if (numberOfPeople > 2 || skippedF) {
				skippedF = false;
				//suggest 4 seater table
				for (String table : remainingTables) {
					if (table.startsWith("F")) {

						if (numberOfPeople <= 4)
							return table + " ";
						else {
							remainingTables.remove(table);
							return table + " " + findEmptyTableForPeople(numberOfPeople - 4, remainingTables);
						}
					}
					fTablesRemaining++;
					numberOFRemainingTables--;
				}
			} else {
				skippedF = true;
			}
			//suggest 2 seater table
			for (String table : remainingTables) {
				if (table.startsWith("T")) {

					if (numberOfPeople <= 2)
						return table + " ";
					else {
						remainingTables.remove(table);
						return table + " " + findEmptyTableForPeople(numberOfPeople - 2, remainingTables);
					}
				}
				tTablesRemaining++;
				numberOFRemainingTables--;
			}

		} while (!skippedH && !skippedH);

		throw new ExNotEnoughSeats("Not enough tables");
//		return null;
	}
	
	//To check if the tables are assigned or not
	private void CheckIfTablesAreAssigned(ArrayList<String> strtable, String date, int ticNo)
			throws ExBookingExist, ExTableAllocated {
		TableList tlist = TableList.getInstance();

		String passedTables = String.join(",", strtable);

		for (String stable : strtable) {
			for (Reservation r : allReservations) {
				if (!tlist.CheckTableValid(stable)) {
					throw new ExBookingExist("Table code " + stable + " not found!");
				} else {
					// System.out.println(r.getDineDate() + " , " +date + " , " +ticNo);
					if (r.getDineDate().toString().equals((new Day(date)).toString()) && r.getTicketNo() == ticNo) {
						CheckIfBooked(passedTables, r, tlist);

					} else if (r.getDineDate().toString().equals((new Day(date)).toString())) {
						if (checkForTables(stable, r)) {
							throw new ExTableAllocated("Table " + stable + " is already reserved by another booking!");
						}
					}
				}
			}
		}
	}
	
	//To check if the tables are booked for 
	private void CheckIfBooked(String passedTables, Reservation r, TableList tList) throws ExTableAllocated {
		int total = 0;
		for (String t : passedTables.split(",")) //To get table names individually
		{
			total = total + tList.GetTotalSeatCountByTableType(t);
		}

		if (total >= r.getTotPersons() && r.getStatus() instanceof RStateTableAllocated) {
			throw new ExTableAllocated("Table(s) already assigned for this booking!");
		}
	}
	
	//To assign the tables
	public Reservation AssignTable(String date, int ticketnumber, ArrayList<String> strtable) throws ExTableNotFound,
			ExBookingExist, ExBookingNotFound, ExTableAllocated, ExNotEnoughSeats, ExDatePassed {
		int found = 0;
		Reservation res = null;
		TableList tlist = TableList.getInstance();

		int totalSeats = 0;

		for (String stable : strtable) {
			totalSeats = totalSeats + tlist.GetTotalSeatCountByTableType(stable);
		}

		CheckIfTablesAreAssigned(strtable, date, ticketnumber);

		String stable = String.join(",", strtable);
		for (Reservation r : allReservations) {
			if (r.getDineDate().toString().equals(date) && r.getTicketNo() == ticketnumber) {

				if (r.getStatus() instanceof RStatePending) {
					if (r.getTotPersons() > totalSeats) {
						throw new ExNotEnoughSeats("Not enough seats for the booking!");
					}
				}
				// System.out.println("Table Name : " + stable);
				int idx = allReservations.indexOf(r);
				saveTables(idx, stable, r);
				found = 1;
				res = r;
			}
		}
		if (found == 0) {
			throw new ExBookingNotFound("Booking not found!");
		}
		return res;

	}
	
	//Table name gets saved to the allocated Table list
	private void saveTables(int index, String tblName, Reservation r) throws ExTableNotFound {

		if (r.getStatus() instanceof RStatePending) {
			RStateTableAllocated rsta = new RStateTableAllocated();

			rsta.addTableName(tblName);
			r.setStatus(rsta);
		} else if (r.getStatus() instanceof RStateTableAllocated) {
			RStateTableAllocated rsta = (RStateTableAllocated) r.getStatus();
			rsta.addTableName(tblName);
			r.setStatus(rsta);
		}
		allReservations.set(index, r);
	}
	
	//To check for tables
	private boolean checkForTables(String stable, Reservation reservation) throws ExBookingExist {
		if (reservation.getStatus() instanceof RStateTableAllocated) {
			RStateTableAllocated rta = (RStateTableAllocated) reservation.getStatus();
			if (rta.CheckTableExists(stable)) {
				return true;
			}
		}
		return false;
	}
	
	//To cancel the reservation
	public Reservation cancelReservation(String date, int ticketnumber) throws ExBookingNotFound {
		boolean found = false;
		Reservation foundReservation = null;

//		allReservations.get()

		for (Reservation reservation : allReservations) {
//			System.out.println("1=" + reservation.getDineDate().toString() + " 2 " + date + " is " + reservation.getDineDate().toString().contentEquals(date));
			if (reservation.getDineDate().toString().contentEquals(date) && reservation.getTicketNo() == ticketnumber && (reservation.getStatus() instanceof RStateTableAllocated || reservation.getStatus() instanceof RStatePending)) {
				found=true;
				foundReservation = reservation;
//				int index = allReservations.indexOf(reservation);
//				reservation.setStatus(new RStateCancel());
//				allReservations.set(index, reservation);
				break;
			}
		}
		if(!found) {
			throw new ExBookingNotFound("Booking not found!");
		}
		return foundReservation;
	}

	public int getReservationIndex(String date, int ticketnumber) throws ExBookingNotFound {
		for (Reservation reservation : allReservations) {
//			System.out.println("1=" + reservation.getDineDate().toString() + " 2 " + date + " is " + reservation.getDineDate().toString().contentEquals(date));
			if (reservation.getDineDate().toString().contentEquals(date) && reservation.getTicketNo() == ticketnumber) {
				int index = allReservations.indexOf(reservation);
				return index;
			}
		}
		throw new ExBookingNotFound("Booking not found!");
	}

	public void updateReservation(Reservation updatedReservation) {
		int index = -1;
		for (Reservation reservation : allReservations) {
//			System.out.println("1=" + reservation.getDineDate().toString() + " 2 " + date + " is " + reservation.getDineDate().toString().contentEquals(date));
			if (reservation.getDineDate().toString().contentEquals(updatedReservation.getDineDate().toString()) && reservation.getTicketNo() == updatedReservation.getTicketNo()) {
				index = allReservations.indexOf(reservation);
			}
		}
		allReservations.set(index, updatedReservation);
	}

	public Reservation getReservation(String phoneNum) {
		for (Reservation r : allReservations)
			if (r.getPhoneNumber().equals(phoneNum))
				return r;
		return null;
	}
	
	// To check if the reservation is available or not.
	private boolean hasReservation(Reservation r) {
		if (allReservations.size() == 0)
			return false;
		else {
			Reservation f = allReservations.stream().filter(s -> s.getName().equals(r.getName())
					&& s.getDineDate().toString().equals(r.getDineDate().toString())).findFirst().orElse(null);

			if (f != null)
				return true;

		}
		return false;

	}
}