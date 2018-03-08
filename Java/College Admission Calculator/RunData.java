import javax.swing.*;
import java.awt.*;

public class RunData
{
    public static void main(String[] args) {
        GUI window = new GUI();
        window.setTitle("College Admission Calculator");
        window.setSize(250, 450);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
    }
}