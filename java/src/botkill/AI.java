package botkill;

import java.io.IOException;

public class AI implements Runnable {

    // TODO: CHANGE ME
    public static final String BOT_ID = "CHANGE_ME";
    private final TCPClient client;
    private MessageHandler handler;

    public AI(TCPClient client) {
        this.client = client;
        handler = new MessageHandler();
    }

    /**
     * Main thread that will read all the messages from the game server and add those in message queue
     */
    public void run() {


        while (!Thread.currentThread().isInterrupted()) {
            // If socket disconnects, interrupt thread
            if (client.disconnected()) {
                System.out.println("Socket disconnected. Terminating.");
                Thread.currentThread().interrupt();
            }

            // Read messages from game server
            String msg = client.readLine();

            if (msg != null) {
                String response = handler.handle(msg);
                if (response != null) {

                    // Check if our game ended. Not just one round but the whole game.
                    if (response.equals(MessageHandler.GAME_OVER)) {
                        // Interrupt this AI
                        Thread.currentThread().interrupt();
                    } else {
                        client.send(response);
                    }
                }
            }

            try {
               Thread.sleep(1000/60); // Work at maximum speed of 60fps
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
//        System.out.println("Thread interrupted. Closing bot...");
//        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        TCPClient client = new TCPClient();
        client.connect(BOT_ID);
        Runnable ai = new AI(client);
        new Thread(ai).run();
    }
}
