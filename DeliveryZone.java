import java.util.Objects;
public class DeliveryZone {
    private Position center;
    private double radius;

    public DeliveryZone(Position center, double radius){
        this.center = center;
        this.radius = radius;
    }

    public boolean contains(Position p){return center.distanceTo(p) <= radius;}
    
    public Position getCenter(){ return center; }
    public double getRadius(){ return radius; }
    public void setCenter(Position center){ this.center = center; }
    public void setRadius(double radius){ this.radius = radius; }

    @Override
    public String toString(){
        return "DeliveryZone{center=" + center + ", radius=" +radius + "}";
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof DeliveryZone)) return false;
        DeliveryZone z=(DeliveryZone) o;
        return Double.compare(z.radius, radius) == 0 && Objects.equals(center, z.center);
    }
    

}
