import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

public class CounterComponent extends JLabel {
    private int counter = 0;
    public CounterComponent() {
        this.setForeground(Color.RED);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setPreferredSize(new Dimension(175, 100));
        this.reset();
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
        this.setText("value = " + this.counter);
    }

    void reset(){
        this.counter = 0;
        this.update();
    }
}