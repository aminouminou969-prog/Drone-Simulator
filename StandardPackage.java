
import java.util.Objects;

public class StandardPackage implements Deliverable {
    private double weight;
    private Position destination;

    public StandardPackage(double weight, Position destination) {
        this.weight = weight;
        this.destination = destination;
    }

  @Override
    public double getWeight() {
        return weight;
    }
 @Override
 public Position getDestination(){
    return destination;

 }
  public void setWeight(double weight) {
        this.weight = weight;
    }
  public void setDestination(Position destination){
    this.destination = destination;
  }
    @Override
public String toString(){
    return "standardpackage{Weight="+weight+", destination=" + destination + "}";
    }
@Override
    public boolean equals(Object o) {
        if (!(o instanceof StandardPackage)) return false;
        StandardPackage p = (StandardPackage) o;
        return Double.compare(p.weight, weight) == 0 &&
               Objects.equals(p.destination, destination);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(weight, destination);
    }

}

