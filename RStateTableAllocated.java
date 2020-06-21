import java.util.ArrayList;

public class RStateTableAllocated implements RState {

	private ArrayList<Table> allocatedTable = new ArrayList<Table>();

	@Override
	public String getState() {
		String result = "";
		for (Table table : allocatedTable) {
			result = result + " " + table.getTblName();
		}
		if (result.length() > 0) {
			result = "Table assigned: " + result.trim();
		}
		return result.trim();
	}
	
	//Method to get the allocated table names
	public String getAllocatedTableNames() {
		String result = "";

		for (Table table : allocatedTable) {
			result = result + ":" + table.getTblName();
		}
		return result;
	}
	
	//To clear all bookings
	public void ClearBookings() {
		allocatedTable.clear();
	}
	
	//Function to check whether a Table exists or not
	public boolean CheckTableExists(String tblName) {
		for (Table table : allocatedTable) {
			if (table.getTblName().equals(tblName)) {
				return true;
			}
		}
		return false;
	}

	public void addTableName(String table) {
		if (table.contains(",")) {
			String arr[] = table.split(",");
			for(String t : arr)
			{
				if(t.trim().length()>0)
				{
					allocatedTable.add(new Table(t.trim()));
				}
			}
		} else {
			allocatedTable.add(new Table(table));
		}
	}
	 
//	private int TotalSeatCount() {
//		int t = 0;
//		for (Table table : allocatedTable) {
//			t += table.getNoOfSeats();
//		}
//		return t;
//	}

//	private int FindTableSeatCount(String t) {
//		int count = 0;
//		if (t.startsWith("T"))
//			count = 2;
//		else if (t.startsWith("F"))
//			count = 4;
//		else if (t.startsWith("H"))
//			count = 8;
//		return count;
//	}

//	public void CheckIfTablesAssigned(ArrayList<String> strtable) throws ExBookingExist, ExTableAllocated {
//		TableList tlist = TableList.getInstance();
//		int allocatedTableCount = TotalSeatCount();
//		int passedTableCount = 0;
//		for (String stable : strtable) {
//			if (!tlist.CheckTableValid(stable)) {
//				throw new ExBookingExist("Table code " + stable + " not found!");
//			}
//			passedTableCount += FindTableSeatCount(stable);
//		}
//		if (allocatedTableCount == passedTableCount)
//			throw new ExTableAllocated("Table(s) already assigned for this booking!");
//	}

}
