

public class initialAirport extends Thread{
    public void run(){
        //Generate 12 passenger
        for(int n=1; n<=12; n++){
            passenger pass = new passenger(n);
            synchronized (airport.passengerList){
                airport.passengerList.add(pass);
            }
        }

        gates gate = new gates(1);
        Thread thgate = new Thread(gate);

        gates gate2 = new gates(2);
        Thread thgate2 = new Thread(gate2);

        gates gate3 = new gates(3);
        Thread thgate3 = new Thread(gate3);

        gates gate4 = new gates(4);
        Thread thgate4 = new Thread(gate4);

        thgate.start();
        thgate2.start();
        thgate3.start();
        thgate4.start();

        //generate the first 4 plane
        synchronized (air_control_center.gateStatus){
            air_control_center.gateStatus.put("Gate 1","Busy");
            air_control_center.gateStatus.put("Gate 2","Busy");
            air_control_center.gateStatus.put("Gate 3","Busy");
            air_control_center.gateStatus.put("Gate 4","Busy");
        }

        air_control_center ATC = new air_control_center();
        Thread thATC = new Thread(ATC);
        thATC.start();

        plane plane1 = new plane(1);
        plane1.gate ="Gate 1";
        plane plane2 = new plane(2);
        plane2.gate = "Gate 2";
        plane plane3 = new plane(3);
        plane3.gate = "Gate 3";
        plane plane4 = new plane(4);
        plane4.gate = "Gate 4";

        gates.pickGate(plane1); //disembark passenger
        gates.pickGate(plane2); //disembark passenger
        plane3.takeOff();
        plane4.takeOff();

    }
}
