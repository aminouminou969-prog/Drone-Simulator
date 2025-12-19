import java.util.Objects;
public class Order {
    private static int NEXT_ID = 1;

    private int id;
    private String client;
    private Deliverable deliverable;
    private double cost;
    private String urgency; // NORMAL or EXPRESS
    private String status;  // PENDING, IN PROGRESS, DELIVERED, FAILED

 public Order(String client, Deliverable deliverable, String urgency, double cost) {
        this.id = NEXT_ID++;
        this.client = client;
        this.deliverable = deliverable;
        this.urgency = urgency;
        this.cost = cost;
        this.status = "PENDING";
    }

    public int getId() { return id; }
    public String getClient() { return client; }
    public Deliverable getDeliverable() { return deliverable; }
    public double getCost() { return cost; }
    public String getUrgency() { return urgency; }
    public String getStatus() { return status; }
     public void setCost(double cost) { this.cost = cost; }
    public void setStatus(String status) { this.status = status; }

 @Override
    public String toString() {
       return "Order{id=" + id +
                ", client='" + client + '\'' +
                    ", deliverable=" + deliverable +
                ", cost=" + String.format("%.2f", cost) +
                ", urgency='" + urgency + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order)) return false;
        return ((Order) o).id == this.id;
    }

     @Override
    public int hashCode() { return Objects.hash(id); }
}

