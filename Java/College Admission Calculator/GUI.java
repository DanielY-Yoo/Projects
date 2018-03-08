import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;


public class GUI extends JFrame
{
    private JButton chances, close, edit;
    private JTextField gpa, sat, act, mygpa, mysat, myact, error;
    private JPanel contentPanel;
    private JLabel GPA, SAT, ACT, MYGPA, MYSAT, MYACT, ERROR;
    
    public GUI() {
        GPA = new JLabel("College GPA: "); SAT = new JLabel("College SAT(0-1600): "); 
        ACT = new JLabel("Colege ACT(1-36): "); MYGPA = new JLabel("MYGPA: "); MYSAT = new JLabel("MYSAT: "); 
        MYACT = new JLabel("MYACT(1-36): "); ERROR = new JLabel("Percent Margin(0-100): ");
        
        Pressed p = new Pressed();
        
        gpa = new JTextField(null, 20);
        sat = new JTextField(null, 20);
        act = new JTextField(null, 20);
                
        gpa.setPreferredSize(new Dimension(75, 25)); 
        sat.setPreferredSize(new Dimension(75, 25)); 
        act.setPreferredSize(new Dimension(75, 25));
        
        gpa.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {setColor("gpa");}
            public void removeUpdate(DocumentEvent e) {setColor("gpa");}
            public void insertUpdate(DocumentEvent e) {setColor("gpa");}
        }); 
        
        sat.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {setColor("sat");}
            public void removeUpdate(DocumentEvent e) {setColor("sat");}
            public void insertUpdate(DocumentEvent e) {setColor("sat");}
        }); 
        act.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {setColor("act");}
            public void removeUpdate(DocumentEvent e) {setColor("act");}
            public void insertUpdate(DocumentEvent e) {setColor("act");}
        }); 
        
        mygpa = new JTextField(null, 20);
        mysat = new JTextField(null, 20);
        myact = new JTextField(null, 20);
        
        mygpa.setPreferredSize(new Dimension(75, 25)); 
        mysat.setPreferredSize(new Dimension(75, 25)); 
        myact.setPreferredSize(new Dimension(75, 25));
        
        mygpa.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {setColor("mygpa");}
            public void removeUpdate(DocumentEvent e) {setColor("mygpa");}
            public void insertUpdate(DocumentEvent e) {setColor("mygpa");}
        }); 
        mysat.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {setColor("mysat");}
            public void removeUpdate(DocumentEvent e) {setColor("mysat");}
            public void insertUpdate(DocumentEvent e) {setColor("mysat");}
        }); 
        myact.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {setColor("myact");}
            public void removeUpdate(DocumentEvent e) {setColor("myact");}
            public void insertUpdate(DocumentEvent e) {setColor("myact");}
        });  
        
        error = new JTextField(null, 20);
        
        error.setPreferredSize(new Dimension(75, 25));
        
        error.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {setColor("myact");}
            public void removeUpdate(DocumentEvent e) {setColor("myact");}
            public void insertUpdate(DocumentEvent e) {setColor("myact");}
        }); 

        close = new JButton("close");
        edit = new JButton("disable edit");
        
        close.setPreferredSize(new Dimension(225, 25));
        edit.setPreferredSize(new Dimension(225, 25));
        
        close.addActionListener(p); edit.addActionListener(p); 

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new FlowLayout());
        
        contentPanel.add(GPA, BorderLayout.NORTH);
        contentPanel.add(gpa, BorderLayout.CENTER);
        contentPanel.add(SAT, BorderLayout.SOUTH);
        contentPanel.add(sat, BorderLayout.NORTH);
        contentPanel.add(ACT, BorderLayout.CENTER);
        contentPanel.add(act, BorderLayout.SOUTH);
        
        contentPanel.add(MYGPA, BorderLayout.NORTH);
        contentPanel.add(mygpa, BorderLayout.CENTER);
        contentPanel.add(MYSAT, BorderLayout.SOUTH);
        contentPanel.add(mysat, BorderLayout.NORTH);
        contentPanel.add(MYACT, BorderLayout.CENTER);
        contentPanel.add(myact, BorderLayout.SOUTH);
        
        contentPanel.add(ERROR, BorderLayout.NORTH);
        contentPanel.add(error, BorderLayout.CENTER);
        
        contentPanel.add(edit, BorderLayout.SOUTH);
        contentPanel.add(close, BorderLayout.SOUTH);
        
        this.setContentPane(contentPanel);
    }
    
    private class Pressed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton src = (JButton) e.getSource();
            
            if (src.equals(edit)) {
                if(edit.getText().equals("enable edit")) {
                    sat.setEditable(true); act.setEditable(true); gpa.setEditable(true);
                    mysat.setEditable(true); myact.setEditable(true); mygpa.setEditable(true);
                    error.setEditable(true);
                    edit.setText("disable edit");
                }
                else {
                    sat.setEditable(false); act.setEditable(false); gpa.setEditable(false);
                    mysat.setEditable(false); myact.setEditable(false); mygpa.setEditable(false);
                    error.setEditable(false);
                    edit.setText("enable edit");
                }
            }
            if (src.equals(close)) {
                System.exit(0);
            }
        }
    } 

    private void setColor(String src) {
        if(!isDouble(error.getText())) return;
        
        double margin = toDouble(error.getText()) / 100;
        
        if(isDouble(sat.getText()) && isDouble(mysat.getText())) {
            if(toDouble(mysat.getText()) >= toDouble(sat.getText()) * (1-margin)) 
                mysat.setBackground(Color.GREEN);
            else
                mysat.setBackground(Color.RED);
        }
        if(isDouble(act.getText()) && isDouble(myact.getText())) {
            if(toDouble(myact.getText()) >= toDouble(act.getText()) * (1-margin)) 
                myact.setBackground(Color.GREEN);
            else
                myact.setBackground(Color.RED);
        }
        if(isDouble(gpa.getText()) && isDouble(mygpa.getText())) {
            if(toDouble(mygpa.getText()) >= toDouble(gpa.getText()) * (1-margin)) 
                mygpa.setBackground(Color.GREEN);
            else
                mygpa.setBackground(Color.RED);
        }
    }
    
    private double toDouble(String a) {
        return Double.parseDouble(a);
    }
    
    private boolean isDouble(String a) {
        try {
            Double.parseDouble(a);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}








