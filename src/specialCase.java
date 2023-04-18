import java.util.LinkedList;
import java.util.Random;

public class specialCase extends Thread{

    public void closeGate(LinkedList gate){
        synchronized (gate){
            synchronized (air_control_center.gateStatus){
                System.out.print("\nSpecial Event : Mechanical malfunctions, gate 1 is closed.");
                air_control_center.gateStatus.put("Gate 1","Busy");
            }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            synchronized (air_control_center.gateStatus) {
                System.out.print("\nSpecial Event : Mechanical malfunctions resolved, gate 1 is open for services.");
                air_control_center.gateStatus.put("Gate 1", "Free");
            }
        }
    }

    boolean flag = true;

    public void run(){
        try{
            Random random = new Random();
            int choice = random.nextInt(3);
            if(choice==1){
                //snow cleaning
                Thread.sleep(10000); //sleep 10sec
                while(flag)
                    if (airport.getRunwayStatus()){
                        System.out.print("\nSpecial Event : The runway is closed to clean the snow.");
                        airport.setRunwayStatus(false);
                        Thread.sleep(5000);
                        System.out.print("\nSpecial Event : Snow cleaning done, runway is open now.");
                        airport.setRunwayStatus(true);
                        flag = false;
                    }else {
                        Thread.sleep(5);
                    }

            }else if(choice==2){
                Thread.sleep(10000);
                    //mechanical malfunctions
                if (airport.gate1.size()==0){
                    closeGate(airport.gate1);
                }else if (airport.gate2.size()==0){
                    closeGate(airport.gate2);
                }else if (airport.gate3.size()==0){
                    closeGate(airport.gate3);
                }else if (airport.gate4.size()==0){
                    closeGate(airport.gate4);
                }

            }else {
                //weather changing
            Thread.sleep(10000); //sleep 10sec
            while(flag)
                if (airport.getRunwayStatus()){
                    System.out.print("\nSpecial Event : The runway is closed due to bad weather condition.");
                    airport.setRunwayStatus(false);
                    Thread.sleep(5000);
                    System.out.print("\nSpecial Event : Weather condition turns good, runway is open now.");
                    airport.setRunwayStatus(true);
                    flag = false;
                }else {
                    Thread.sleep(5);
                }
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}
