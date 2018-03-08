import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;

public class CalcPanel extends JFrame {
    
    /* * * * * * * * * * * * * * * 
     *     Instance Variables    *
     * * * * * * * * * * * * * * */
     
    public Polynomial p;
    Screen s;
    
    JLabel yequals = new JLabel("   y =");
    JTextField polynomialTF = new JTextField("x^2");
    JButton graphButton = new JButton("Graph");
    
    JLabel xLabel = new JLabel("    xMin - xMax: ");
    public JTextField xMin = new JTextField("-10.0");
    JLabel toLabel = new JLabel("to ");
    public JTextField xMax = new JTextField("10.0");
    
    JLabel yLabel = new JLabel("    yMin - yMax: ");
    public JTextField yMin = new JTextField("-10.0");
    JLabel toLabel2 = new JLabel("to");
    public JTextField yMax = new JTextField("10.0");
    
    JLabel rootLabel = new JLabel("    Roots: ");
    JTextField displayRootsTF = new JTextField();
    
    JLabel roundToLabel = new JLabel("    Round to: ");
    JTextField roundToTF = new JTextField("2");
    
    //Buttons for changing graph background... Do this after you're done with drawing the graph
    JLabel colorLabel = new JLabel("    Change graph color: ");
    JTextField colorTF = new JTextField("Type 'Green', 'Blue', 'Red', 'Orange', 'Purple', 'Yellow', or 'White'.");
    JButton changeColor = new JButton("Color!");
    /* * * * * * * * * * *
     *     Cnstructor    *
     * * * * * * * * * * */
     
    public CalcPanel(String title) {
        setTitle(title);
        
        JPanel northPanel = new JPanel(new GridLayout(1,3,10,10));
        JPanel centerPanel = new JPanel(new GridLayout(1,1,1,1));
        JPanel southPanel = new JPanel(new GridLayout(5,4,5,5));
        
        Container pane = getContentPane();
        pane.add(northPanel, BorderLayout.NORTH);
        pane.add(centerPanel, BorderLayout.CENTER);
        pane.add(southPanel, BorderLayout.SOUTH);
        
        
        //North Panel
        northPanel.add(yequals);
        northPanel.add(polynomialTF);
        northPanel.add(graphButton);
        graphButton.addActionListener(new actions(polynomialTF));
        
        //Center Panel
        s = new Screen(Color.white,this);
        centerPanel.add(s);
        
        //South Panel
        southPanel.add(xLabel);
        southPanel.add(xMin);
        southPanel.add(xMax);
        
        southPanel.add(yLabel);
        southPanel.add(yMin);
        southPanel.add(yMax);
        
        southPanel.add(rootLabel);
        southPanel.add(displayRootsTF);
        displayRootsTF.setEditable(false);
        southPanel.add(new JLabel("     "));
        
        southPanel.add(roundToLabel);
        southPanel.add(roundToTF);
        southPanel.add(new JLabel("     "));
        
        southPanel.add(colorLabel);
        southPanel.add(colorTF);
        southPanel.add(changeColor);
        changeColor.addActionListener(new actions());
    }

    
    //Action Listener
    public class actions implements ActionListener
    {
        JTextField tfToParse;
        
        public actions() {
            
        }
        
        public actions(JTextField t) {
            tfToParse = t;
        }
        
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == graphButton) {
                try {
                    p = new Polynomial(tfToParse.getText());
                } catch(Exception exc) {
                    JOptionPane errorBox = new JOptionPane();
                    errorBox.showMessageDialog(s, "Please enter a valid polynomial.");
                }
                
                //Max and Min x values
                double thisMin = Double.parseDouble(xMin.getText());
                double thisMax = Double.parseDouble(xMax.getText());
                
                //Max and Min y values
                double thisMiny = Double.parseDouble(yMin.getText());
                double thisMaxy = Double.parseDouble(yMax.getText());
                
                s.findPointsToPlot(thisMin,thisMax,thisMiny,thisMaxy);
                
                
                //Number to round it to
                int digit = Integer.parseInt(roundToTF.getText());
                //String of the roots to display
                String rootsString = "";
                
                for(int i = 0; i < p.roots().length; i++) {
                    double r = p.roots()[i];
                    //Rounding formula from previous project
                    r = ((double)((int)(p.roots()[i]*Math.pow(10, digit) + 0.5)))/Math.pow(10, digit);
                    rootsString += r + ", ";
                }
                
                displayRootsTF.setText(rootsString);
            } else {
                /*
                 * This is just responsible for changing the background color. This will be my
                 * extra addition to the calculator project.
                   */
                  if(colorTF.getText().equals("Green")) { s.setColor(Color.green); }
                  if(colorTF.getText().equals("Blue")) { s.setColor(Color.blue); }
                  if(colorTF.getText().equals("Red")) { s.setColor(Color.red); }
                  if(colorTF.getText().equals("Orange")) { s.setColor(Color.orange); }
                  if(colorTF.getText().equals("Purple")) { s.setColor(new Color(255,0,250)); }
                  if(colorTF.getText().equals("Yellow")) { s.setColor(Color.yellow); }
                  if(colorTF.getText().equals("White")) { s.setColor(Color.white); }
            }
       }
    }
    
}
