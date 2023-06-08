import java.util.Objects;
import java.util.Random;

public class Game extends Room{

    // When the game starts, the players are transferred to the Game, and the Room they were in is destroyed.

    // Both Side Variables
    final private Galaxy galaxy;

    //region Server
    //Server Side Game
    Game(Room room){
        super();
        this.galaxy = new Galaxy();
        this.players = room.getPlayers();
        for(Player player : players){
            player.setUnits(0);
        }
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
                player.setCurrentSystem(location);
                player.sendMessage("start-location:" + location);
            }
        }
    }

    public Player getPlayer(String ip) {
        for(Player player : players){
            if(Objects.equals(player.getIpAddress(), ip)){
                return player;
            }
        }
        return null;
    }

    public Planet getPlanet(int systemId, int planetId){
        return galaxy.getSystem(systemId).getPlanet(planetId);
    }

    public void testForWin(){
        for(Player player : players){
            int winningThreshold = 10000;
            if(player.getUnits() >= winningThreshold){
                //Celebrate!
                broadcast("won:" + player.getUsername());
            }
        }
    }
    //endregion

    // Client Side Variables
    private WindowManager window;
    private SolarSystem currentSystem;
    private Client client;

    private Player currentPlayer;

    //region Client
    Game(String galaxy, WindowManager manager,Client client,Player currentPlayer){
        this.window = manager;
        this.galaxy = new Galaxy(galaxy);
        this.window.setGame(this);
        this.client = client;
        this.currentPlayer = currentPlayer;
    }

    public void setCurrentLocation(int id){
        String[] names = new String[3];
        currentSystem = this.galaxy.getSystem(id);
        names[0] = currentSystem.getName();
        names[1] = galaxy.getSystem(currentSystem.getLeftLink()).getName();
        names[2] = galaxy.getSystem(currentSystem.getRightLink()).getName();
        window.updateGameMap(names);
    }

    public void flyLeft(){
        SolarSystem newSystem = this.galaxy.getSystem(currentSystem.getLeftLink());
        client.send("moved-to:" + newSystem.getId());
        setCurrentLocation(newSystem.getId());
    }

    public void flyRight(){
        SolarSystem newSystem = this.galaxy.getSystem(currentSystem.getRightLink());
        client.send("moved-to:" + newSystem.getId());
        setCurrentLocation(newSystem.getId());
    }

    public void fastTravel(int systemId){
        client.send("moved-to:" + systemId);
        setCurrentLocation(systemId);
    }

    public SolarSystem getCurrentSystem() {
        return currentSystem;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    //endregion
}
