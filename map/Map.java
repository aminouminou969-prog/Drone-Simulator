package map;
import java.util.ArrayList;
import java.util.List;

import model.DeliveryZone;
import model.Position;

public class Map {
    private List<DeliveryZone> deliveryZones = new ArrayList<>();
    private List<NoFlyZone> noFlyZones = new ArrayList<>();

    public void addDeliveryZone(DeliveryZone z){deliveryZones.add(z);}
    public void addNoFlyZone(NoFlyZone z){noFlyZones.add(z);}

    public boolean isAllowed(Position p){return !isForbidden(p);}
    public boolean isForbidden(Position p){
        for(NoFlyZone z: noFlyZones){
            if(z.contains(p))return true;
        }
        return false;
    }
}
