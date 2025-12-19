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
}