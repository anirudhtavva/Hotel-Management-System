import java.util.Calendar;
//provides methods for converting date between a specific instant in time and a set of calendar fields 
//such as MONTH, YEAR, HOUR, etc

public class Day implements Cloneable {
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
	private int year;
	private int month;
	private int day;

	@Override
	public Day clone() {
		Day copy = null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace(); // displays error message in the console; where we are getting the exception in
									// the source code
		}
		return copy;
	}

	// Constructor
	public Day(int y, int m, int d) {
		this.setYear(y);
		this.setMonth(m);
		this.setDay(d);
	}

	// check if a given year is a leap year
	static public boolean isLeapYear(int y) {
		if (y % 400 == 0)
			return true;
		else if (y % 100 == 0)
			return false;
		else if (y % 4 == 0)
			return true;
		else
			return false;
	}

	// check if y,m,d valid
	static public boolean valid(int y, int m, int d) {
		if (m < 1 || m > 12 || d < 1)
			return false;
		switch (m) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return d <= 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return d <= 30;
		case 2:
			if (isLeapYear(y))
				return d <= 29;
			else
				return d <= 28;
		}
		return false;
	}

	public void set(String sDay) // Set year,month,day based on a string like 01-Mar-2019
	{
		String[] sDayParts = sDay.split("-");
		this.setDay(Integer.parseInt(sDayParts[0])); // Apply Integer.parseInt for sDayParts[0];
		this.setYear(Integer.parseInt(sDayParts[2]));
		;
		this.setMonth(MonthNames.indexOf(sDayParts[1]) / 3 + 1);
	}
	
	// Constructor, simply call set(sDay)
	public Day(String sDay) {
		set(sDay);
	}

	@Override
	public String toString() {
		return getDay() + "-" + MonthNames.substring((getMonth() - 1) * 3, (getMonth()) * 3) + "-" + getYear(); // (month-1)*3,(month)*3
	}
	
	// Getter method to return year
	public int getYear() {
		return year;
	}
	
	// Setter method for year
	public void setYear(int year) {
		this.year = year;
	}
	
	// Getter method for month
	public int getMonth() {
		return month;
	}
	
	// Setter method for month
	public void setMonth(int month) {
		this.month = month;
	}
	
	// Getter method for day
	public int getDay() {
		return day;
	}
	
	// Setter method for day
	public void setDay(int day) {
		this.day = day;
	}
	
	// to compare the dining dates
	public static int CompareDineDates(Day a, Day b) {
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, a.getYear());
		c1.set(Calendar.MONTH, a.getMonth());
		c1.set(Calendar.DAY_OF_MONTH, a.getDay());

		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.YEAR, b.getYear());
		c2.set(Calendar.MONTH, b.getMonth());
		c2.set(Calendar.DAY_OF_MONTH, b.getDay());

		return c1.compareTo(c2);
	}
}