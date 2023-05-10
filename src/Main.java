import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        String address = "localhost";
        int port = 8080;
        if(args.length != 0){
            if(Objects.equals(args[0], "--server")){
                //Start the server
                System.out.println("What address would you like to start the server with?");
                address = input.nextLine();
                System.out.println("What port?");
                port = input.nextInt();
                WebSocketServer server = new Server(new InetSocketAddress(address,port));
                server.run();
            }
            else if(Objects.equals(args[0], "--dev")){
                //Skips IP and Port selection.
                WebSocketServer server = new Server(new InetSocketAddress(address,port));
                server.run();
            }
        }
        //Start in regular mode, aka, just the game client
        System.out.println("Welcome to Space Expedition. Please connect to a server!");
        System.out.println("No servers were automatically detected, please enter the IP address and port of the server you want to connect to.");
        //TODO: Code a simple web api, probably not in java to allow the game servers to make themselves public.
    }
}
