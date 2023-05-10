import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class Server extends WebSocketServer {

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

    }

    @Override
    public void onMessage(WebSocket conn, String s) {

    }

    @Override
    public void onError(WebSocket conn, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("Server Started Successfully on port " + getPort());
    }
}
