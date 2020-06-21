public class SystemDate extends Day {
    private static SystemDate instance;
     
    private SystemDate(String sDay)
    {
        super(sDay);
    }
     
    public static Day getInstance() {
        // TODO Auto-generated method stub
        return instance;
    }
     
    public static void createTheInstance(String sDay) {
        // TODO Auto-generated method stub
        if(instance == null)
            instance = new SystemDate(sDay);
        else
            System.out.println("Cannot create one more system data instance.");
    }
     
}