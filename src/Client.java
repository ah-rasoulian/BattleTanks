import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * a class that executed the connection to a server
 * it has frame to give the server ip
 * and it continuesly sends and give an object that have the detail for the game
 * @author AmirHossein Rasulian
 */
public class Client implements Runnable
{
    private JFrame frame;
    private JTextField textField;
    private boolean clientConnected ;
    private String hostIP = "192.168.1.100" ;

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket server;

    public static MultiplayDatas multiplayDatas;
    public static MultiplayDatas friendMultiPlayDatas;
    public Client (){
        multiplayDatas = new MultiplayDatas();
        friendMultiPlayDatas = new MultiplayDatas();
        Handler handler = new Handler();
        clientConnected = false;
        frame = new JFrame("Connection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(new GridLayout(2,1));
        textField = new JTextField();
        textField.addActionListener(handler);

        JLabel label = new JLabel("Enter the Host IP" , SwingConstants.CENTER);

        panel.add(label);
        panel.add(textField);
        frame.pack();
    }

    @Override
    public void run() {
        try {
            while (!clientConnected) {
                server = new Socket(hostIP, 1397);
                if (server.isConnected()) {
                    clientConnected = true;
                    GameState.menuIsFinished = true;
                    System.out.println("conected to" + server.getInetAddress());
                }
            }
            System.out.println("exiting while");
             out = new ObjectOutputStream(server.getOutputStream());
             in = new ObjectInputStream(server.getInputStream());
             while (true){
                 friendMultiPlayDatas = (MultiplayDatas) in.readObject();
                 out.writeObject(multiplayDatas);
                 out.flush();
                 out.reset();
                 for (Integer a:
                         friendMultiPlayDatas.getEnemysDown()) {
                     for (EnemyTank enemyTank:
                             GameState.enemyTanks) {
                         if (a == enemyTank.tankNumber)
                             enemyTank.health = 0 ;
                     }
                 }
             }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    class Handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(textField))
            {
                hostIP = textField.getText();
                frame.setVisible(false);
                frame = null;
                ThreadPool.execute(GameState.getClient());
            }
        }
    }
}
