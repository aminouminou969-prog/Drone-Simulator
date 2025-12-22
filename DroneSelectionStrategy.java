import java.util.List;

public interface DroneSelectionStrategy {
    Drone selectDrone(Order order,List<Drone> fleet,Map theMap);    
}
