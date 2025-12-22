package model;

public class HeavyDrone extends Drone {
 public HeavyDrone(Position position, double battery) {
    super(position, battery, "HeavyDrone", 20.0, 3.0);
    }
        @Override
          public double calculateConsumption(double distance) { return 5.0 * distance; }
            @Override
             protected double getEffectiveSpeedKmh() {
                 if (getBattery() < 20.0) return 16.0;
        return getSpeed();
             }  

           
    
}
