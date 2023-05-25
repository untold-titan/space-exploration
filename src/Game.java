import java.util.ArrayList;
import java.util.Random;

public class Game extends Room{

    // When the game starts, the players are transferred to the Game, and the Room they were in is destroyed.

    // Client Side Variables
    private WindowManager window;

    // Both Side Variables
    private Galaxy galaxy;

    //region Server
    //Server Side Game
    Game(Room room){
        super();
        this.galaxy = new Galaxy();
        this.players = room.getPlayers();
        // Galaxy toString serializes the entire galaxy into a single string that is decoded by the client,
        // and reconverted into a Galaxy object. See below for that process.
        broadcast("galaxy:" + galaxy.toString());
    }

    public void setLoadedStatus(Player player){
        if(players.contains(player)){
            player.setLoaded();
        }
    }

    public void tryStart(){
        boolean ready = true;
        for(Player player : players){
            if (!player.isLoadedGame()) {
                ready = false;
                break;
            }
        }
        if(ready){
            broadcast("start:");
            Random rand = new Random();
            for(Player player : players){
                int location = rand.nextInt(50);
                galaxy.getSystem(location).addPlayerToSystem(player);
                player.sendMessage("start-location:" + location);
            }
        }
    }
    //endregion

    private SolarSystem currentSystem;

    //region Client
    Game(String galaxy){
        this.galaxy = new Galaxy(galaxy);
        //TODO: Load map from server, display it on the window and then listen for events
    }

    public void setCurrentLocation(int id){

    }

    //endregion
}
