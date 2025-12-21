import java.util.*;
public class ControlCenter {
    private List<Drone> fleet=new ArrayList<>();
    private List<Order> pendingOrders;
    private List<Order> processedOrders;
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
        this.pendingOrders=new ArrayList<>();
        this.processedOrders=new ArrayList<>();
    }

    public void addDrone(Drone d) { fleet.add(d); }
    public void addOrder(Order o) { pendingOrders.add(o); }
    public List<Drone> getFleet(){ return new ArrayList<>(fleet); }
    public List<Order> getPendingOrders(){ return new ArrayList<>(pendingOrders); }
    public List<Order> getProcessedOrders(){ return new ArrayList<>(processedOrders); }
    public java.util.Map<Drone,Order> getActiveDeliveries(){ return activeDeliveries; }
    public Position getBase(){ return base; }
    
    public void sortPendingOrdersByUrgency(){
        pendingOrders.sort((o1,o2) -> {
            boolean e1 ="EXPRESS".equals(o1.getUrgency());
            boolean e2 ="EXPRESS".equals(o2.getUrgency());
            if(e1 == e2) return 0;
            return e1 ? -1: 1;
        });
    }

    public Drone findDroneForOrder(Order order){
        Position dest = order.getDeliverable().getDestination();
        double weight = order.getDeliverable().getWeight();
        
        if(!map.isAllowed(dest)) return null;

        boolean isExpress = "EXPRESS".equals(order.getUrgency());
        
        //1) EXPRESS: try ExpressDrone ONLY (mandatory)
        if(isExpress){
            for(Drone d: fleet){
                if(!"AVAILABLE".equals(d.getStatus())) continue;
                if(!"ExpressDrone".equals(d.getModel())) continue;
                if(d.getCapacity() < weight) continue;
                if(!d.canFlyTo(dest)) continue;
                return d;
            }
        }

        //2) Shortest Estimated Time (Standard / Heavy only)
        Drone best = null;
        double bestTime = Double.MAX_VALUE;

        for(Drone d: fleet){
            if(!"AVAILABLE".equals(d.getStatus())) continue;
            if("ExpressDrone".equals(d.getModel())) continue;
            if(d.getCapacity() < weight) continue;
            if(!d.canFlyTo(dest)) continue;

            double oneWay = d.getPosition().distanceTo(dest);
            double roundTrip = oneWay * 2.0;
            double estimatedTime = roundTrip/d.getSpeed();
            if(estimatedTime < bestTime){
                bestTime = estimatedTime;
                best = d;
            }
        }

        //3) If still no drone → order stays PENDING

        return best;
        
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

    public double calculateDeliveryCost(Order order,Drone drone){
        double initialPrice = order.getCost();
        Position dest = order.getDeliverable().getDestination();
        double oneWay = drone.getPosition().distanceTo(dest);
        double distance = 2.0 * oneWay;
        double consumption = drone.calculateConsumption(distance);
        double operationCost = (distance * 0.1) + (consumption * 0.02) + 20.0;
        double insurance = Math.max(initialPrice * 0.02, 10.0);
        if ("EXPRESS".equals(order.getUrgency())) insurance += 20.0;

        return operationCost + insurance;
    }

    public void processOneMinute(){
        sortPendingOrdersByUrgency();
        List<Order> toTry = new ArrayList<>(pendingOrders);
        for(Order o : toTry){
            if("PENDING".equals(o.getStatus())) assignOrder(o);
        }
    }

    public void markDelivered(Drone d){
        Order o = activeDeliveries.get(d);
        if(o == null) return;
        o.setStatus("DELIVERED");
        processedOrders.add(o);
        numberOfDeliveries++;
    }
    
    public void markFailed(Drone d){
        Order o = activeDeliveries.get(d);
        if(o == null) return;
        o.setStatus("FAILED");
        processedOrders.add(o);
    }

    public void finishDelivery(Drone d){
        activeDeliveries.remove(d);
    }

    public static void addEnergyConsumed(Drone d, double consumedPercent){
        if("StandardDrone".equals(d.getModel())) energryConsumedStandard += consumedPercent;
        else if("ExpressDrone".equals(d.getModel())) energyConsumedExpress += consumedPercent;
        else if("HeavyDrone".equals(d.getModel())) energyConsumedHeavy += consumedPercent;
    }

    public Drone getMostActiveDroneByDistance(){
        if(fleet.isEmpty()) return null;
        Drone best = fleet.get(0);
        for(Drone d: fleet){
            if(d.getTotalDistance() > best.getTotalDistance()){
                best = d;
            }
        }
        return best;
    }
}
