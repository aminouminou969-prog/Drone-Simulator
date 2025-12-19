public class Main {
   public static void main(String[] args) {
     Map map = new Map();
     map.addNoFlyZone(new NoFlyZone(new Position(3, 3), 2.5));
       Position base = new Position(0, 0);
        ControlCenter center = new ControlCenter(base, map); 
}
