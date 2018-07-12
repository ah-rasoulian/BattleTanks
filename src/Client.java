import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable
{
    private JFrame frame;
    private JTextField textField;
    private boolean clientConnected ;
    private String hostIP ;

    public Client (){
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

    public boolean isClientConnected() {
        return clientConnected;
    }

    @Override
    public void run() {
        try (Socket server = new Socket(hostIP , 1397)) {
            clientConnected = true ;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
