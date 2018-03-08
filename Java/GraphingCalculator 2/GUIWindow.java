import javax.swing.*;
import java.awt.*;

public class GUIWindow
{
    public static void main(String[] args) {
        CalcPanel cp = new CalcPanel("Graphing Calculator");
        cp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cp.setSize(700,600);
        cp.setLocationRelativeTo(null);
        
        cp.setVisible(true);
    }
}
