import java.util.*;

public abstract class RecordedCommand implements Command {
	// Abstract method undoMe()
	public abstract void undoMe();

	// Abstract method redoMe()
	public abstract void redoMe();

	private static ArrayList<RecordedCommand> undoList = new ArrayList<>();
	private static ArrayList<RecordedCommand> redoList = new ArrayList<>();

	protected static void addUndoCommand(RecordedCommand cmd) {
		undoList.add(cmd);
	}

	protected static void addRedoCommand(RecordedCommand cmd) {
		redoList.add(cmd);
	}

	protected static void clearRedoList() {
		redoList.clear();
	}
	
	// Function used to undo one command
	public static void undoOneCommand() {

		if (undoList.isEmpty())
			System.out.println("Nothing to undo.");
		else
			undoList.remove(undoList.size() - 1).undoMe();
	}
	
	// Function used to redo one command
	public static void redoOneCommand() {

		if (redoList.isEmpty())
			System.out.println("Nothing to redo.");
		else
			redoList.remove(redoList.size() - 1).redoMe();
	}

}
