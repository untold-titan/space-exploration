import org.java_websocket.WebSocket;

public class Player {
    private String ipAddress;
    private WebSocket conn;
    private String username;

    //Server Side
    Player(String username, String ip, WebSocket conn){
        this.username = username.trim();
        this.ipAddress = ip;
        this.conn = conn;
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
}
