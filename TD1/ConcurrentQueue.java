import java.util.ArrayList;
import java.util.List;

public class ConcurrentQueue {
    private List<Object> queue = new ArrayList<>();

    public synchronized void average(){
        double sum = 0;
        for(Object number:queue){
            if(number instanceof Double){
                sum += (Double)number;
            }
        }
        double avg = sum/queue.size();
        System.out.println("average value : " + avg); 
    }

    public synchronized void generateRandomNumber(){
        queue.add(Math.random() * 1000);
    }
}
