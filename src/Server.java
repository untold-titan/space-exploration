import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Objects;

public class Server extends WebSocketServer {

    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Room> rooms = new ArrayList<>();

    ArrayList<Game> games = new ArrayList<>();

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
        Player sender = getPlayer(conn.getRemoteSocketAddress().toString());
        System.out.println("Message from Client: \n" + s);
        if(s.contains("set-user:")){
            String username = s.split(":")[1];
            username = username.replaceAll("([\\[\\]])","");
            players.add(new Player(username,conn.getRemoteSocketAddress().toString(),conn));
            System.out.println("Added new player: " + username);
            return;
        }
        if(s.contains("join-room:")){
            if(sender.getUsername() == null){
                sender.sendMessage("error:no-username");
                return;
            }
            if(rooms.isEmpty()){
                rooms.add(new Room(4));
                rooms.get(0).addPlayer(sender,true);
            }
            else{
                for(Room room : rooms){
                    if(room.hasRoom()){
                        room.addPlayer(sender,true);
                        return;
                    }
                }
                rooms.add(new Room(4));
                rooms.get(rooms.size() - 1).addPlayer(sender,true);
            }
            return;
        }
        if(s.contains("message:")){
            Room room = getRoomOfPlayer(sender);
            String message = sender.getUsername() +
                    "-" +
                    s;
            room.broadcast(message);
            return;
        }
        if(s.contains("start-game:")){
            System.out.println("Initializing Game");
            //TODO: Generate the map and sync it with clients
            Room room = getRoomOfPlayer(sender);
            rooms.remove(room);
            room.broadcast("Server-message:Game is Loading!! Get Ready!");
            Game newGame = new Game(room);
            games.add(newGame);
            return;
        }
        if(s.contains("loaded-map:")){
            Game game = getGameOfPlayer(sender);
            game.setLoadedStatus(sender);
            game.tryStart();
            return;
        }
        if(s.contains("moved-to:")){
            Game game = getGameOfPlayer(sender);
            game.getPlayer(sender.getIpAddress()).setCurrentSystem(Integer.parseInt(s.split(":")[1]));
            return;
        }
        if(s.contains("ransacked:")){
            Game game = getGameOfPlayer(sender);
            int systemId = Integer.parseInt(s.split(":")[1].split(",")[0]);
            int planetId = Integer.parseInt(s.split(":")[1].split(",")[1]);
            Planet planet = game.getPlanet(systemId,planetId);
            planet.grabArtifact();
            System.out.println("Player " + sender.getUsername() + " ransacked planet " + planet.getPlanetName());
            game.broadcast("artifacts:" + systemId + "," + planetId);
            return;
        }
        if(s.contains("looted:")){
            Game game = getGameOfPlayer(sender);
            String[] data = s.split(":")[1].split(",");
            int systemId = Integer.parseInt(data[0]);
            int planetId = Integer.parseInt(data[1]);
            int units = Integer.parseInt(data[2]);
            Planet planet = game.getPlanet(systemId,planetId);
            planet.getUnits();
            game.broadcast("units:" + systemId + "," + planetId);
            game.getPlayer(sender.getIpAddress()).addUnits(units);
            game.testForWin();
            System.out.println("Player now has " + game.getPlayer(sender.getIpAddress()).getUnits() + " units");
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

    Game getGameOfPlayer(Player player){
        for(Game game : games){
            if(game.hasPlayer(player)){
                return game;
            }
        }
        return null;
    }
}
