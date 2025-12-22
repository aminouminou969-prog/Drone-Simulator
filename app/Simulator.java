package app;
import java.util.HashMap;
import java.util.Map;

import core.ControlCenter;
import model.Drone;
import model.Order;
import model.Position;
public class Simulator {
    private ControlCenter controlCenter;

    public Simulator(ControlCenter controlCenter){ 
        this.controlCenter = controlCenter; 
    }

    private static final boolean DEBUG = false;

    public void simulateDay(){
        for(int minute = 1; minute <= 480; minute++){
            if(DEBUG && minute <= 10){
                System.out.println("----- minute " + minute + " -----");
            }
            controlCenter.processOneMinute();
            moveDronesOneMinute(minute);
        }
    }
    
    private void moveDronesOneMinute(int minute){
        java.util.Map<Drone, Order> active = new HashMap<>(controlCenter.getActiveDeliveries());

        if(DEBUG && minute <= 10){
            System.out.println("BEFORE move");
            for(Drone d: controlCenter.getFleet()){
                System.out.println("Drone " + d.getId() + " " + d.getModel()
                    + " status=" + d.getStatus()
                    + " pos=" + d.getPosition()
                    + " battery=" + String.format("%.2f", d.getBattery()));
            }
        }

        for(java.util.Map.Entry<Drone,Order> e : active.entrySet()){
            Drone d = e.getKey();
            Order o = e.getValue();
            if("IN DELIVERY".equals(d.getStatus())){
                Position dest = o.getDeliverable().getDestination();
                double beforeBattery = d.getBattery();
                double beforeDist = d.getTotalDistance();

                d.flyTo(dest);
                ControlCenter.totalDistance += (d.getTotalDistance() - beforeDist);
                ControlCenter.addEnergyConsumed(d, beforeBattery - d.getBattery());

                if(d.getBattery() <= 0 && !dest.equals(d.getPosition())){
                    controlCenter.markFailed(d);
                    d.setStatus("AVAILABLE");
                    controlCenter.finishDelivery(d);
                } else if (dest.equals(d.getPosition())){
                    controlCenter.markDelivered(d);
                    d.setStatus("RETURN TO BASE");

                }
            }else if ("RETURN TO BASE".equals(d.getStatus())){
                Position base = controlCenter.getBase();
                double beforeBattery = d.getBattery();
                double beforeDist = d.getTotalDistance();

                d.flyTo(base);
                ControlCenter.totalDistance +=(d.getTotalDistance() - beforeDist);
                ControlCenter.addEnergyConsumed(d, beforeBattery - d.getBattery());

                if (base.equals(d.getPosition())){
                    d.setStatus("AVAILABLE");
                    controlCenter.logReturned(d);
                    controlCenter.finishDelivery(d);
                }
            }


            
        }
        for(Drone d: controlCenter.getFleet()){
            if("AVAILABLE".equals(d.getStatus()) && controlCenter.getBase().equals(d.getPosition())){
                if(d.getBattery() < 100){
                    d.recharge(1.0); // 1% par minute
                }
            }
        }
        if(DEBUG && minute <= 10){
            System.out.println("AFTER move");
            for(Drone d: controlCenter.getFleet()){
                System.out.println("Drone " + d.getId() + " " + d.getModel()
                    + " status=" + d.getStatus()
                    + " pos=" + d.getPosition()
                    + " battery=" + String.format("%.2f", d.getBattery()));
            }
        }
    }
}
