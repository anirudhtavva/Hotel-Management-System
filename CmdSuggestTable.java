public class CmdSuggestTable implements Command {

    BookingOffice bo = BookingOffice.getInstance();

    @Override
    public void execute(String[] cmdParts) {

        if(cmdParts.length<3) {
            System.out.println("Insufficient command arguments!");
            return;
        }

        try {
            System.out.println(bo.SuggestTables(cmdParts[1], Integer.parseInt(cmdParts[2])));
        } catch (ExBookingNotFound exBookingNotFound) {
            System.out.println(exBookingNotFound.getMessage());
        } catch (ExNotEnoughSeats exNotEnoughSeats) {
            System.out.println(exNotEnoughSeats.getMessage());
        } catch (NumberFormatException nfe) {
            System.out.println("Wrong number format!");
        } catch (ExTableAllocated exTableAllocated) {
            System.out.println(exTableAllocated.getMessage());
        }
    }
}
