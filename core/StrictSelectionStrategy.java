package core;
import java.util.List;

import map.Map;
import model.Drone;
import model.Order;
import model.Position;

public class StrictSelectionStrategy implements DroneSelectionStrategy {
    @Override
    public Drone selectDrone(Order order,List<Drone> fleet,Map theMap){
        Position dest = order.getDeliverable().getDestination();
        double weight = order.getDeliverable().getWeight();

        if(!theMap.isAllowed(dest)) return null;

        for(Drone d:fleet){
            if(!"AVAILABLE".equals(d.getStatus())) continue;
            if(d.getCapacity() < weight) continue;
            if(!d.canFlyTo(dest)) continue;
            return d;
        }
        return null;
    }
}
