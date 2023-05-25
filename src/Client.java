import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    private Room room;
    private String username;
    private WindowManager window;
    private Game game;

    Client(URI uri,String username){
        super(uri);
        this.username = username;

    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        //Setting the player's username on the server.
        send("set-user:" + username);
        send("join-room:");
        window = new WindowManager(this);
        window.showLobby();
    }

    @Override
    public void onMessage(String s) {
        System.out.println("Message from Server: \n" + s);
        if (s.contains("joined-room:")) {
            this.room = new Room(4);
            return;
        }
        if(s.contains("room-players:")){
            String[] players = s.split(":")[1].split(",");
            this.room.setPlayers(players);
            window.updateRoomLobby(this.room);
            return;
        }
        if(s.contains("message:")){
            String message = s.replace("-message","");
            window.updateMessages(message);
            return;
        }
        if(s.contains("galaxy:")){
            //TODO: Load galaxy into the client/game
            game = new Game(s.replaceAll("galaxy:",""));
            send("loaded-map:");
            return;
        }
        if(s.contains("start:")){
            //TODO: Start game!
            window.showGame();
            return;
        }
        if(s.contains("start-location:")){

        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {}

    @Override
    public void onError(Exception e) {
        System.out.println("Exception was thrown!" + e.getMessage());
        e.printStackTrace();
    }
}
