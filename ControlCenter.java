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
    
}
