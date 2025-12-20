
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

        // client, package(weight,destination), urgency, initial product cost
        center.addOrder(new Order("ClientA", new StandardPackage(0.8, new Position(10, 0)), "NORMAL", 1200));
        center.addOrder(new Order("ClientB", new StandardPackage(0.7, new Position(12, 2)), "EXPRESS", 2000));
        center.addOrder(new Order("ClientC", new StandardPackage(2.2, new Position(8, 8)), "NORMAL", 1500));
        center.addOrder(new Order("ClientD", new StandardPackage(0.6, new Position(11, 5)), "NORMAL", 1200));
        center.addOrder(new Order("ClientF", new StandardPackage(1.7, new Position(15, 0)), "EXPRESS", 2000));
        center.addOrder(new Order("ClientG", new StandardPackage(1.1, new Position(7, 3)), "NORMAL", 1500));

        Simulator sim = new Simulator(center);
        sim.simulateDay();


        System.out.println("\n===== FINAL STATISTICS =====");
        System.out.println("Number of deliveries: " + ControlCenter.numberOfDeliveries);
        System.out.println("Total distance: " + String.format("%.2f", ControlCenter.totalDistance) + " km");
        System.out.println("Energy StandardDrone: " + String.format("%.2f", ControlCenter.energryConsumedStandard) + " %");
        System.out.println("Energy ExpressDrone: " + String.format("%.2f", ControlCenter.energyConsumedExpress) + " %");
        System.out.println("Energy HeavyDrone: " + String.format("%.2f", ControlCenter.energyConsumedHeavy) + " %");

        Drone best = center.getMostActiveDroneByDistance();
        if(best != null){
            System.out.println("Most active drone: ID=" + best.getId() +
            " model=" + best.getModel() +
            " distance=" + String.format("%.2f",best.getTotalDistance()) + "km");
        }

        System.out.println("\n===== DRONES =====");
        for (Drone d : center.getFleet()) System.out.println(d);

        System.out.println("\n===== PROCESSED ORDERS =====");
        for (Order o : center.getProcessedOrders()) System.out.println(o);

        System.out.println("\n===== STILL PENDING =====");
        for (Order o : center.getPendingOrders()) System.out.println(o);

    }
}
