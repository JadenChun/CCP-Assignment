import java.util.LinkedList;

public class airport {

    private static boolean runwayStatus = true; //true=available
    public static boolean getRunwayStatus(){ return runwayStatus; }
    public static void setRunwayStatus(boolean b){ runwayStatus=b; }
    public static LinkedList<passenger> passengerList= new LinkedList<>();
    public static LinkedList<plane> gate1 = new LinkedList<>();
    public static LinkedList<plane> gate2 = new LinkedList<>();
    public static LinkedList<plane> gate3 = new LinkedList<>();
    public static LinkedList<plane> gate4 = new LinkedList<>();


    public static void main(String[] args) {
        System.out.print("Airport simulation starts:\n");

        initialAirport airport = new initialAirport();
        airport.start();

        planeGenerator generator = new planeGenerator();
        generator.start();

        specialCase special = new specialCase();
        special.start();
    }
}
