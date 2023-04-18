public class passenger{

    int id;

    passenger(int id){
        this.id = id;
    }

    public void embark(plane plane){
        try{
            System.out.print("\nThread-"+plane.gate+": Plane " + plane.id + ": Embarking passenger.");
            Thread.sleep(200);
            System.out.print("\nThread-"+plane.gate+": Passenger "+this.id+": Now embarking plane "+plane.id+".");
            Thread.sleep(700);
            System.out.print("\nThread-"+plane.gate+": Passenger "+this.id+": Everyone was aboard on plane "+plane.id+".");
            Thread.sleep(200);
            System.out.print("\nThread-"+plane.gate+": Plane " + plane.id + ": All passenger onboard. Ready to take off.");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void disembark(plane plane){
        try{
            System.out.print("\nThread-"+plane.gate+": Plane " + plane.id + ": Disembarking passenger.");
            Thread.sleep(200);
            System.out.print("\nThread-"+plane.gate+": Passenger on plane "+plane.id+": Now disembarking plane "+plane.id+".");
            Thread.sleep(700);
            System.out.print("\nThread-"+plane.gate+": Passenger on plane "+plane.id+": Everyone has left plane "+plane.id+".");
            Thread.sleep(200);
            System.out.print("\nThread-"+plane.gate+": Plane " + plane.id + ": All passenger were off board. Ready to embark passenger.");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
