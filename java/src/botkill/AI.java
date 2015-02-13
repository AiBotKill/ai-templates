package botkill;

import org.json.JSONObject;

import java.io.IOException;

public class AI extends Thread {

    private TCPClient client;
    private MessageHandler handler;
    private MessageBuffer messageBuffer;

    public AI(TCPClient client) {
        this.client = client;
        handler = new MessageHandler();
        messageBuffer = new MessageBuffer();
    }

    /**
     * Main thread that will read all the messages from the game server and add those in message queue
     */
    public void run() {
        System.out.println("Waiting for join requests...");

        while (!isInterrupted()) {

            // Read messages from game server
            String msg = client.readLine();

            String msgType = parseMsgType(msg);
            String aiId = parseAiId(msg);

            // Is it a join request? Create new game and player in here.
            if (msgType != null && msgType.equals(MessageHandler.JOIN_REQUEST)) {
                // Join request contains game data which should be utilized to set our player properties
                String createPlayerMsg = handler.handle(msg);
                if (createPlayerMsg != null) {
                    // Create the player
                    client.send(createPlayerMsg);

                    // Start game loop
                    MessageListener listener = new MessageListener(client, messageBuffer, aiId);
                    listener.start();
                } else {
                    System.out.println("Create player msg was null...this shouldn't happen if you want to join any games.");
                }
            }
            // Message is related to a certain game, insert msgs into that game's queue.
            else if (msgType != null) {
                messageBuffer.add(aiId, msg);
            }

            try {
                Thread.sleep(1000/60); // Work at maximum speed of 60fps
            } catch (InterruptedException e) {
                interrupt();
            }
        }

    }

    private String parseMsgType(String msg) {
        if (msg == null) return null;

        JSONObject msgJson = new JSONObject(msg);
        return (String) msgJson.keySet().toArray()[0];
    }

    private String parseAiId(String msg) {
        if (msg == null) return null;

        JSONObject msgJson = new JSONObject(msg);
        return (String) msgJson.keySet().toArray()[1];
    }

    public static void main(String[] args) throws IOException {
        TCPClient client = new TCPClient();
        // TODO: Change the ID
        client.connect("MY_SECRET_ID");

        AI ai = new AI(client);
        ai.start();
    }
}
