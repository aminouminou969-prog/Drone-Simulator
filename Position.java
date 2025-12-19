import java.util.Objects;

public class Position{
    private double x;
    private double y;

    public Position(double x, double y){this.x = x; this.y = y;}
    
    public double distanceTo(Position other){
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx*dx+dy*dy);
    }
}

public void moveTo(Position destination,double step){
    double dist = distanceTo(destination);
    if (dist <= 0) return;

    if(step >= dist){
        this.x = destination.x;
        this.y = destination.y;
        return;
    }
    double ratio = step/dist;
    this.x = this.x + (destination.x - this.x)*ratio;
    this.y = this.y + (destination.y - this.y)*ratio;

}