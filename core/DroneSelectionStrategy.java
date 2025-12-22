package core;
import java.util.List;

import map.Map;
import model.Drone;
import model.Order;

public interface DroneSelectionStrategy {
    Drone selectDrone(Order order,List<Drone> fleet,Map theMap);    
}
