import javax.swing.JFrame;

public class ServerTest {
    public static void main(String[] args) {
        Server person = new Server();
        person.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        person.startRunning();
    }
}
