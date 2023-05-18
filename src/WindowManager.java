import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Handles the graphics of the game.
public class WindowManager {
    private JFrame frame;
    private RoomLobby lobby;

    private Client client;
    //0 -> Not Displaying
    //1 -> Lobby
    //2 -> Game
    int currentWindow = 0;

    WindowManager(Client client){
        this.client = client;
        currentWindow = 1;
        lobby = new RoomLobby(this);
        frame = new JFrame("Lobby");
        frame.setContentPane(lobby.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void initLobby(){

    }

    public void sendMessage(String s){
        String message = "message:";
        client.send(message + s);
    }

    public void updateRoomLobby(Room room){
        //Only usable when in the lobby window
        //The reason I'm doing this, is I want just the window manager to interact with the actual GUI side of things.
        if(currentWindow == 1){
            lobby.updatePlayers(room);
        }
    }

    public void updateMessages(String s){
        if(currentWindow == 1){
            lobby.addMessage(s);
        }
        else if(currentWindow == 2){
            //TODO: Chat Messages in-game
        }

    }
}
