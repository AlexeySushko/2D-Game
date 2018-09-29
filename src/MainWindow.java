import javax.swing.*;
import java.awt.*;

/**
 * @author Alexey Sushko 29/09/2018
 */
public class MainWindow extends JFrame {

    public MainWindow(){
        setTitle("Trip HOME");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.WHITE);
        setSize(735,704);
        setLocation(450,100);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

}
