import java.util.*;
public class ControlCenter {
    private List<Drone> fleet=new ArrayList<>();
    private List<Order> pendigOrders=new ArrayList<>();
    private List<Order> processedOrders=new ArrayList<>();
    private java.util.Map<Drone, Order> activeDeliveries = new HashMap<>();

    private Position base;
    private Map map;
    
}
