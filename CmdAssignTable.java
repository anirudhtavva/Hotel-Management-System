import java.util.ArrayList;

public class CmdAssignTable extends RecordedCommand {
	BookingOffice bo = BookingOffice.getInstance();
	Reservation r = null;
	String strTables = "";

	@Override
	public void execute(String[] cmdParts) {
		// TODO Auto-generated method stub
		try {
			Day sd = new Day(SystemDate.getInstance().toString()); // System date
			Day pd = new Day(cmdParts[1]); // Passed Date
			// To check the number of tables, person asked for.
			if (sd.toString().equals(pd.toString()) == false && Day.CompareDineDates(pd, sd) < 0) // if passed date is
				throw new ExDatePassed("Date has already passed!");									// less than the
																									// system date then
																									// it means that the
																									// date has already
																									// passed
				

			ArrayList<String> strtable = new ArrayList<String>();

			// Check for Number of Arguments
			if (cmdParts.length <= 3) // <= means checking from the third part
				throw new ArrayIndexOutOfBoundsException("");
			for (int x = 3; x < cmdParts.length; x++) {
				strtable.add(cmdParts[x]);
			}

			r = bo.AssignTable(cmdParts[1], Integer.parseInt(cmdParts[2]), strtable);
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Insufficient command arguments!");
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (ExTableNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExBookingExist e) {
			System.out.println(e.getMessage());
		} catch (ExBookingNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExTableAllocated e) {
			System.out.println(e.getMessage());
		} catch (ExNotEnoughSeats e) {
			System.out.println(e.getMessage());
		} catch (ExDatePassed e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void undoMe() {
		strTables = getTables();
		bo.removeAssignedTables(r);
		addRedoCommand(this);
	}

	@Override
	public void redoMe() {
		bo.addAssignedTables(r, strTables);
		addUndoCommand(this);
	}

	private String getTables() {
		if (r.getStatus() instanceof RStateTableAllocated) {
			RStateTableAllocated rsta = (RStateTableAllocated) r.getStatus();
			return rsta.getAllocatedTableNames();
		}
		return "";
	}

}
