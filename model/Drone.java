package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;


public abstract class Drone {
    private static int NEXT_ID = 1;
      private int id;
        private Position position;
         private double battery; // 0..100
    private String model;
       private double speed;   // km/h
    private double capacity; // kg
    private String status;  // AVAILABLE, IN DELIVERY, RETURN TO BASE
      private double totalDistance;
      private int deliveriesDone = 0;
    private List<Position> positionHistory;
     public Drone(Position position, double battery, String model, double speed, double capacity) {
        this.id = NEXT_ID++;
        this.position = position;
        this.battery = clamp(battery);
        this.model = model;
        this.speed = speed;
        this.capacity = capacity;
 this.status = "AVAILABLE";
        this.totalDistance = 0.0;
      this.positionHistory = new ArrayList<>();
        this.positionHistory.add(copyPosition(position));
    }  
       public abstract double calculateConsumption(double distance);
         public boolean canFlyTo(Position destination) {
          double oneWay = position.distanceTo(destination);
           double roundTrip = 2.0 * oneWay;
        double needed = calculateConsumption(roundTrip);
        return needed <= battery;

    }
       public void flyTo(Position destination) {
        double stepKm = getEffectiveSpeedKmh() / 60.0;
        Position before = copyPosition(position);
        position.moveTo(destination, stepKm);
        double moved = before.distanceTo(position);
              totalDistance += moved;
        battery = clamp(battery - calculateConsumption(moved));
        positionHistory.add(copyPosition(position));
    }
    public void recharge(double percentage) { battery = clamp(battery + percentage); }
    protected double getEffectiveSpeedKmh() { return speed; }
     protected Position copyPosition(Position p) { return new Position(p.getX(), p.getY()); }
     private double clamp(double v) { return Math.max(0, Math.min(100, v)); }
      public int getId() { return id; }
    public Position getPosition() { return position; }
     public double getBattery() { return battery; }
    public String getModel() { return model; }
     public double getSpeed() { return speed; }
    public double getCapacity() { return capacity; }

    public String getStatus() { return status; }
    public double getTotalDistance() { return totalDistance; }
    public List<Position> getPositionHistory() { return new ArrayList<>(positionHistory); }
    public int getDeliveriesDone() { return deliveriesDone; }
    public void incrementDeliveriesDone() { deliveriesDone++; }
    public double getAvgDistancePerDelivery() {
      if (deliveriesDone == 0) return 0.0;
      return totalDistance/deliveriesDone;
    }
    public void setStatus(String status) { this.status = status; }
      @Override
    public String toString() {
        return "Drone{id=" + id +
                ", model='" + model + '\'' +
                ", status='" + status + '\'' +
                ", position=" + position +
                ", battery=" + String.format("%.2f", battery) +
                ", speed=" + speed +
                ", capacity=" + capacity +
                ", totalDistance=" + String.format("%.2f", totalDistance) +
                '}';
    }
       @Override
    public boolean equals(Object o) {
        if (!(o instanceof Drone)) return false;
        return ((Drone) o).id == this.id;
    }
      @Override
    public int hashCode() { return Objects.hash(id); }
}


    




    

