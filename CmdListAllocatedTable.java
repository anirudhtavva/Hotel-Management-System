
public class CmdListAllocatedTable implements Command {
	BookingOffice bo = BookingOffice.getInstance();

	@Override
	public void execute(String[] cmdParts) {

		try {			
			bo.listAllocatedTables(cmdParts[1]);

		} catch (ExBookingExist e) {
			System.out.println(e.getMessage());
		} catch (ExTableAllocated e) {
			System.out.println(e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Insufficient command arguments!");
		}
	}
}
