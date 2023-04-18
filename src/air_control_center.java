import java.util.HashMap;
import java.util.LinkedList;

public class air_control_center implements Runnable{

    public static LinkedList<plane> requestList= new LinkedList<>();
    public static HashMap<String,String> gateStatus = new HashMap<String, String>();
    public static int planeServed=0;
    private LinkedList<Long> waitingTime = new LinkedList();
    public static double maxSec;
    public static double minSec;
    public static double averageSec;

    synchronized public static boolean requestTakeOff(plane plane){ //return permission
        boolean result = false;
        try{
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        if (airport.getRunwayStatus()){
            System.out.print("\nThread-ATC: Request approve. Plane "+plane.id+" You may now proceed to take off.");
            planeServed+=1;
            airport.setRunwayStatus(false);
            result = true;
        }else{
            System.out.print("\nThread-ATC: Request rejected. Plane "+plane.id+", please join the take off queue.");
        }
        return result;
    }

    synchronized public static String[] requestLanding(plane plane){ //return permission and gate number to go
        String permission = "false";
        String gate = "";
        try{
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        if (airport.getRunwayStatus()){
            for (String i: gateStatus.keySet()){
                if (gateStatus.get(i)=="Free"){
                    airport.setRunwayStatus(false);
                    System.out.print("\nThread-ATC: Request approve. Plane "+plane.id+", after landing please go to "+i+".");
                    permission = "true";
                    gate = i;
                    break;
                }
            }
        }else{
            System.out.print("\nThread-ATC: Request rejected. Plane "+plane.id+", please join the landing queue.");
        }
        String[] result = {permission,gate};
        return result;
    }

    public void run(){
        //assign plane while plane in list
        while (airport.passengerList.size()!=0){
            try {
                Thread.sleep(300);
                if (airport.getRunwayStatus() && requestList.size()!=0) {
                    synchronized (requestList) {
                        plane plane= requestList.poll();
                        if (plane.request=="landing") {
                            boolean gate = false;
                            for (String i : gateStatus.keySet()) {
                                if (gateStatus.get(i) == "Free") {
                                    gate = true;
                                }
                            }
                            if (gate) {
                                System.out.print("\nThread-ATC: Plane " + plane.id + ",runway is clear for landing, Please request again to land now!");
                                plane.landing();
                                Thread.sleep(10);
                                waitingTime.add(plane.landingWaitTime);
                            } else {
                                synchronized (requestList){
                                    requestList.addFirst(plane);
                                    for (plane p : requestList){
                                        if(p.request=="takeOff"){
                                            requestList.remove(p);
                                            requestList.addFirst(p);
                                            break;
                                        }
                                    }
                                }
                            }
                         } else {
                            System.out.print("\nThread-ATC: Plane " + plane.id + ",runway is clear for take off, Please request again to take off now!");
                            plane.takeOff();
                            Thread.sleep(10);
                            waitingTime.add(plane.takeOffWaitTime);
                        }
                    }
                }
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
        }
        long max = 0;
        long min = 0;
        long total = 0;
        for (int i=0;i<waitingTime.size();i++){
            long x = waitingTime.get(i);
            if (i==0){
                max = min= x;
            }else{
                if (min>x){
                    min = x;
                }else if(max<x){
                    max = x;
                }
            }
            total += x;
        }
        long average = total/waitingTime.size();
        maxSec = max/1000000000;
        minSec = min/1000000000;
        averageSec = average/1000000000;
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\nThread-ATC: End.");
        if (airport.gate1.size()==0){
            System.out.print("\nGate 1: Empty");
        }
        if (airport.gate2.size()==0){
            System.out.print("\nGate 2: Empty");
        }
        if (airport.gate3.size()==0){
            System.out.print("\nGate 3: Empty");
        }
        if (airport.gate4.size()==0){
            System.out.print("\nGate 4: Empty");
        }
        System.out.print("\nNumber of planes served: "+(air_control_center.planeServed-2));
        int passengerBoarded = (air_control_center.planeServed)*50;
        System.out.print("\nNumber of passenger boarded: "+passengerBoarded);
        System.out.print("\nMaximum waiting time: "+air_control_center.maxSec);
        System.out.print("\nMinimum waiting time: "+air_control_center.minSec);
        System.out.print("\nAverage waiting time: "+air_control_center.averageSec);
    }
}
