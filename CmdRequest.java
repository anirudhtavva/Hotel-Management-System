public class CmdRequest extends RecordedCommand {
	// Object of the BookingOffice class
	BookingOffice bo = BookingOffice.getInstance();
	Reservation r;

	@Override
	public void execute(String[] cmdParts) {
		// try - catch block
		try {
			r = bo.addReservation(cmdParts[1], cmdParts[2], Integer.parseInt(cmdParts[3]), cmdParts[4]);
			System.out.printf("Done. Ticket code for %s: %d\n", r.getDineDate(), r.getTicketNo());
			addUndoCommand(this);
			clearRedoList();
		} catch (ArrayIndexOutOfBoundsException e) {
			// thrown to indicate that we are trying to access array element with an illegal
            // index.
			System.out.println("Insufficient command arguments!");
		} catch (NumberFormatException e) {
			// thrown when we try to convert a string into a numeric value such as float or
            // integer, but the format of the input string is not appropriate or illegal.
			System.out.println("Wrong number format!");
		} catch (ExDatePassed e) {
			System.out.println(e.getMessage());	// used to return a detailed message of the Throwable object
		} catch (ExBookingExist e) {
			System.out.println(e.getMessage()); // used to return a detailed message of the Throwable object
		}
	}

	@Override
	public void undoMe() {		
		bo.removeReservation(r); // calling the removeReservation function
		addRedoCommand(this);
		// TODO Auto-generated method stub

	}

	@Override
	public void redoMe() {		
		bo.addReservation(r); // calling the addReservation function
		addUndoCommand(this);
		// TODO Auto-generated method stub

	}

}
