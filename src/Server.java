
import java.io.*;
import java.net.*;

public class Server implements Runnable
{
    private boolean serverConnected ;
    private ObjectOutputStream out;
    private ObjectInputStream in;

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
    public void updateDatas (){
        try {
            out.writeObject(GameState.multiplayDatas);
            GameState.friendMultiPlayDatas = (MultiplayDatas) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println();
        }
    }
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(1397)){
            try (Socket client = serverSocket.accept()){
                serverConnected = true;
                GameState.menuIsFinished = true;
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
