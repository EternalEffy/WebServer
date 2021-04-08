import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerFactory {
    private ArrayList<Thread> threads = new ArrayList<>();
    private int port=0;
    private ServerSocket serverSocket = null;
    private Socket client;

    public ServerFactory(int port){
        setServerPort(port);
    }

    public void setServerPort(int port){
        this.port = port;
    }

    public void startServerFactory(){
        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            this.port=port+1;
            startServerFactory();
        }
        System.out.println("ServerFactory started");
        System.out.println("Port: "+port);
        System.out.println("_________________________________________________");
        try{
            while(true) {
                client = serverSocket.accept();
                if(client.isConnected()) {
                    System.out.println("New connection established");
                    System.out.println("_________________________________________________");
                    new Thread(new EFFY_WebServer(client)).start();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try{
                serverSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
