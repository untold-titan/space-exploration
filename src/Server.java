import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Objects;

public class Server extends WebSocketServer {

    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Room> rooms = new ArrayList<>();

    public Server(InetSocketAddress address){
        super(address);
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        conn.send("Welcome to the Server. We are currently testing things.");
        System.out.println(conn.getRemoteSocketAddress().toString() + " has connected");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Player removeMe = getPlayer(conn.getRemoteSocketAddress().toString());
        System.out.println("Player " + removeMe.getUsername() + " DC'd");
        players.remove(removeMe);
        for(Room room : rooms){
            //TODO: Remove the player from the rooms
        }
        conn.close();
    }

    @Override
    public void onMessage(WebSocket conn, String s) {
        System.out.println("Message from Client: \n" + s);
        if(s.contains("set-user:")){
            String username = s.split(":")[1];
            username = username.replaceAll("([\\[\\]])","");
            players.add(new Player(username,conn.getRemoteSocketAddress().toString(),conn));
            System.out.println("Added new player: " + username);
        }
        if(s.contains("join-room:")){
            Player playerToMatchmake = getPlayer(conn.getRemoteSocketAddress().toString());
            if(playerToMatchmake.getUsername() == null){
                playerToMatchmake.sendMessage("error:no-username");
                return;
            }
            if(rooms.isEmpty()){
                rooms.add(new Room(4));
                rooms.get(0).addPlayer(playerToMatchmake,true);
            }
            else{
                for(Room room : rooms){
                    if(room.hasRoom()){
                        room.addPlayer(playerToMatchmake,true);
                        return;
                    }
                }
                rooms.add(new Room(4));
                rooms.get(rooms.size() - 1).addPlayer(playerToMatchmake,true);
            }
        }
        if(s.contains("message:")){
            Player sender = getPlayer(conn.getRemoteSocketAddress().toString());
            Room room = getRoomOfPlayer(sender);
            String message = sender.getUsername() +
                    "-" +
                    s;
            room.broadcast(message);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception e) {
        System.out.println("Server Error Occurred!\n\n " + e.getMessage());
        broadcast("Server Error Occurred!!!!");
        try{
            conn.close();
        }
        catch(Exception ignored){}
    }

    @Override
    public void onStart() {
        System.out.println("Server Started Successfully on port " + getPort());
    }

    Player getPlayer(String IP){
        for(Player player : players){
            if(Objects.equals(player.getIpAddress(), IP)){
                return player;
            }
        }
        return null;
    }

    Room getRoomOfPlayer(Player player){
        for(Room room : rooms){
            if(room.hasPlayer(player)){
                return room;
            }
        }
        return null;
    }
}
