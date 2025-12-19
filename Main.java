public class Main {
   public static void main(String[] args) {
     Map map = new Map();
     map.addNoFlyZone(new NoFlyZone(new Position(3, 3), 2.5));
       Position base = new Position(0, 0);
        ControlCenter center = new ControlCenter(base, map); 
         Drone d1 = new StandardDrone(new Position(0, 0), 100);
          Drone d2 = new ExpressDrone(new Position(0, 0), 100);
           Drone d3 = new HeavyDrone(new Position(0, 0), 100);
          center.addDrone(d1);
        center.addDrone(d2);
        center.addDrone(d3);
          
}
