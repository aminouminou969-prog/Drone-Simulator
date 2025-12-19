public class ExpressDrone extends Drone {
 public ExpressDrone(Position position, double battery) {
     super(position, battery, "ExpressDrone", 45.0, 1.0);
    }
     @Override
       public double calculateConsumption(double distance) { return 4.0 * distance; }    
    
}
