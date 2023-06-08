import javax.swing.*;

//Handles the graphics of the game.
public class WindowManager {
    private final JFrame frame;
    private final RoomLobby lobby;

    private final GalacticMap galacticMap;

    private final SystemMap systemMap;

    private final SpaceStation spaceStation;

    private final CoordinateWindow coordWindow;

    private final Client client;

    private Game game;
    //0 -> Not Displaying
    //1 -> Lobby
    //2 -> Game
    int currentWindow;

    WindowManager(Client client){
        this.client = client;
        currentWindow = 1;
        frame = new JFrame();
        frame.setTitle("Space Game Lobby");
        lobby = new RoomLobby(this);
        galacticMap = new GalacticMap(this);
        systemMap = new SystemMap(this);
        spaceStation = new SpaceStation(this);
        coordWindow = new CoordinateWindow(this);
    }

    public void showLobby(){
        frame.setContentPane(lobby.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void showGalacticMap(){
        frame.setVisible(false);
        frame.setTitle("Galactic Map");
        frame.setContentPane(galacticMap.getContent());
        frame.pack();
        frame.setVisible(true);
    }

    public void showSystemMap(){
        frame.setVisible(false);
        frame.setTitle("System Map");
        frame.setContentPane(systemMap.getContent());
        frame.pack();
        frame.setVisible(true);
        systemMap.setSystem(game.getCurrentSystem());
    }

    public void showStation(){
        frame.setVisible(false);
        frame.setTitle("Space Station");
        frame.setContentPane(spaceStation.getContent());
        frame.pack();
        frame.setVisible(true);
        spaceStation.setEconomy(game.getCurrentSystem().getEconomy());
        spaceStation.setInformation(game.getCurrentPlayer());
    }

    public void showCoordinateWindow(){
        //Because the coordinate window will never be changed, it can manage itself.
        coordWindow.startWindow();
    }
    public void startGame(){
        client.send("start-game:");
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

    public void setGame(Game game){
        this.game = game;
    }

    public void updateGameMap(String[] names){
        galacticMap.setCurrentSystem(names);
    }

    public void updateMessages(String s){
        if(currentWindow == 1){
            lobby.addMessage(s);
        }
        else if(currentWindow == 2){
            //TODO: Chat Messages in-game
        }

    }

    public void flyLeft() {
        if(game == null){
            return;
        }

        game.flyLeft();
    }

    public void flyRight() {
        if(game == null){
            return;
        }

        game.flyRight();
    }

    public int awardUnits(Planet planet){
        int units = planet.getUnits();
        if(units == 0){
            return 0;
        }
        client.send("looted:" + game.getCurrentSystem().getId()+","+planet.getId()+","+units);
        game.getCurrentPlayer().addUnits(units);
        return units;
    }

    public boolean awardArtifacts(int artifacts,Planet planet){
        if(planet.getArtifacts() <= 0){
            return false;
        }
        client.send("ransacked:"+ game.getCurrentSystem().getId() + "," + planet.getId());
        game.getCurrentPlayer().addArtifacts(artifacts);
        return true;
    }

    public void sellArtifacts(int artifacts, int rate){
        game.getCurrentPlayer().sellArtifacts(artifacts,rate);
    }

    public void pack(){
        frame.pack();
    }

    public void panic(int code){
        frame.setVisible(false);
        System.out.println("Game Panic: Server Error Occured. Please restart and try again. Error Code:" + code);
    }

    public void fastTravel(int systemId){
        game.fastTravel(systemId);
    }

    public void createWaypoint(int systemId,String name){
        coordWindow.addCoordinate(systemId,name);
    }

    public void close(){
        frame.setVisible(false);
        coordWindow.close();
    }
}
