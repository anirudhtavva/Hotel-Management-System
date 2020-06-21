import java.util.ArrayList;

public class TableList {
	private ArrayList<Table> listOfTables;

	private static TableList instance = new TableList();

	private TableList() {
		listOfTables = new ArrayList<Table>();
		PopulateTableList();
	}

	public static TableList getInstance() {
		return instance;
	}

	private  void PopulateTableList() {
		// T01 - T10
		for (int x = 0; x < 10; x++) {
			Table t = new Table();
			if (x + 1 <= 9) {
				t.setTblName("T0" + (x + 1));
			} else if (x + 1 == 10) {
				t.setTblName("T" + (x + 1));
			}
			t.setNoOfSeats(2);
			listOfTables.add(t);
		}
		// F01 - F06
		for (int x = 0; x < 6; x++) {
			Table t = new Table();
			t.setTblName("F0" + (x + 1));
			t.setNoOfSeats(4);
			listOfTables.add(t);
		}
		// H01 - H03
		for (int x = 0; x < 3; x++) {
			Table t = new Table();
			t.setTblName("H0" + (x + 1));
			t.setNoOfSeats(8);
			listOfTables.add(t);
		}
	}
	
	public boolean CheckTableValid(String tblName)
	{
		for (Table table : listOfTables) {
			if(table.getTblName().equals(tblName))
			{
				return true;
			}
			
		}
		return false;	 
	}
	
	public String GetListOfTables()
	{
		String lst = "" ;
		for (Table table : listOfTables) {
			lst = lst + ":" + table.getTblName();
		}
		return lst;
	}
	
	public int GetTotalSeatCountByTableType(String sTable)
	{
		int count = 0 ;
		if(sTable.startsWith("T"))
			count = 2;
		else if(sTable.startsWith("F"))
			count = 4;
		else if(sTable.startsWith("H"))
			count = 8;
		return count;
	}

}
