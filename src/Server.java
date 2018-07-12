
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Server implements Runnable
{
    private ServerSocket serverSocket ;
    private boolean serverConnected ;

    public Server (){
        serverConnected = false;
    }
    public String showIp (){
        String systemIpAddress = "";
        try
        {
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            systemIpAddress = sc.readLine().trim();
            return systemIpAddress;
        }
        catch (Exception e)
        {
            systemIpAddress = "Cannot Execute Properly";
        }

//            return InetAddress.getLocalHost().getHostAddress();
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
