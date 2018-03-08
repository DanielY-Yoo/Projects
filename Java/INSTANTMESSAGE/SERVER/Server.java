import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame {
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    
    //Constructor
    public Server() {
        super("Instant Messager R");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    sendMessage(event.getActionCommand());
                    userText.setText("");
                }
            }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300, 150);
        setVisible(true);
    }
    
    /*
     * Sets and runs the server
     */
    public void startRunning() {
        try{
            server = new ServerSocket(6789, 100);
            while(true) {
                try{
                    //connect and have conversation
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                } catch(EOFException eofException) {
                    showMessage("\n Server ended the connection! ");
                } finally {
                    closeEverything();
                }
                
            }
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }
    
    /*
     * Waits for connection, then displays the connection information
     */
    private void waitForConnection() throws IOException {
        showMessage(" Waiting for someone to connect...  \n");
        connection = server.accept();
        showMessage(" Now connected to " + connection.getInetAddress().getHostName());
    }
    
    /*
     * Gets stream to send and recieve the data
     */
    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are now setup! \n");
    }
    
    /*
     * Runs during the conversation 
     */
    private void whileChatting() throws IOException {
        String message = " You are now connected! ";
        sendMessage(message);
        abletoType(true);
        do {
            try {
                message = (String) input.readObject();
                showMessage("\n" + message);
            } catch(ClassNotFoundException classNotFoundException) {
                showMessage(" \n not a valid input ");
            }
        } while(!message.equals("CLIENT - END"));
    }
    
    /*
     * Close streams and sockets after done chatting
     */
    private void closeEverything() {
        showMessage("\n Closing connections... \n");
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
     * Sends messages to the client
     */
    public void sendMessage(String message) {
        try {
            output.writeObject("SERVER -  " + message);
            output.flush();
            showMessage("\nSERVER - " + message);
        } catch(IOException ioException) {
            chatWindow.append("\n ERROR: I CAN'T SEND THAT MESSAGE");
        }
    }
    
    /*
     * updates the Chat Window
     */
    private void showMessage(final String text) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    chatWindow.append(text);
                }
            }
        );
    }
    
    /*
     * allows the user to type
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
