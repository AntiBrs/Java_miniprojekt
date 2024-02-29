import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        MenuPanel Menu= null;
        try {
            Menu = new MenuPanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JFrame f=new JFrame();
        f.add(Menu);
        f.setSize(1980,1024);

        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

}
