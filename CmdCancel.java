
public class CmdCancel extends RecordedCommand {

	Reservation r;
	RState state;
	int index = -1;
	BookingOffice bo = BookingOffice.getInstance();

	@Override
	public void execute(String[] cmdParts) {


		if (Day.CompareDineDates(SystemDate.getInstance(), new Day(cmdParts[1])) > 0) {
			System.out.println("Date has already passed!");
			return;
		}
		try {
//			index = bo.getReservationIndex(cmdParts[1],Integer.parseInt(cmdParts[2]));

			r = bo.cancelReservation(cmdParts[1],Integer.parseInt(cmdParts[2]));
			state = r.getStatus();
			r.setStatus(new RStateCancel());
			bo.updateReservation(r);
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Insufficient command arguments!");
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExBookingNotFound exBookingNotFound) {
			System.out.println(exBookingNotFound.getMessage());
		}
	}

	@Override
	public void undoMe() {
		r.setStatus(state);
		bo.updateReservation(r);
		addRedoCommand(this);
	}

	@Override
	public void redoMe() {
		r.setStatus(new RStateCancel());
		bo.updateReservation(r);
		addUndoCommand(this);
	}
}
