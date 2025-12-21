import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static void exportReport(String content){
        try(FileWriter fw = new FileWriter("report.txt")){
            fw.write(content);
        } catch(IOException e){
            System.out.println("Could not write report.txt: " + e.getMessage());
        }
    }
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
        center.addOrder(new Order("Client A", new StandardPackage(0.8, new Position(10, 0)), "NORMAL", 1200));
        center.addOrder(new Order("Client B", new StandardPackage(0.7, new Position(12, 2)), "EXPRESS", 2000));
        center.addOrder(new Order("Client C", new StandardPackage(2.2, new Position(8, 8)), "NORMAL", 1500));
        center.addOrder(new Order("Client D", new StandardPackage(0.6, new Position(11, 5)), "NORMAL", 1200));
        center.addOrder(new Order("Client F", new StandardPackage(1.7, new Position(15, 0)), "EXPRESS", 2000));
        center.addOrder(new Order("Client G", new StandardPackage(1.1, new Position(7, 3)), "NORMAL", 1500));
        center.addOrder(new Order("client NFZ", new StandardPackage(0.5, new Position(3, 3)), "NORMAL", 500));

        Simulator sim = new Simulator(center);
        sim.simulateDay();


        StringBuilder sb = new StringBuilder();

        sb.append("\n===== FINAL STATISTICS =====\n");
        sb.append("Number of deliveries: ").append(ControlCenter.numberOfDeliveries).append("\n");
        sb.append("Total distance: ").append(String.format("%.2f", ControlCenter.totalDistance)).append("km\n");
        sb.append("Energy StandardDrone: ").append(String.format("%.2f", ControlCenter.energryConsumedStandard)).append(" %\n");
        sb.append("Energy ExpressDrone: ").append(String.format("%.2f", ControlCenter.energyConsumedExpress)).append(" %\n");
        sb.append("Energy HeavyDrone: ").append(String.format(("%.2f"), ControlCenter.energyConsumedHeavy)).append(" %\n");

        Drone best = center.getMostActiveDroneByDistance();
        if(best != null){
            sb.append("Most active drone: ID=").append(best.getId())
                .append(" model=").append(best.getModel())
                .append(" distance=").append(String.format("%.2f", best.getTotalDistance()))
                .append("km\n");
        }

        sb.append("\n===== DRONES =====\n");
        for(Drone d: center.getFleet()) sb.append(d).append("\n");
        sb.append("\n===== PROCESSED ORDERS =====\n");
        for(Order o: center.getProcessedOrders()) sb.append(o).append("\n");
        sb.append("\n===== STILL PENDING =====\n");
        for(Order o: center.getPendingOrders()){
            sb.append(o).append("\n");
            sb.append("Reason: ").append(center.getPendingReason(o)).append("\n");
        }
        
        System.out.print(sb.toString());
        exportReport(sb.toString());
    }
}
