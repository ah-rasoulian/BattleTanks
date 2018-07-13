import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable
{
    private JFrame frame;
    private JTextField textField;
    private boolean clientConnected ;
    private String hostIP ;

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket server;

    public static MultiplayDatas multiplayDatas;
    public static MultiplayDatas friendMultiPlayDatas;
    public Client (){
        multiplayDatas = new MultiplayDatas();
        friendMultiPlayDatas = new MultiplayDatas();
//        Handler handler = new Handler();
        clientConnected = false;
//        frame = new JFrame("Connection");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//        frame.setLocationRelativeTo(null);
//        JPanel panel = new JPanel();
//        frame.setContentPane(panel);
//        panel.setLayout(new GridLayout(2,1));
//        textField = new JTextField();
//        textField.addActionListener(handler);
//
//        JLabel label = new JLabel("Enter the Host IP" , SwingConstants.CENTER);
//
//        panel.add(label);
//        panel.add(textField);
//        frame.pack();
    }

    public boolean isClientConnected() {
        return clientConnected;
    }

    public void updateDatas (){
        if (!server.isClosed()) {
            try {
                out.writeObject(multiplayDatas);
                friendMultiPlayDatas = (MultiplayDatas) in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        try {
            server = new Socket(hostIP , 1397);
            if (!server.isClosed())
                clientConnected = true ;
            GameState.menuIsFinished = true;
             out = new ObjectOutputStream(server.getOutputStream());
             in = new ObjectInputStream(server.getInputStream());
             while (true){
                 friendMultiPlayDatas = (MultiplayDatas) in.readObject();
                 System.out.println("server loc" + friendMultiPlayDatas.getMyTankLoc());
                 out.writeObject(multiplayDatas);
                 System.out.println("client loc " + multiplayDatas.getMyTankLoc());
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
            }
        }
    }
}
