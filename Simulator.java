import java.util.*;
public class Simulator {
    private ControlCenter controlCenter;

    public void simulateDay(ControlCenter controlCenter){ this.controlCenter = controlCenter; }

    public void simulaterDay(){
        for(int minute = 1; minute <= 480; minute++){
            controlCenter.processOneMinute();
            moveDronesOneMinute();
        }
    }
    
}
