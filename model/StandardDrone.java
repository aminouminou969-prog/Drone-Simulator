package model;

public class StandardDrone extends Drone {
   public StandardDrone(Position position, double battery) {
     super(position, battery, "StandardDrone", 30.0, 1.0);
    }
    @Override
     public double calculateConsumption(double distance) { return 3.0 * distance; }
}  

