import java.util.Random;

public class planeGenerator extends Thread{

    public void run(){
        //generate landing plane
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int n = 5; n<15; n++){
            plane newPlane = new plane(n);
            newPlane.landing();
            try {
                Random random = new Random();
                long randomSleep =random.nextInt(3) * 1000;
                Thread.sleep(randomSleep);                      //wait 1 to 3 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
