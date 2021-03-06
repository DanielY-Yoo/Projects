import java.rmi.*;import java.rmi.Naming.*;import java.rmi.server.*;
import java.net.*;
import java.util.*;

interface mathInterface extends Remote {
     public int add(int a,int b) throws RemoteException;
     public int subt(int a,int b) throws RemoteException;
     public int mult(int a,int b) throws RemoteException;
     public int div(int a,int b) throws RemoteException;
}

public class mathServer extends UnicastRemoteObject implements mathInterface {
    public mathServer() throws RemoteException {
        System.out.println("Initializing Server");
    }
    public int add(int a,int b) {
        return(a+b);
    }
    public int subt(int a,int b) {
        return(a-b);
    }
    public int mult(int a,int b) {
        return(a*b);
    }
    public int div(int a,int b) {
        return(a/b);
    }
    public static void main(String args[]) {
        try {
            mathServer ms=new mathServer();
            java.rmi.Naming.rebind("MathServ",ms);
            System.out.println("Server Ready");
        } catch(RemoteException RE) {
            System.out.println("Remote Server Error:"+ RE.getMessage());
            System.exit(0);
        } catch(MalformedURLException ME) {
            System.out.println("Invalid URL!!");
        }
    }
}