package botkill;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Hell
 * Date: 23.11.2014
 * Time: 1:46
 */
public class TCPClient {

    private final String HOST = "dead.coffee";
    private final int PORT = 2000;

    private Socket clientSocket;
    private DataOutputStream out;
    private BufferedReader in;

    public TCPClient() {}

    public synchronized void connect(String secretId) throws IOException {
        clientSocket = new Socket(HOST, PORT);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // This connects the bot to the correct ai-game-console team
        send("{\"teamId\":\"" + secretId + "\"}");
    }

    public synchronized boolean disconnected() {
        return clientSocket.isClosed();
    }

    public synchronized void send(String msg) {
        try {
            System.out.println("Sending: " + msg);
            out.writeBytes(msg + '\n');
            out.flush();
        } catch (IOException e) {
            System.out.println("Unable to send msg to server. Exception: " + e.getMessage());
        }
    }

    public synchronized String readLine() {
        try {
            if (in.ready()) {
                String line = in.readLine();
                return line;
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("Unable to read msg from server. Exception: " + e.getMessage());
            return null;
        }
    }
}
