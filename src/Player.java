import org.java_websocket.WebSocket;

public class Player {
    private String ipAddress;
    private WebSocket conn;
    private String username;

    private boolean loadedGame;

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

    public boolean isLoadedGame() {
        return loadedGame;
    }

    public void setLoaded() {
        this.loadedGame = true;
    }
}
