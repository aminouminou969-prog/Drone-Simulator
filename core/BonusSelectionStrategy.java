package core;
import java.util.List;

import map.Map;
import model.Drone;
import model.Order;
import model.Position;

public class BonusSelectionStrategy implements DroneSelectionStrategy {
    @Override
    public Drone selectDrone(Order order,List<Drone> fleet,Map theMap){
        Position dest = order.getDeliverable().getDestination();
        double weight = order.getDeliverable().getWeight();

        if(!theMap.isAllowed(dest)) return null;

        boolean isExpress = "EXPRESS".equals(order.getUrgency());

        //1) try ExpressDrone first
        if(isExpress){
            for(Drone d: fleet){
                if(!"AVAILABLE".equals(d.getStatus())) continue;
                if(!"ExpressDrone".equals(d.getModel())) continue;
                if(d.getCapacity() < weight) continue;
                if(!d.canFlyTo(dest)) continue;
                return d;
            }
        }

        //2) shortest estimated time among Standard/Heavy
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
        
        return best;
    }
}
