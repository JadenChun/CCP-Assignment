import java.util.LinkedList;

public class plane {

    int id;
    String gate; //(Gate 1, Gate 2, Gate 3, Gate 4)
    String request; //(takeOff, landing)
    long takeOffStart;
    long takeOffWaitTime;
    long landingStart;
    long landingWaitTime;

    plane(int id){
        this.id=id;
    }

    public void takeOff(){
        try {
            System.out.print("\nMain: Plane " + this.id + ": Request to take off.");
            boolean result = air_control_center.requestTakeOff(this);
            if (result) {
                takeOffWaitTime = System.nanoTime() - takeOffStart;
                System.out.print("\nPlane " + this.id + ": Moving to runway to take off.");
                Thread.sleep(500);
                System.out.print("\nPlane " + this.id + ": Take off successful. Goodbye ATC.");
                synchronized (air_control_center.gateStatus){
                    air_control_center.gateStatus.replace(this.gate,"Free");
                }
                airport.setRunwayStatus(true);

            }else{
                System.out.print("\nPlane " + this.id + ": Waiting to take off.");
                synchronized (air_control_center.requestList){
                    this.request= "takeOff";
                    air_control_center.requestList.add(this);
                }
                takeOffStart = System.nanoTime(); //count waiting time
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void landing(){
        try{
            System.out.print("\nMain: Plane " + this.id + ": Request to landing.");
            String[] result = air_control_center.requestLanding(this);
            if (result[0]=="true") {
                landingWaitTime = System.nanoTime() - landingStart;
                System.out.print("\nPlane " + this.id + ": Landing on the runway.");
                Thread.sleep(500);
                System.out.print("\nPlane " + this.id + ": Landing successful. Going to "+result[1]);
                this.gate= result[1];
                synchronized (air_control_center.gateStatus){
                    air_control_center.gateStatus.replace(result[1],"Busy");
                    gates.pickGate(this);
                }
                airport.setRunwayStatus(true);
            }else{
                System.out.print("\nPlane " + this.id + ": Waiting to landing.");
                synchronized (air_control_center.requestList){
                    this.request= "landing";
                    air_control_center.requestList.add(this);
                }
                landingStart = System.nanoTime(); //count waiting time
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
