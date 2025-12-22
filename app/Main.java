package app;
import java.io.FileWriter;
import java.io.IOException;

import core.ControlCenter;
import core.DroneFactory;
import map.Map;
import map.NoFlyZone;
import model.Drone;
import model.Order;
import model.Position;
import model.StandardPackage;

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

        Drone d1 = DroneFactory.createDrone("STANDARD",new Position(0, 0), 100);
        Drone d2 = DroneFactory.createDrone("EXPRESS",new Position(0, 0), 100);
        Drone d3 = DroneFactory.createDrone("HEAVY",new Position(0, 0), 100);

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

        center.clearEvents();
        Simulator sim = new Simulator(center);
        sim.simulateDay();


        StringBuilder sb = new StringBuilder();

        sb.append("\n============================================================\n");
        sb.append("                DRONE DELIVERY - FINAL REPORT               \n");
        sb.append("============================================================\n\n");

        sb.append(String.format("Deliveries completed : %d%n", ControlCenter.numberOfDeliveries));
        sb.append(String.format("Total distance       : %.2f km%n", ControlCenter.totalDistance));
        sb.append(String.format("Energy StandardDrone : %.2f %% %n", ControlCenter.energryConsumedStandard));
        sb.append(String.format("Energy ExpressDrone  : %.2f %% %n", ControlCenter.energyConsumedExpress));
        sb.append(String.format("Energy HeavyDrone    : %.2f %% %n", ControlCenter.energyConsumedHeavy));

        Drone best = center.getMostActiveDroneByDistance();
        if(best != null){
            sb.append(String.format("Most active drone    : ID=%d %s (%.2f km)%n",
                best.getId(), best.getModel(), best.getTotalDistance()));
        }

        sb.append("------------------------------------------------------------\n\n");

    
        sb.append("DRONES\n");
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("%-3s %-12s %-12s %-16s %-7s %-10s %-6s %-10s%n",
        "ID", "MODEL", "STATUS", "POSITION", "BAT%", "DIST(km)", "DONE", "AVG(km)"));
        sb.append("------------------------------------------------------------\n");

        for(Drone d: center.getFleet()){
            sb.append(String.format("%-3d %-12s %-12s %-16s %-7.0f %-10.2f %-6d %-10.2f%n",
                d.getId(),
                d.getModel(),
                d.getStatus(),
                d.getPosition().toString(),
                d.getBattery(),
                d.getTotalDistance(),
                d.getDeliveriesDone(),
                d.getAvgDistancePerDelivery()
            ));
        }
        sb.append("------------------------------------------------------------\n\n");


        sb.append("PROCESSED ORDERS\n");
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("%-4s %-12s %-10s %-8s %-10s %-10s%n",
        "ID", "CLIENT", "URG", "WEIGHT", "COST", "STATUS"));
        sb.append("------------------------------------------------------------\n");

        for(Order o: center.getProcessedOrders()){
            sb.append(String.format("%-4d %-12s %-10s %-8.2f %-10.2f %-10s%n",
                o.getId(),
                o.getClient(),
                o.getUrgency(),
                o.getDeliverable().getWeight(),
                o.getCost(),
                o.getStatus()
            ));
        }
        sb.append("------------------------------------------------------------\n\n");


        sb.append("PENDING ORDERS\n");
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("%-4s %-12s %-10s %-8s %-10s %-35s%n",
        "ID", "CLIENT", "URG", "WEIGHT", "STATUS", "REASON"));
        sb.append("------------------------------------------------------------\n");

        for(Order o: center.getPendingOrders()){
            sb.append(String.format("%-4d %-12s %-10s %-8.2f %-10s %-35s%n",
                o.getId(),
                o.getClient(),
                o.getUrgency(),
                o.getDeliverable().getWeight(),
                o.getStatus(),
                center.getPendingReason(o)
            ));
        }
        sb.append("------------------------------------------------------------\n");

        
        System.out.print(sb.toString());
        exportReport(sb.toString());
    }
}
