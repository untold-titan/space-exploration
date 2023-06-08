import org.java_websocket.WebSocket;

public class Player {
    private String ipAddress;
    private WebSocket conn;
    final private String username;
    private boolean loadedGame;

    private int units;
    private int artifacts;
    private int currentSystem;

    //Server Side
    Player(String username, String ip, WebSocket conn){
        this.username = username.trim();
        this.ipAddress = ip;
        this.conn = conn;
        this.loadedGame = false;
    }
    //Client Side
    Player(String username){
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String s){
        conn.send(s);
    }

    public void addUnits(int units) {
        this.units += units;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getArtifacts() {
        return artifacts;
    }

    public void addArtifacts(int artifacts) {
        this.artifacts += artifacts;
    }

    public void sellArtifacts(int artifacts, int rate){
        this.units += (artifacts * rate);
        this.artifacts -= artifacts;
    }

    public boolean isLoadedGame() {
        return loadedGame;
    }

    public void setLoaded() {
        this.loadedGame = true;
    }

    public void setCurrentSystem(int currentSystem) {
        this.currentSystem = currentSystem;
    }
}
