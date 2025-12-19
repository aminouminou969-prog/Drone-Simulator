import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<DeliveryZone> deliveryZones = new ArrayList<>();
    private List<NoFlyZone> noFlyZones = new ArrayList<>();

    public void addDeliveryZone(DeliveryZone z){deliveryZones.add(z);}
    public void addNoFlayZone(NoFlyZone z){noFlyZones.add(z);}
    
    
}
