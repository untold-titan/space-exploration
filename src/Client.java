import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    private Room room;
    private final String username;
    private WindowManager window;
    private Game game;

    private Player player;

    Client(URI uri,String username){
        super(uri);
        this.username = username;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        //Setting the player's username on the server.
        send("set-user:" + username);
        send("join-room:");
        player = new Player(username);   
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
            game = new Game(s.replaceAll("galaxy:",""),window,this,player);
            send("loaded-map:");
            return;
        }
        if(s.contains("start:")){
            window.showGalacticMap();
            window.showCoordinateWindow();
            return;
        }
        if(s.contains("start-location:")){
            game.setCurrentLocation(Integer.parseInt(s.split(":")[1]));
            return;
        }
        if(s.contains("artifacts:")){
            int system = Integer.parseInt(s.split(":")[1].split(",")[0]);
            int planet = Integer.parseInt(s.split(":")[1].split(",")[1]);
            System.out.println(game.getPlanet(system,planet).getArtifacts());
            game.getPlanet(system,planet).grabArtifact();
            System.out.println(game.getPlanet(system,planet).getArtifacts());
        }
        if(s.contains("Error")){
            window.panic(500);
        }
        if(s.contains("won:")){
            window.close();
            String winner = s.split(":")[1];
            System.out.println("Game Ended! " + winner + " won!");
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
