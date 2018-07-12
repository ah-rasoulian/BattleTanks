
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server implements Runnable
{
    private ServerSocket serverSocket ;
    private boolean serverConnected ;

    public Server (){
        serverConnected = false;
    }
    public String showIp (){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isServerConnected() {
        return serverConnected;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(1397)){
            this.serverSocket = serverSocket;
            try (Socket client = serverSocket.accept()){
                serverConnected = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
