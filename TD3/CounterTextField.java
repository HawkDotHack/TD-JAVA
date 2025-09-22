import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class CounterTextField extends JTextField {
    
    private int counter = 0;
    public CounterTextField() {
        this.setForeground(Color.RED);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setPreferredSize(new Dimension(175, 100));
        this.reset();
        this.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                System.out.println(c);
                if(!Character.isDigit(c)){
                    e.consume();
                }
            }
        });
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e){
                ((CounterTextField) e.getSource()).setCounter(((CounterTextField) e.getSource()).getValue());
            }

            @Override
            public void focusGained (FocusEvent e){

            }
        });
    }

    void setCounter(int newValue) {
        this.counter = newValue;
        this.update();
    }

    void increase() {
        this.counter++;
        this.update();
    }

    void decrease() {
        this.counter--;
        this.update();
    }

    void update(){
        this.setText("" + this.counter);
    }

    int getValue(){
        return Integer.parseInt(this.getText());
    }

    void reset(){
        this.counter = 0;
        this.update();
    }

    
}
