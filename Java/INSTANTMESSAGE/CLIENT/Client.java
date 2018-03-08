import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP;
    private Socket connection;
    
    /*
     * Constructor for Class Client
     */
    public Client(String host) {
        super("Client Window");
        serverIP = host;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    sendMessage(event.getActionCommand());
                    userText.setText("");
                }
            }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(300, 150);
        setVisible(true);
    }
    
    /*
     * Starts running everything
     */
    public void startRunning() {
        try {
            connectToServer();
            setupStreams();
            whileChatting();
        } catch(EOFException eofException) {
            showMessage("\n Client terminated connection");
        } catch(IOException ioException) {
            ioException.printStackTrace();
        } finally {
            closeEverything();
        }
    }
    
    /*
     * Connects to server
     */
    public void connectToServer() throws IOException {
        showMessage("Attempting connection... \n");
        connection = new Socket(InetAddress.getByName(serverIP), 6789);
        showMessage("Connected to: " + connection.getInetAddress().getHostName());
    }
    
    /*
     * Sets up the streams to send and recieve the message
     */
    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Your streams are good to go! \n");
        
    }
    
    /*
     * Runs while chatting with the server
     */
    private void whileChatting() throws IOException {
        abletoType(true);
        do {
            try {
                message = (String) input.readObject();
                showMessage("\n" + message);
            } catch(ClassNotFoundException classNotFoundException) {
                showMessage("\n Invalid object type");
            }
        } while(!message.equals("SERVER - END"));
    }
    
    /*
     * Closes everything running
     */
    private void closeEverything() {
        showMessage("\n Closing everything down...");
        abletoType(false);
        try {
            output.close();
            input.close();
            connection.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }
    
    /*
     * Sends messages to the server
     */
    private void sendMessage(String message) {
        try {
            output.writeObject("CLIENT - " + message);
            output.flush();
            showMessage("\nCLIENT - " + message);
        } catch(IOException ioException) {
            chatWindow.append("\n UNKNOWN ERROR #685");
        }
    }
    
    /*
     * Change/Updates chatWindow
     */
    private void showMessage(final String m) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    chatWindow.append(m);
                }
            }
        );
    }
    
    /*
     * Gives user permission to type into the box.
     */
    private void abletoType(final boolean tof) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    userText.setEditable(tof);
                }
            }
        );
    }
}
