import java.util.ArrayList;
import java.util.LinkedList;

public class gates implements Runnable{

    plane plane;
    passenger passenger;
    int id;
    public gates(int id){
        this.id =id;
    }

    synchronized public static void pickGate(plane plane){
        if (plane.gate=="Gate 1"){
            airport.gate1.add(plane);
        }else if(plane.gate=="Gate 2"){
            airport.gate2.add(plane);
        }else if(plane.gate=="Gate 3"){
            airport.gate3.add(plane);
        }else {
            airport.gate4.add(plane);
        }
    }

    public void progress(LinkedList<plane> gate){
        if (gate.size()!=0){
            plane = gate.poll();
            synchronized (airport.passengerList){
                passenger = airport.passengerList.poll();
            }
            passenger oldPassenger = new passenger(0);
            oldPassenger.disembark(plane);
            passenger.embark(plane);
            plane.takeOff();
        }
    }


    public void run(){
        while(airport.passengerList.size()!=0){
            switch (this.id){
                case 1:{
                    synchronized (airport.gate1){
                        progress(airport.gate1);
                    }
                }
                case 2: {
                    synchronized (airport.gate2) {
                        progress(airport.gate2);
                    }
                }
                case 3:{
                    synchronized (airport.gate3){
                        progress(airport.gate3);
                    }
                }
                case 4:{
                    synchronized (airport.gate4){
                        progress(airport.gate4);
                    }
                }
            }
        }
    }
}
