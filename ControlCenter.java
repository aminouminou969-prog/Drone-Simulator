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
    
    public Drone findDroneForOrder(Order order){
        double weight = order.getDeliverable().getWeight();
        Position dest = order.getDeliverable().getDestination();

        for(Drone d : fleet){
            if(!"AVAILABLE".equals(d.getStatus())) continue;
            if(d.getCapacity() < weight) continue;
            if(!map.isAllowed(dest)) continue;
            if("ExpressDrone".equals(d.getModel()) && !"EXPRESS".equals(order.getUrgency())) continue;
            if (!d.canFlyTo(dest)) continue;

            return d;
        }
        return null;
    }

    public boolean assignOrder(Order order){
        Drone d = findDroneForOrder(order);
        if(d == null) return false;
        
        double newCost = calculateDeliveryCost(order, d);
        order.setCost(newCost);
        order.setStatus("IN PROGRESS");
        d.setStatus("IN DELIVERY");
        pendingOrders.remove(order);
        activeDeliveries.put(d,order);
        return true;
    }

}
