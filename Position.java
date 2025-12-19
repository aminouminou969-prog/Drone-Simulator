import java.util.Objects;

public class Position{
    private double x;
    private double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }    
    public double distanceTo(Position other){
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx*dx+dy*dy);
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
    
    public double getX() {return x;}
    public double getY() {return y;}

    public void setX(double x){this.x = x;}
    public void setY(double y){this.y = y;}

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Position)) return false;
        Position p = (Position) o;
        return Double.compare(p.x, x) == 0 && Double.compare(p.y, y) == 0;
    }    

    @Override
    public int hashCode(){
        return Objects.hash(x, y); 

    }

}