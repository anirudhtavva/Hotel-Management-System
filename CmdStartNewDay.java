public class CmdStartNewDay extends RecordedCommand {

	String newSystemDate;
	String oldSystemDate;

	@Override
	public void execute(String[] cmdParts) {

		try {
			oldSystemDate = SystemDate.getInstance().toString();
			SystemDate.getInstance().set(cmdParts[1]);
			newSystemDate = cmdParts[1];
			System.out.println("Done.");

			addUndoCommand(this);
			clearRedoList();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Insufficient command arguments!");
		}  
	}

	@Override
	public void undoMe() {

		SystemDate.getInstance().set(oldSystemDate);
		addRedoCommand(this);
		// TODO Auto-generated method stub

	}

	@Override
	public void redoMe() {

		SystemDate.getInstance().set(newSystemDate);
		addUndoCommand(this);
		// TODO Auto-generated method stub

	}

}
