public class NoFlyZone extends DeliveryZone {
    public NoFlyZone(Position center, double radius){ super(center,radius); }

    @Override
    public String toString(){
        return "NoFlyZone{center=" + getCenter() +",radius=" + getRadius() + "}";

    } 
}