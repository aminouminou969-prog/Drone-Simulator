public class DroneFactory {
    public static Drone createDrone(String type,Position position,double battery){
        switch (type.toUpperCase()) {
            case "STANDARD":
                return new StandardDrone(position, battery);
            case "EXPRESS":
                return new ExpressDrone(position, battery);
            case "HEAVY":
                return new HeavyDrone(position, battery);       
            default:
                throw new IllegalArgumentException("Unknown drone type: " + type);
        }
    }
}
