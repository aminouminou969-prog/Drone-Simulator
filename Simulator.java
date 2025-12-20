import java.util.*;
public class Simulator {
    private ControlCenter controlCenter;

    public Simulator(ControlCenter controlCenter){ 
        this.controlCenter = controlCenter; 
    }

    public void simulateDay(){
        for(int minute = 1; minute <= 480; minute++){
            controlCenter.processOneMinute();
            moveDronesOneMinute();
        }
    }
    
    private void moveDronesOneMinute(){
        java.util.Map<Drone, Order> active = new HashMap<>(controlCenter.getActiveDeliveries());

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
    }
}
