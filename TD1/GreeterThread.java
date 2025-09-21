import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Timer;
import java.util.TimerTask;


class GreeterThread extends Thread {

    public String nom;
    static int compteur = 0;

    public static class ConcurrentQueue {
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


    public GreeterThread(){
        super();
    }

    public static void log(Object o) {
        Thread thread = Thread.currentThread();
        System.out.println("[" + thread.getName() + "] " + o + " " + thread.getPriority());
    }

    public static synchronized void increment() {
        for(int i = 0; i < 100; i++){
            compteur ++;
            System.out.println(compteur);
        }
    }
    
    @Override
    public void run() {
        log("Greetings !!!");
    }

   //méthode GreetAll avec join
    private static void greetAll(List<String> names) throws InterruptedException {
        int prio = Thread.MAX_PRIORITY;
        for (String name : names){
            Thread t = new Thread(() -> log(name));
            t.setPriority(prio --); //marche pas avec les prio car pas absolu + temps d'exec insuffisant
            t.start();
            t.join();   
            //Thread.sleep(1000);
        }
   }

   private static void ticker(AtomicBoolean interrupt) {
        while(interrupt.get()){
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("tick");
    }
   }

   private static void time_ticker(Timer timer, TimerTask task, long delay) {
        timer.scheduleAtFixedRate(task, 0, delay);
   }

   //méthode GreetAll avec les sémaphores
/*  private static void greetAll(List<String> names) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        for (String name : names){
            Thread t = new Thread(() -> {latch.countDown(); log(name); });
            t.start(); 
            latch.await();
        }
   }
 */


    public static void main(String[] args) throws InterruptedException {
    /* GreeterThread t = new GreeterThread();
    t.setName(args[0]);
    t.start();  // NOT run() ! */


    //partie synchronisation
    for (int i = 0; i<1000; i++){
        new Thread(() -> increment()).start();
    }

    //partie ticker 
    //1) on rajoute le atomicboolean pour comprendre qu'on peut changer le boolean pendant que le ticker est en cours
    //2) on redéfinit proprement la méthode interrupt pour qu'elle le fasse 
    //Mis en com pour la suite avec le vrai timer
    /* Thread inpublic class ConcurrentQueue {
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
teruptable = new Thread(){
        private final AtomicBoolean interrupt = new AtomicBoolean(true);

        @Override
        public void interrupt(){
            this.interrupt.set(false);
        }

        @Override
        public void run(){
            ticker(interrupt);
        }
    };
    interuptable.start(); */

    //partie rename
    List<String> liste = new ArrayList<>();
    liste.add("Vivien");
    liste.add("Eloi");
    liste.add("Adrien");
    greetAll(liste);

    //ici pour la partie ticker d'au dessus
    //interuptable.interrupt();

    //partie Scheduling 
    Timer timer = new Timer();
    TimerTask ticking = new TimerTask(){
        @Override
        public void run(){
            System.out.println("tick");
        }
    };
    Thread t = new Thread(() -> time_ticker(timer, ticking, 1000));
    t.start();


    

}

}