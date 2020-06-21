public class Table {
	private String tblName;
	private int noOfSeats;

	public Table() {
		setTblName("");
		// setNoOfSeats(0) ;
	}

	public Table(String st)
	{
		this.tblName = st;
		if(st.startsWith("T"))
			this.noOfSeats = 2;
		else if(st.startsWith("F"))
			this.noOfSeats = 4;
		else if(st.startsWith("H"))
			this.noOfSeats = 8;
	}
	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tName) {
		this.tblName = tName;
	}
	
	 public int getNoOfSeats() { return noOfSeats; }
	 
	 public void setNoOfSeats(int noOfSeats) { this.noOfSeats = noOfSeats; }
	 
}
