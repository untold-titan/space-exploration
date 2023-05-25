import java.util.ArrayList;
import java.util.Objects;

public class Room {

    //This field is marked as protected so that its subclass, Game can access it.
    protected ArrayList<Player> players = new ArrayList<>();
    private int maxPlayers = 4;

    Room(int maxPlayers){
        this.maxPlayers = maxPlayers;
    }

    Room(){}

    public void addPlayer(Player player, boolean server){
        if(server){
            if(players.size() < maxPlayers){
                players.add(player);
                player.sendMessage("joined-room:");
                for(Player member : players){
                    member.sendMessage("room-players:" + getUsernames());
                }
            }
            return;
        }
        players.add(player);
    }
    //For use on Client only
    public void setPlayers(String[] usernames){
        players = new ArrayList<>();
        for(String str : usernames){
            players.add(new Player(str.replaceAll("([\\[\\]])","")));
        }

    }

    public ArrayList<String> getUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        for(Player player : players){
            usernames.add(player.getUsername());
        }
        return usernames;
    }

    public boolean hasPlayer(Player player){
        for(Player member : players){
            if(Objects.equals(player.getIpAddress(), member.getIpAddress())){
                return true;
            }
        }
        return false;
    }

    public void broadcast(String string){
        for(Player member : players){
            member.sendMessage(string);
        }
    }

    public boolean hasRoom(){
        return players.size() < maxPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
