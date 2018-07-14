
import java.io.*;
import java.net.*;
/**
 * a class that executed the connection to a client
 * it waits until a client conects to it
 * it continuesly sends and give an object that have the detail for the game
 * @author AmirHossein Rasulian
 */
public class Server implements Runnable
{
    private boolean serverConnected ;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket client ;
    public static MultiplayDatas multiplayDatas;
    public static MultiplayDatas friendMultiPlayDatas;
    public Server (){
        multiplayDatas = new MultiplayDatas();
        friendMultiPlayDatas = new MultiplayDatas();
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

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(1397)){
            while (!serverConnected) {
                client = serverSocket.accept();
                if (client.isConnected()) {
                    serverConnected = true;
                    GameState.menuIsFinished = true;
                }
            }
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
                while (true){
                    out.writeObject(multiplayDatas);
                    out.flush();
                    out.reset();
                    friendMultiPlayDatas = (MultiplayDatas) in.readObject();
                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
