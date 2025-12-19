import java.util.*;
public class ControlCenter {
    private List<Drone> fleet=new ArrayList<>();
    private List<Order> pendigOrders=new ArrayList<>();
    private List<Order> processedOrders=new ArrayList<>();
    private java.util.Map<Drone, Order> activeDeliveries = new HashMap<>();

    private Position base;
    private Map map;

    public static int numberOfDeliveries = 0;
    public static double totalDistance = 0.0;
    public static double energryConsumedStandard = 0.0;
    public static double energyConsumedExpress = 0.0;
    public static double energyConsumedHeavy = 0.0;

    public ControlCenter(Position base, Map map){
        this.base = base;
        this.map = map;
    }

    public void addDrone(Drone d) { fleet.add(d); }
    public void addOrder(Order o) { pendigOrders.add(o); }
    public List<Drone> getFleat(){ return new ArrayList<>(fleet); }
    public List<Order> getPendingOrders(){ return new ArrayList<>(pendigOrders); }
    public List<Order> getProcessedOrders(){ return new ArrayList<>(processedOrders); }
    public java.util.Map<Drone,Order> getActiveDeliveries(){ return activeDeliveries; }
    public Position getBase(){ return base; }
    

}
