import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class FrameDemo {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Something more catchy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //CRÉATION DU CORPS DE TEXTE =================================================

        CounterTextField text = new CounterTextField();
        frame.getContentPane().add(text, BorderLayout.CENTER);  

        //CRÉATION DES BOUTONS =======================================================

        //bouton plus
        JButton buttonPlus = new JButton("+");
        buttonPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                text.increase();
            }
        });

        //bouton moins
        JButton buttonMoins = new JButton("-");
        buttonMoins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //System.out.println("MINUS");
                text.decrease();
            }
        });

        //bouton countdown
        JButton buttonCountdown = new JButton("Start Countdown");
        buttonCountdown.addActionListener(new ActionListener() {

            /* boolean isOver(){
                System.out.println("over test");
                return Integer.parseInt(text.getText()) == 0;
            } */

            /* void decrementText(){
                while(!isOver()){
                    text.setText(""+ (Integer.parseInt(text.getText())-1));
                    System.out.println("tick");
                    try{
                        Thread.sleep(1000);   
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            } */

            public void decrease(Timer t, TimerTask task, int delay){
                t.scheduleAtFixedRate(task, 0, delay);
            }

            @Override
            public void actionPerformed(ActionEvent e){
                Timer timer = new Timer();
                TimerTask taskDecr = new TimerTask(){
                    @Override
                    public void run(){
                        text.decrease();
                        if(Integer.parseInt(text.getText()) == 0){
                            this.cancel();
                            timer.cancel();
                        }
                    }
                };
                Thread t = new Thread(() -> decrease(timer, taskDecr, 1000));
                t.start();
        }});

        //AJOUT DES BOUTONS AU CONTAINER =============================================

        JPanel bouttonsContainer = new JPanel();
        bouttonsContainer.setLayout(new FlowLayout());
        bouttonsContainer.add(buttonMoins);
        bouttonsContainer.add(buttonCountdown);
        bouttonsContainer.add(buttonPlus);
        frame.getContentPane().add(bouttonsContainer, BorderLayout.PAGE_END);

        // make window's dimension fit its content
        frame.pack();
        // Display the window.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
