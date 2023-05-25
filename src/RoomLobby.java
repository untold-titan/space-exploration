import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RoomLobby {
    private JButton startButton;
    private JTextField messageinput;
    private JPanel contentPane;
    private JPanel players;
    private JPanel messages;
    private final WindowManager manager;

    RoomLobby(WindowManager manager){
        players.setLayout(new BoxLayout(players,BoxLayout.Y_AXIS));
        players.setBorder(new EmptyBorder(0,20,0,0));
        messages.setLayout(new BoxLayout(messages,BoxLayout.Y_AXIS));
        messages.setBorder(new EmptyBorder(0,10,0,0));
        messageinput.addActionListener(this::onTextField);
        startButton.addActionListener(this::onStartGameButtonPress);
        this.manager = manager;
    }

    private void onTextField(ActionEvent event){
        manager.sendMessage(event.getActionCommand());
        messageinput.setText("");
    }

    private void onStartGameButtonPress(ActionEvent event){
        //TODO: Voting system
        manager.startGame();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void updatePlayers(Room room){
        players.removeAll();
        ArrayList<String> playerNames = room.getUsernames();

        for(String name : playerNames){
            JLabel player = new JLabel(name);
            players.add(player);
        }

        players.revalidate();
        players.repaint();
    }

    public void addMessage(String s){
        JLabel message = new JLabel(s);
        messages.add(message);
        messages.revalidate();
        messages.repaint();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
